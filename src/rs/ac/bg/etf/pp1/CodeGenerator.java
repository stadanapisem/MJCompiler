package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

    static {
        DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    Logger logger;
    private int mainPC = -1;
    private Struct currentType;
    private String programName;

    public CodeGenerator() {
        logger = Logger.getLogger(SemanticAnalyzer.class);
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
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    @Override public void visit(ProgramName programName) {
        this.programName = programName.getId();
    }

    @Override public void visit(Program program) {
        CounterVisitor.VariableCounter varCount = new CounterVisitor.VariableCounter();
        program.traverseTopDown(varCount);
        Code.dataSize = varCount.getCounter();

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
        if (printStatement.getExpression().struct == Tab.intType) {
            Code.put(Code.print);
        } else if (printStatement.getExpression().struct == Tab.charType) {
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

    @Override public void visit(DesignatorSingle DesignatorSingle) {
        Obj tmp = completeSeach(Tab.find(programName), DesignatorSingle.getId());
        if (tmp == Tab.noObj) {
            report_error("Line " + DesignatorSingle.getLine() + " name " + DesignatorSingle.getId()
                + " not declared");
        }

        DesignatorSingle.obj = tmp;
    }

    @Override public void visit(DesignatorArray designatorArray) {
        Obj tmp = completeSeach(Tab.find(programName), designatorArray.getId());
        if (tmp == Tab.noObj) {
            report_error("Line " + designatorArray.getLine() + " name " + designatorArray.getId()
                + " not declared");
        }

        if (!designatorArray.getExpression().struct.compatibleWith(Tab.intType)) {
            report_error("Line " + designatorArray.getLine() + " array index must be an integer");
        }

        designatorArray.obj = tmp;
        Code.load(tmp);
    }

    @Override public void visit(Type Type) {
        Obj typeNode = Tab.find(Type.getTypeName());

        if (typeNode.equals(Tab.noObj)) {
            report_error("Symbol not found " + Type.getTypeName());
        } else if (typeNode.getKind() == Obj.Type) {
            Type.struct = typeNode.getType();
        } else {
            report_error(
                "Line " + Type.getLine() + " Name: " + Type.getTypeName() + " is not a type");
            Type.struct = Tab.noType;
        }

        currentType = Type.struct;
        report_debug("Type " + Type.getLine());
    }

    @Override public void visit(CharConst CharConst) {
        CharConst.obj = new Obj(Obj.Con, "", Tab.charType, (int) CharConst.getVar(), Obj.NO_VALUE);
        report_debug("Line " + CharConst.getLine() + " Found char constant's value");
    }

    @Override public void visit(BoolConst BoolConst) {
        BoolConst.obj = new Obj(Obj.Con, "", Tab.intType, BoolConst.getVar() ? 1 : 0, Obj.NO_VALUE);
        report_debug("Line " + BoolConst.getLine() + " Found Bool constant");
    }

    @Override public void visit(NumericConst NumericConst) {
        NumericConst.obj = new Obj(Obj.Con, "", Tab.intType, NumericConst.getVar(), Obj.NO_VALUE);
        report_debug("Line " + NumericConst.getLine() + " Found number constant");
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
        if (constObj == Tab.noObj) {
            report_error("Line " + ConstId.getLine() + " Assignment into undefined constant");
        } else {
            constObj.setAdr(ConstId.getConstant().obj.getAdr());
            report_debug("Line " + ConstId.getLine() + " Constant " + ((ConstIdentifier) ConstId
                .getConst_identifier()).getId() + " assigned with value: " + ConstId
                .getConstant().obj.getAdr());
        }
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
