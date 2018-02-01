package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.Stack;

public class CodeGenerator extends VisitorAdaptor {

    static {
        DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    Logger logger;
    private int mainPC = -1;
    private boolean returnFound = false;
    private String programName;
    private int varCount = 0;

    private Stack<Integer> fixUpAnd, fixUpOr, fixUpAdr, breakPc, continuePc, breaksNumber;

    public CodeGenerator() {
        logger = Logger.getLogger(SemanticAnalyzer.class);
        fixUpAnd = new Stack<>();
        fixUpOr = new Stack<>();
        fixUpAdr = new Stack<>();
        breakPc = new Stack<>();
        continuePc = new Stack<>();
        breaksNumber = new Stack<>();
    }

    private void report_info(String msg) {
        logger.info(msg);
    }

    private void report_debug(String msg) {
        logger.debug(String.format("%1$" + 10 + "s", "") + msg);
    }

    private void report_error(String msg) {
        logger.error(String.format("%1$" + 5 + "s", "") + msg);
        throw new Error();
    }

    @Override public void visit(MethodIdentifier methodIdentifier) {
        if (methodIdentifier.getId().equals("main")) {
            mainPC = Code.pc;

            if (methodIdentifier.obj.getType() != Tab.noType)
                report_error("Main function cannot have a return type");
        }

        methodIdentifier.obj.setAdr(Code.pc);

        CounterVisitor.VariableCounter varCount = new CounterVisitor.VariableCounter();
        CounterVisitor.FormalParamCounter paramCount = new CounterVisitor.FormalParamCounter();

        methodIdentifier.getParent().traverseTopDown(varCount);
        methodIdentifier.getParent().traverseTopDown(paramCount);

        methodIdentifier.obj.setLevel(paramCount.getCounter());

        Code.put(Code.enter);
        Code.put(paramCount.getCounter());
        Code.put(varCount.getCounter() + paramCount.getCounter());
    }

    @Override public void visit(MethodDefinition methodDefinition) {
        if (!returnFound) {
            Code.put(Code.exit);
            Code.put(Code.return_);
        }

        returnFound = false;
    }

    @Override public void visit(ReturnStatement returnStatement) {
        Code.put(Code.exit);
        Code.put(Code.return_);
        returnFound = true;
    }

    @Override public void visit(ReturnVoid returnVoid) {
        Code.put(Code.exit);
        Code.put(Code.return_);
        returnFound = true;
    }

    @Override public void visit(ProgramName programName) {
        this.programName = programName.getId();
    }

    @Override public void visit(Program program) {
        varCount = 0;
        program.getProgram_name().obj.getLocalSymbols().forEach(tmp -> {
            if (tmp.getKind() == Obj.Var)
                varCount++;
        });
        Code.dataSize = varCount;

        if (mainPC == -1) {
            report_error("Main function not found!");
        } else {
            Code.mainPc = mainPC;
        }
    }

    @Override public void visit(PrintStatement printStatement) {
        if (printStatement.getExpression().struct.getKind() == Struct.None) {
            report_error("Line " + printStatement.getLine() + " unknown type");
        }

        Code.loadConst(5);
        if (printStatement.getExpression().struct == Tab.intType
            || printStatement.getExpression().struct.getElemType() == Tab.intType) {
            Code.put(Code.print);
        } else if (printStatement.getExpression().struct == Tab.charType
            || printStatement.getExpression().struct.getElemType() == Tab.charType) {
            Code.put(Code.bprint);
        }
    }

    @Override public void visit(ComplexPrintStatement complexPrintStatement) {
        if (complexPrintStatement.getExpression().struct.getKind() == Struct.None) {
            report_error("Line " + complexPrintStatement.getLine() + " unknown type");
        }

        Code.loadConst(complexPrintStatement.getNumeric_const().obj.getAdr());

        if (complexPrintStatement.getExpression().struct == Tab.intType) {
            Code.put(Code.print);
        } else if (complexPrintStatement.getExpression().struct == Tab.charType) {
            Code.put(Code.bprint);
        }
    }

    @Override public void visit(ReadStatement readStatement) {
        Designator dest = readStatement.getDesignator();
        if (dest.obj.getKind() == Obj.Var || dest.obj.getKind() == Obj.Elem) {
            if (dest.obj.getType().compatibleWith(Tab.intType) || (
                dest.obj.getType().getKind() == Struct.Array && dest.obj.getType().getElemType()
                    .compatibleWith(Tab.intType))) {
                Code.put(Code.read);
                Code.store(dest.obj);
            } else if (dest.obj.getType().compatibleWith(Tab.charType) || (
                dest.obj.getType().getKind() == Struct.Array && dest.obj.getType().getElemType()
                    .compatibleWith(Tab.charType))) {
                Code.put(Code.bread);
                Code.store(dest.obj);
            } else {
                report_error("Line " + readStatement.getLine()
                    + " read function argument must be int, char or bool");
            }
        } else {
            report_error("Line " + readStatement.getLine()
                + " read function argument must be variable or array element");
        }
    }

    @Override public void visit(Assignment assignment) {
        Code.store(assignment.getDesignator().obj);
    }

    @Override public void visit(Increment increment) {
        Obj tmp = increment.getDesignator().obj;

        if (tmp.getKind() == Obj.Var || tmp.getKind() == Obj.Elem) {
            if (tmp.getType().compatibleWith(Tab.intType)) {
                if (tmp.getKind() == Obj.Elem) {
                    Code.put(Code.dup2);
                }

                Code.load(tmp);
                Code.loadConst(1);
                Code.put(Code.add);
                Code.store(tmp);
            }
        }
    }

    @Override public void visit(Decrement decrement) {
        Obj tmp = decrement.getDesignator().obj;

        if (tmp.getKind() == Obj.Var || tmp.getKind() == Obj.Elem) {
            if (tmp.getType().compatibleWith(Tab.intType)) {
                if (tmp.getKind() == Obj.Elem) {
                    Code.put(Code.dup2);
                }

                Code.load(tmp);
                Code.loadConst(1);
                Code.put(Code.sub);
                Code.store(tmp);
            }
        }
    }

    @Override public void visit(ConstantFactor constantFactor) {
        constantFactor.struct = constantFactor.getConstant().obj.getType();
        if (constantFactor.getConstant().obj.getType() == Tab.intType) {
            Code.load(constantFactor.getConstant().obj);
        } else if (constantFactor.getConstant().obj.getType() == Tab.charType) {
            Code.load(constantFactor.getConstant().obj);
        }
    }

    @Override public void visit(ConstructorFactor ConstructorFactor) {
        ConstructorFactor.struct = ConstructorFactor.getType().struct;
        //report_debug("Line " + ConstructorFactor.getLine() + " found constructor factor");
    }

    @Override public void visit(ConstructorArrayFactor ConstructorArrayFactor) {
        ConstructorArrayFactor.struct =
            new Struct(Struct.Array, ConstructorArrayFactor.getType().struct);
        //report_debug(
        //    "Line " + ConstructorArrayFactor.getLine() + " found array constructor factor");

        if (ConstructorArrayFactor.getExpression().struct.compatibleWith(Tab.intType)) {
            if (!ConstructorArrayFactor.getType().struct.compatibleWith(Tab.nullType)) {
                Code.put(Code.newarray);

                if (ConstructorArrayFactor.getType().struct == Tab.charType
                    || ConstructorArrayFactor.getType().getTypeName().equals("bool")) {
                    Code.put(0);
                } else {
                    Code.put(1);
                }
            } else {
                ConstructorArrayFactor.struct = Tab.nullType;
            }
        } else {
            report_error("Line " + ConstructorArrayFactor.getLine()
                + " number of elements must be an integer");
        }
    }

    @Override public void visit(ExpressionFactor ExpressionFactor) {
        ExpressionFactor.struct = ExpressionFactor.getExpression().struct;
        //report_debug("Line " + ExpressionFactor.getLine() + " found expression factor");
    }

    @Override public void visit(DesignatorFactor designatorFactor) {
        Code.load(designatorFactor.getDesignator().obj);
    }

    @Override public void visit(AddExpression AddExpression) {
        AddExpression.struct = (AddExpression.getAddition_term_list()).struct;
        //report_debug("Line " + AddExpression.getLine() + " found add expression");
    }

    @Override public void visit(SignAdd signAdd) {
        signAdd.string = "23";
    }

    @Override public void visit(SignSub signSub) {
        signSub.string = "24";
    }

    @Override public void visit(SignMul signMul) {
        signMul.string = "25";
    }

    @Override public void visit(SignDiv signDiv) {
        signDiv.string = "26";
    }

    @Override public void visit(SignMod signMod) {
        signMod.string = "27";
    }

    @Override public void visit(OperatorEq operatorEq) {
        operatorEq.string = "0";
    }

    @Override public void visit(OperatorNeq operatorNeq) {
        operatorNeq.string = "1";
    }

    @Override public void visit(OperatorLss operatorLss) {
        operatorLss.string = "2";
    }

    @Override public void visit(OperatorLeq operatorLeq) {
        operatorLeq.string = "3";
    }

    @Override public void visit(OperatorGrt operatorGrt) {
        operatorGrt.string = "4";
    }

    @Override public void visit(OperatorGrteq operatorGrteq) {
        operatorGrteq.string = "5";
    }

    @Override public void visit(CondOpFactor condOpFactor) {
        Code.putFalseJump(Integer.parseInt(condOpFactor.getRel_operator().string), 0);
        fixUpAnd.push(Code.pc - 2);
    }

    @Override public void visit(TermCondFactor termCondFactor) {
        Code.loadConst(0);
        Code.putFalseJump(Code.ne, 0);
        fixUpAnd.push(Code.pc - 2);
    }

    @Override public void visit(CondTerminalTerm condTerminalTerm) {
        Code.putJump(0);
        fixUpOr.push(Code.pc - 2);

        while (!fixUpAnd.empty()) {
            Code.fixup(fixUpAnd.pop());
        }
    }

    @Override public void visit(CondTermList condTermList) {
        Code.putJump(0);
        fixUpOr.push(Code.pc - 2);

        while (!fixUpAnd.empty()) {
            Code.fixup(fixUpAnd.pop());
        }
    }

    @Override public void visit(CondExpression condExpression) {
        Code.putJump(0);
        fixUpAdr.push(Code.pc - 2);

        while (!fixUpOr.empty()) {
            Code.fixup(fixUpOr.pop());
        }
    }

    @Override public void visit(IfStatement ifStatement) {
        Code.fixup(fixUpAdr.pop());
    }

    @Override public void visit(ElseFix elseFix) {
        Code.pc += 3;
        Code.fixup(fixUpAdr.pop());
        Code.pc -= 3;

        Code.putJump(0);
        fixUpAdr.push(Code.pc - 2);
    }

    @Override public void visit(RememberPc rememberPc) {
        continuePc.push(Code.pc);
        breaksNumber.push(0);
    }

    @Override public void visit(DoStatement doStatement) {
        Code.putJump(continuePc.peek());
        Code.fixup(fixUpAdr.pop());

        if (!breaksNumber.empty()) {
            int numOfBreaks = breaksNumber.pop();
            while (numOfBreaks > 0) {
                numOfBreaks--;
                Code.fixup(breakPc.pop());
            }
        }

        continuePc.pop();
    }

    @Override public void visit(ContinueStatement continueStatement) {
        Code.putJump(continuePc.peek());
    }

    @Override public void visit(BreakStatement breakStatement) {
        Code.putJump(0);
        breakPc.push(Code.pc - 2);
        breaksNumber.push(breaksNumber.pop() + 1);
    }

    @Override public void visit(Term Term) {
        Term.struct = Term.getMultiplication_factor_list().struct;
    }

    @Override public void visit(FactorList FactorList) {
        Code.put(Integer.parseInt(FactorList.getMultiplication_operator().string));
        FactorList.struct = FactorList.getMultiplication_factor_list().struct;
    }

    @Override public void visit(TerminalFactor TerminalFactor) {
        TerminalFactor.struct = TerminalFactor.getFactor().struct;
    }

    @Override public void visit(TermList TermList) {
        Code.put(Integer.parseInt(TermList.getAddition_operator().string));
        TermList.struct = TermList.getAddition_term_list().struct;
    }

    @Override public void visit(TerminalTerm TerminalTerm) {
        TerminalTerm.struct = TerminalTerm.getTerm().struct;
    }

    @Override public void visit(NegTerminalTerm NegTerminalTerm) {
        Code.put(Code.neg);
        NegTerminalTerm.struct = NegTerminalTerm.getTerm().struct;
    }

    @Override public void visit(MethodCall methodCall) {
        int offset =
            ((MethodCallIdent) methodCall.getMethod_call_ident()).getDesignator().obj.getAdr()
                - Code.pc;

        Code.put(Code.call);
        Code.put2(offset);
    }

    @Override public void visit(MethodCallDesignator methodCallDesignator) {
        int offset =
            ((MethodCallIdent) methodCallDesignator.getMethod_call_ident()).getDesignator().obj
                .getAdr() - Code.pc;

        Code.put(Code.call);
        Code.put2(offset);
    }

    @Override public void visit(DesignatorSingle DesignatorSingle) {
        Obj tmp = completeSeach(Tab.find(programName), DesignatorSingle.getId());
        if (tmp == Tab.noObj) {
            tmp = Tab.find(DesignatorSingle.getId());

            if (tmp == Tab.noObj) {
                report_error(
                    "Line " + DesignatorSingle.getLine() + " name " + DesignatorSingle.getId()
                        + " not declared");
            }
        }

        DesignatorSingle.obj = tmp;
    }

    @Override public void visit(DesignatorArray designatorArray) {
        designatorArray.obj = designatorArray.getArray_ident().obj;
    }

    @Override public void visit(ArrayIdent arrayIdent) {
        String ident = arrayIdent.getId();
        Obj tmp = completeSeach(Tab.find(programName), ident);
        if (tmp == Tab.noObj) {
            report_error("Line " + arrayIdent.getLine() + " name " + ident + " not declared");
        }

        arrayIdent.obj = new Obj(Obj.Elem, tmp.getName(), tmp.getType().getElemType());
        Code.load(tmp);
    }

    @Override public void visit(Type Type) {
        Type.struct = Tab.find(Type.getTypeName()).getType();
    }

    @Override public void visit(CharConst CharConst) {
        CharConst.obj = new Obj(Obj.Con, "", Tab.charType, (int) CharConst.getVar(), Obj.NO_VALUE);
    }

    @Override public void visit(BoolConst BoolConst) {
        BoolConst.obj = new Obj(Obj.Con, "", Tab.intType, BoolConst.getVar() ? 1 : 0, Obj.NO_VALUE);
    }

    @Override public void visit(NumericConst NumericConst) {
        NumericConst.obj = new Obj(Obj.Con, "", Tab.intType, NumericConst.getVar(), Obj.NO_VALUE);
    }

    @Override public void visit(ConstChar ConstChar) {
        ConstChar.obj = ConstChar.getChar_const().obj;
    }

    @Override public void visit(ConstBool ConstBool) {
        ConstBool.obj = ConstBool.getBool_const().obj;
    }

    @Override public void visit(ConstNumber ConstNumber) {
        ConstNumber.obj = ConstNumber.getNumeric_const().obj;
    }

    @Override public void visit(ConstId ConstId) {
        Obj constObj = ConstId.getConstant().obj;

        constObj.setAdr(ConstId.getConstant().obj.getAdr());
        report_debug("Line " + ConstId.getLine() + " Constant " + ((ConstIdentifier) ConstId
            .getConst_identifier()).getId() + " assigned with value: " + ConstId.getConstant().obj
            .getAdr());

    }

    private Obj completeSeach(Obj obj, String ident) {
        for (Obj tmp : obj.getLocalSymbols()) {
            if (ident.equals(tmp.getName())) {
                return tmp;
            }

            Obj rectmp = completeSeach(tmp, ident);
            if (rectmp != Tab.noObj)
                return rectmp;
        }

        return Tab.noObj;
    }
}
