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
    private boolean insideClass = false;
    private boolean virtualCall = false;
    private byte buff[];
    private int buffptr = 0;
    private int additionalData = 0;
    private Obj this_var = null;
    private Obj currentMethodThis = null;
    private Obj newTypeObj = null;
    private Obj budzevina = null;

    private Obj currentMethod;
    private Struct currentType;
    private int level = 0;

    private Stack<Integer> fixUpAnd, fixUpOr, fixUpAdr, breakPc, continuePc, breaksNumber;

    public CodeGenerator() {
        logger = Logger.getLogger(SemanticAnalyzer.class);
        fixUpAnd = new Stack<>();
        fixUpOr = new Stack<>();
        fixUpAdr = new Stack<>();
        breakPc = new Stack<>();
        continuePc = new Stack<>();
        breaksNumber = new Stack<>();
        buff = new byte[8192];

        Tab.init();
        Tab.insert(SemanticAnalyzer.boolType.getKind(), "bool",
            SemanticAnalyzer.boolType.getType());
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
        methodIdentifier.obj = Tab.insert(Obj.Meth, methodIdentifier.getId(),
            methodIdentifier.getMethod_return_type().struct);

        if (methodIdentifier.getId().equals("main")) {
            mainPC = Code.pc;

            if (methodIdentifier.obj.getType() != Tab.noType)
                report_error("Main function cannot have a return type");
        }

        methodIdentifier.obj.setAdr(Code.pc);
        level++;
        currentMethod = methodIdentifier.obj;

        CounterVisitor.VariableCounter varCount = new CounterVisitor.VariableCounter();
        CounterVisitor.FormalParamCounter paramCount = new CounterVisitor.FormalParamCounter();

        methodIdentifier.getParent().traverseTopDown(varCount);
        methodIdentifier.getParent().traverseTopDown(paramCount);

        methodIdentifier.obj.setLevel(paramCount.getCounter());
        Tab.openScope();

        if (insideClass) {
            currentMethodThis = Tab.insert(Obj.Var, "this", Tab.noType);
            for (int i = 0; i < methodIdentifier.getId().length(); i++) {
                loadConst(methodIdentifier.getId().charAt(i));
                put(Code.putstatic);
                put2(additionalData++);
            }

            loadConst(-1);
            put(Code.putstatic);
            put2(additionalData++);

            loadConst(methodIdentifier.obj.getAdr());
            put(Code.putstatic);
            put2(additionalData++);
        }

        Code.put(Code.enter);
        Code.put(paramCount.getCounter() + (insideClass ? 1 : 0));
        Code.put(varCount.getCounter() + paramCount.getCounter());
    }

    @Override public void visit(MethodDeclaration methodDeclaration) {
        Tab.chainLocalSymbols(currentMethod);
    }

    @Override public void visit(MethodDefinition methodDefinition) {
        if (!returnFound) {
            Code.put(Code.exit);
            Code.put(Code.return_);
        }

        Tab.closeScope();
        level--;
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
        programName.obj = Tab.insert(Obj.Prog, programName.getId(), Tab.noType);
        Tab.openScope();
        this.programName = programName.getId();
        varCount = 0;
        programName.obj.getLocalSymbols().forEach(tmp -> {
            if (tmp.getKind() == Obj.Var) {
                varCount++;
            }
        });
        Code.dataSize = varCount;
        additionalData = Code.dataSize;
    }

    @Override public void visit(Program program) {
        if (mainPC == -1) {
            report_error("Main function not found!");
        } else {
            Code.mainPc = Code.pc;
        }

        for (int i = Code.dataSize; i < buffptr; i++) {
            Code.put(buff[i]);
        }

        int offset = mainPC - Code.pc;
        Code.put(Code.call);
        Code.put2(offset);

        Code.put(Code.return_);
        Code.put(Code.exit);

        Code.dataSize = additionalData;
        Tab.closeScope();
    }

    @Override public void visit(ClassIdentifier classIdentifier) {
        insideClass = true;

        classIdentifier.obj =
            Tab.insert(Obj.Type, classIdentifier.getId(), new Struct(Struct.Class));
        Tab.openScope();
        Tab.insert(Obj.Fld, "_vtable", Tab.noType).setAdr(additionalData);
        classIdentifier.obj.setAdr(additionalData);
    }

    @Override public void visit(ClassDeclaration classDeclaration) {
        insideClass = false;
        classDeclaration.obj = classDeclaration.getClass_identifier().obj;
        classDeclaration.obj.getType().setMembers(Tab.currentScope().getLocals());
        Tab.chainLocalSymbols(classDeclaration.obj);
        Tab.closeScope();
        loadConst(-2);
        put(Code.putstatic);
        put2(additionalData++);
    }

    @Override public void visit(Extends extnd) {
        Obj base = Tab.find(extnd.getType().getTypeName());

        base.getType().getMembers().symbols().forEach(tmp -> {
            if (!tmp.getName().equals("_vtable")) {
                Obj inherited = Tab.insert(tmp.getKind(), tmp.getName(), tmp.getType());
                inherited.setAdr(tmp.getAdr());

                if (tmp.getKind() == Obj.Meth) {
                    Tab.openScope();
                    tmp.getLocalSymbols().forEach(
                        member -> Tab.insert(member.getKind(), member.getName(), member.getType()));
                    Tab.chainLocalSymbols(inherited);
                    Tab.closeScope();

                    for (int i = 0; i < tmp.getName().length(); i++) {
                        loadConst(tmp.getName().charAt(i));
                        put(Code.putstatic);
                        put2(additionalData++);
                    }

                    loadConst(-1);
                    put(Code.putstatic);
                    put2(additionalData++);

                    loadConst(tmp.getAdr());
                    put(Code.putstatic);
                    put2(additionalData++);
                }
            }
        });
    }

    @Override public void visit(DesignatorFieldSingle designatorFieldSingle) {
        virtualCall = true;
        Code.load(designatorFieldSingle.getDesignator().obj);

        for (Obj tmp : designatorFieldSingle.getDesignator().obj.getType().getMembers().symbols()) {
            if (tmp.getName().equals(designatorFieldSingle.getId())) {
                designatorFieldSingle.obj = tmp;
                break;
            }
        }
    }

    @Override public void visit(DesignatorFieldArray designatorFieldArray) {
        Code.load(designatorFieldArray.getDesignator().obj);

        for (Obj tmp : designatorFieldArray.getDesignator().obj.getType().getMembers().symbols()) {
            if (tmp.getName()
                .equals(((ArrayFldIdent) designatorFieldArray.getArray_fld_ident()).getId())) {
                designatorFieldArray.obj = tmp;
                break;
            }
        }

        Code.load(designatorFieldArray.obj);
        Obj arrayIdent = new Obj(Obj.Elem, designatorFieldArray.obj.getName(),
            designatorFieldArray.obj.getType().getElemType());

        designatorFieldArray.obj = arrayIdent;
    }

    @Override public void visit(DesignatorArrayFld designatorArrayFld) {
        Code.load(designatorArrayFld.getArray_fld_header().obj);
        designatorArrayFld.obj = null;
    }

    @Override public void visit(DesignatorThis designatorThis) {
        Code.load(currentMethodThis);

        designatorThis.obj = Tab.find(designatorThis.getId());
    }

    @Override public void visit(PrintStatement printStatement) {
        if (printStatement.getExpression().struct.getKind() == Struct.None) {
            report_error("Line " + printStatement.getLine() + " unknown type");
        }

        Code.loadConst(0);
        if (printStatement.getExpression().struct == Tab.intType
            || printStatement.getExpression().struct == SemanticAnalyzer.boolType.getType()
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

        if (newTypeObj != null) {
            if (assignment.getDesignator().obj.getKind() == Obj.Elem) {
                Code.load(Tab.find(assignment.getDesignator().obj.getName()));
                Code.load(budzevina);
                report_info("Yes it is");
            }

            Code.load(assignment.getDesignator().obj);
            Code.loadConst(newTypeObj.getAdr());
            Code.put(Code.putfield);
            Code.put2(0);
            newTypeObj = null;
        }
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
            budzevina = constantFactor.getConstant().obj;
            Code.load(constantFactor.getConstant().obj);
        } else if (constantFactor.getConstant().obj.getType() == Tab.charType) {
            Code.load(constantFactor.getConstant().obj);
        }
    }

    @Override public void visit(ConstructorFactor ConstructorFactor) {
        ConstructorFactor.struct = ConstructorFactor.getType().struct;
        //report_debug("Line " + ConstructorFactor.getLine() + " found constructor factor");
        Code.put(Code.new_);
        Code.put2(currentType.getNumberOfFields() * 4);
        newTypeObj = Tab.find(ConstructorFactor.getType().getTypeName());
    }

    @Override public void visit(ConstructorArrayFactor ConstructorArrayFactor) {
        ConstructorArrayFactor.struct =
            new Struct(Struct.Array, ConstructorArrayFactor.getType().struct);
        //report_debug(
        //    "Line " + ConstructorArrayFactor.getLine() + " found array constructor factor");
        Code.put(Code.newarray);
        Code.put(ConstructorArrayFactor.struct == Tab.charType ? 0 : 1);
    }

    @Override public void visit(ExpressionFactor ExpressionFactor) {
        ExpressionFactor.struct = ExpressionFactor.getExpression().struct;
        //report_debug("Line " + ExpressionFactor.getLine() + " found expression factor");
    }

    @Override public void visit(DesignatorFactor designatorFactor) {
        if (designatorFactor.getDesignator().obj != null) {
            Code.load(designatorFactor.getDesignator().obj);
        }
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
        if (!virtualCall) {
            int offset =
                ((MethodCallIdent) methodCall.getMethod_call_ident()).getDesignator().obj.getAdr()
                    - Code.pc;

            Code.put(Code.call);
            Code.put2(offset);
        } else {
            Code.load(this_var);
            Code.put(Code.getfield);
            Code.put2(0);

            Code.put(Code.invokevirtual);

            String methodName =
                ((MethodCallIdent) methodCall.getMethod_call_ident()).getDesignator().obj.getName();
            //methodName = methodName.replace("super.", "");
            for (int i = 0; i < methodName.length(); i++) {
                Code.put4(methodName.charAt(i));
            }

            Code.put4(-1);

            virtualCall = false;
        }
    }

    @Override public void visit(MethodCallDesignator methodCallDesignator) {
        if (!virtualCall) {
            int offset =
                ((MethodCallIdent) methodCallDesignator.getMethod_call_ident()).getDesignator().obj
                    .getAdr() - Code.pc;

            Code.put(Code.call);
            Code.put2(offset);

        } else {
            Code.load(this_var);
            if (this_var.getType().getKind() == Struct.Array) {
                Code.load(budzevina);
                Code.put(Code.aload);
            }

            Code.put(Code.getfield);
            Code.put2(0);

            Code.put(Code.invokevirtual);

            String methodName =
                ((MethodCallIdent) methodCallDesignator.getMethod_call_ident()).getDesignator().obj
                    .getName();
            //methodName = methodName.replace("super.", "");
            for (int i = 0; i < methodName.length(); i++) {
                Code.put4(methodName.charAt(i));
            }

            Code.put4(-1);

            virtualCall = false;
        }
    }

    @Override public void visit(DesignatorSingle DesignatorSingle) {
        Obj tmp = Tab.find(DesignatorSingle.getId());
        if (tmp == Tab.noObj) {
            tmp = Tab.find(DesignatorSingle.getId());

            if (tmp == Tab.noObj) {
                /*if (insideClass) {
                    String superName = "super." + DesignatorSingle.getId();

                    for (int i = 1;
                         tmp == Tab.noObj && i < SemanticAnalyzer.MAX_INHERITANCE_LEVEL; i++) {
                        tmp = Tab.find(superName);
                        superName = "super." + superName;
                    }

                }*/

                if (tmp == Tab.noObj) {
                    report_error(
                        "Line " + DesignatorSingle.getLine() + " name " + DesignatorSingle.getId()
                            + " not declared");
                }
            }
        }

        if (tmp.getKind() == Obj.Fld) {
            Code.load(currentMethodThis);
        }

        DesignatorSingle.obj = tmp;
        this_var = tmp;
    }

    @Override public void visit(DesignatorArray designatorArray) {
        designatorArray.obj = designatorArray.getArray_ident().obj;
    }

    @Override public void visit(ArrayIdent arrayIdent) {
        String ident = arrayIdent.getId();
        Obj tmp = Tab.find(ident);
        if (tmp == Tab.noObj) {
            return;
            //report_error("Line " + arrayIdent.getLine() + " name " + ident + " not declared");
        }

        arrayIdent.obj = new Obj(Obj.Elem, tmp.getName(), tmp.getType().getElemType());

        if (tmp.getKind() == Obj.Fld) {
            Code.load(currentMethodThis);
        }

        Code.load(tmp);
    }

    @Override public void visit(Type Type) {
        currentType = Type.struct = Tab.find(Type.getTypeName()).getType();
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

    @Override public void visit(VarIDArray varIDArray) {
        Obj tmp = Tab.insert(insideClass && currentMethod == null ? Obj.Fld : Obj.Var,
            ((VarIdentifier) varIDArray.getVar_identifier()).getId(),
            new Struct(Struct.Array, currentType));
        tmp.setLevel(level);
    }

    @Override public void visit(VarID varID) {
        Obj tmp = Tab.insert(insideClass && currentMethod == null ? Obj.Fld : Obj.Var,
            ((VarIdentifier) varID.getVar_identifier()).getId(), currentType);
        tmp.setLevel(level);

    }

    @Override public void visit(ConstIdentifier constIdentifier) {
        Tab.insert(Obj.Con, constIdentifier.getId(), currentType);
    }

    @Override public void visit(FormalParameter FormalParameter) {
        Tab.currentScope().addToLocals(
            new Obj(Obj.Var, FormalParameter.getI2(), FormalParameter.getType().struct, 0, level));
    }

    @Override public void visit(FormalParameterArray FormalParameterArray) {
        Tab.currentScope().addToLocals(new Obj(Obj.Var, FormalParameterArray.getI2(),
            new Struct(Struct.Array, FormalParameterArray.getType().struct), 0, level));
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

    private void put(int x) {
        if (buffptr >= 8192) {
            report_error("Error in put method!!!!");
        } else
            buff[buffptr++] = (byte) x;
    }

    private void put2(int x) {
        put(x >> 8);
        put(x);
    }

    private void put4(int x) {
        put2(x >> 16);
        put2(x);
    }

    private void loadConst(int n) {
        put(Code.const_);
        put4(n);
    }
}
