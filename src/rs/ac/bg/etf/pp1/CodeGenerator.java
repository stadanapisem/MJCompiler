package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    private Obj currentClass = null;
    private boolean virtualCall = false;
    private byte buff[];
    private int buffptr = 0;
    private int additionalData = 0;
    private Obj this_var = null;
    private Obj currentMethodThis = null;
    private Obj newTypeObj = null;
    private Obj budzevina = null;
    private List<String> inheritedMethodFix;

    private Obj currentMethod;
    private Struct currentType;
    private int level = 0;

    private Stack<Integer> fixUpAnd, fixUpOr, fixUpAdr, breakPc, continuePc, breaksNumber,
        continueNumber;
    private Tree Extention;

    public CodeGenerator() {
        logger = Logger.getLogger(SemanticAnalyzer.class);
        fixUpAnd = new Stack<>();
        fixUpOr = new Stack<>();
        fixUpAdr = new Stack<>();
        breakPc = new Stack<>();
        continuePc = new Stack<>();
        continueNumber = new Stack<>();
        breaksNumber = new Stack<>();
        buff = new byte[8192];
        inheritedMethodFix = new ArrayList<>();
        Extention = new Tree();
        Extention.insert(new Struct(Struct.None));

        Tab.init();
        Tab.insert(SemanticAnalyzer.boolType.getKind(), "bool",
            SemanticAnalyzer.boolType.getType());
        put(Code.enter);
        put(0);
        put(0);
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
        Obj tmp = Tab.currentScope().findSymbol(methodIdentifier.getId());
        if (tmp != null) {
            if (tmp.getKind() != Obj.Meth) {
                report_error(
                    "Line " + methodIdentifier.getLine() + " Symbol " + methodIdentifier.getId()
                        + " already defined!");
            } else {
                methodIdentifier.obj = tmp;
            }
        } else {
            methodIdentifier.obj = Tab.insert(Obj.Meth, methodIdentifier.getId(),
                methodIdentifier.getMethod_return_type().struct);
        }
        inheritedMethodFix.remove(methodIdentifier.obj.getName());
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
            currentMethodThis = Tab.insert(Obj.Var, "this", currentClass.getType());
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
        Code.put(varCount.getCounter() + paramCount.getCounter() + (insideClass ? 1 : 0));
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
        currentMethod = null;
        this_var = null;
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

    private void integrateFunctions() {
        Obj chrObj = Tab.find("chr");
        chrObj.setAdr(Code.pc);
        Obj ordObj = Tab.find("ord");
        ordObj.setAdr(Code.pc);

        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load);
        Code.put(0);
        Code.put(Code.exit);
        Code.put(Code.return_);

        Obj lenObj = Tab.find("len");
        lenObj.setAdr(Code.pc);

        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load);
        Code.put(0);
        Code.put(Code.arraylength);
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    @Override public void visit(ProgramName programName) {
        programName.obj = Tab.insert(Obj.Prog, programName.getId(), Tab.noType);
        Tab.openScope();
        this.programName = programName.getId();
        additionalData = 0;

        integrateFunctions();
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

        Code.put(Code.exit);
        Code.put(Code.return_);

        Code.dataSize = additionalData;
        Tab.chainLocalSymbols(program.getProgram_name().obj);
        /*program.getProgram_name().obj.getLocalSymbols().forEach(tmp -> {
            if (tmp.getKind() == Obj.Var) {
                tmp.setAdr(Code.dataSize++);
            }
        });*/
        Tab.closeScope();
    }

    @Override public void visit(ClassIdentifier classIdentifier) {
        insideClass = true;

        classIdentifier.obj =
            Tab.insert(Obj.Type, classIdentifier.getId(), new Struct(Struct.Class));
        Tab.openScope();
        Tab.insert(Obj.Fld, "_vtable", Tab.noType).setAdr(additionalData);
        classIdentifier.obj.setAdr(additionalData);
        currentClass = classIdentifier.obj;
        inheritedMethodFix = new ArrayList<>();
    }

    @Override public void visit(ChainVars chainVars) {
        currentClass.setLocals(Tab.currentScope().getLocals());
    }

    @Override public void visit(ClassDeclaration classDeclaration) {
        insideClass = false;
        currentClass = null;
        classDeclaration.obj = classDeclaration.getClass_identifier().obj;

        if (classDeclaration.getOptional_extends() instanceof Extends) {
            Obj base = Tab.find(
                ((Extends) classDeclaration.getOptional_extends()).getType().getTypeName());

            base.getType().getMembers().symbols().forEach(tmp -> {
                if (!tmp.getName().equals("_vtable")
                    && inheritedMethodFix.contains(tmp.getName())) {
                    //Obj inherited = Tab.insert(tmp.getKind(), "super." + tmp.getName(), tmp.getType());
                    Obj inherited = findLocalSymbol(classDeclaration.obj, tmp.getName());
                    inherited.setAdr(tmp.getAdr());

                    if (tmp.getKind() == Obj.Meth) {
                        //String ident = "super." + tmp.getName();
                        String ident = tmp.getName();
                        for (int i = 0; i < ident.length(); i++) {
                            loadConst(ident.charAt(i));
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

        classDeclaration.obj.getType().setMembers(Tab.currentScope().getLocals());
        Tab.chainLocalSymbols(classDeclaration.obj);
        Tab.closeScope();
        loadConst(-2);
        put(Code.putstatic);
        put2(additionalData++);

        if (classDeclaration.getOptional_extends() instanceof Extends) {
            Extention.insert(((Extends) classDeclaration.getOptional_extends()).getType().struct,
                classDeclaration.getClass_identifier().obj.getType());
        } else {
            Extention.insert(classDeclaration.obj.getType());
        }
    }

    @Override public void visit(Extends extnd) {
        Obj base = Tab.find(extnd.getType().getTypeName());

        base.getType().getMembers().symbols().forEach(tmp -> {
            if (!tmp.getName().equals("_vtable")) {
                //Obj inherited = Tab.insert(tmp.getKind(), "super." + tmp.getName(), tmp.getType());
                Obj inherited = Tab.insert(tmp.getKind(), tmp.getName(), tmp.getType());
                inherited.setAdr(tmp.getAdr());

                if (tmp.getKind() == Obj.Meth) {
                    Tab.openScope();
                    tmp.getLocalSymbols().forEach(
                        member -> Tab.insert(member.getKind(), member.getName(), member.getType()));
                    Tab.chainLocalSymbols(inherited);
                    Tab.closeScope();

                    inheritedMethodFix.add(tmp.getName());
                    //String ident = "super." + tmp.getName();
                   /* String ident = tmp.getName();
                    for (int i = 0; i < ident.length(); i++) {
                        loadConst(ident.charAt(i));
                        put(Code.putstatic);
                        put2(additionalData++);
                    }

                    loadConst(-1);
                    put(Code.putstatic);
                    put2(additionalData++);

                    loadConst(tmp.getAdr());
                    put(Code.putstatic);
                    put2(additionalData++);*/
                }
            }
        });
    }

    @Override public void visit(DesignatorFieldSingle designatorFieldSingle) {
        Obj localMeth = findLocalSymbol(designatorFieldSingle.getDesignator().obj, designatorFieldSingle.getId());
        if (localMeth != Tab.noObj && localMeth.getKind() == Obj.Meth) {
            virtualCall = true;
        }
        Code.load(designatorFieldSingle.getDesignator().obj);
        if (this_var == null) {
            this_var = designatorFieldSingle.getDesignator().obj;
        }

        for (Obj tmp : designatorFieldSingle.getDesignator().obj.getType().getMembers().symbols()) {
            if (tmp.getName().equals(designatorFieldSingle.obj.getName())) {
                designatorFieldSingle.obj = tmp;
                break;
            }
        }
    }

    @Override public void visit(DesignatorFieldArray designatorFieldArray) {
        Code.load(designatorFieldArray.getDesignator().obj);


        for (Obj tmp : designatorFieldArray.getDesignator().obj.getType().getMembers().symbols()) {
            if (tmp.getName().equals(designatorFieldArray.obj.getName())) {
                designatorFieldArray.obj = tmp;
                break;
            }
        }

        Code.load(designatorFieldArray.obj);
        Obj arrayIdent = new Obj(Obj.Elem, designatorFieldArray.obj.getName(),
            designatorFieldArray.obj.getType().getElemType());

        designatorFieldArray.obj = arrayIdent;
    }

    @Override public void visit(ArrayFldIdent arrayFldIdent) {
        arrayFldIdent.obj = new Obj(Obj.NO_VALUE, arrayFldIdent.getId(), new Struct(Struct.Array));
    }

    @Override public void visit(DesignatorArrayFld designatorArrayFld) {
        // Code.load(designatorArrayFld.getArray_fld_header().obj);
        designatorArrayFld.obj = designatorArrayFld.getArray_fld_header().obj;
    }

    @Override public void visit(DesignatorThis designatorThis) {
        Code.load(currentMethodThis);

        for(Obj tmp : currentClass.getLocalSymbols()) {
            if (tmp.getName().equals(designatorThis.getId())) {
                designatorThis.obj = tmp;
            }
        }
        /*if (designatorThis.obj == Tab.noObj) {
            String superName = "super." + designatorThis.getId();

            for (int i = 1; designatorThis.obj == Tab.noObj && i < MAX_INHERITANCE_LEVEL; i++) {
                designatorThis.obj = Tab.find(superName);
                superName = "super." + superName;
            }
        }*/
    }

    @Override public void visit(DesignatorThisArray designatorThisArray) {
        //Code.load(currentMethodThis);

        designatorThisArray.obj = designatorThisArray.getArray_ident().obj;
    }

    @Override public void visit(PrintStatement printStatement) {
        if (printStatement.getExpression().obj.getType().getKind() == Struct.None) {
            report_error("Line " + printStatement.getLine() + " unknown type");
        }

        Code.loadConst(0);
        if (printStatement.getExpression().obj.getType() == Tab.intType
            || printStatement.getExpression().obj.getType() == SemanticAnalyzer.boolType.getType()
            || printStatement.getExpression().obj.getType().getElemType() == Tab.intType) {
            Code.put(Code.print);
        } else if (printStatement.getExpression().obj.getType() == Tab.charType
            || printStatement.getExpression().obj.getType().getElemType() == Tab.charType) {
            Code.put(Code.bprint);
        }
    }

    @Override public void visit(ComplexPrintStatement complexPrintStatement) {
        if (complexPrintStatement.getExpression().obj.getType().getKind() == Struct.None) {
            report_error("Line " + complexPrintStatement.getLine() + " unknown type");
        }

        Code.loadConst(complexPrintStatement.getNumeric_const().obj.getAdr());

        if (complexPrintStatement.getExpression().obj.getType() == Tab.intType) {
            Code.put(Code.print);
        } else if (complexPrintStatement.getExpression().obj.getType() == Tab.charType) {
            Code.put(Code.bprint);
        }
    }

    @Override public void visit(ReadStatement readStatement) {
        Designator dest = readStatement.getDesignator();
        if (dest.obj.getKind() == Obj.Var || dest.obj.getKind() == Obj.Elem
            || dest.obj.getKind() == Obj.Fld) {
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

    private boolean compatibleExtention(Obj left, Obj right) {
        Struct derived = right.getType(), base = left.getType();

        if (derived.getKind() == Struct.Array) {
            derived = derived.getElemType();
        }

        if (base.getKind() == Struct.Array) {
            base = base.getElemType();
        }

        return Extention.search(base, derived);
    }

    private boolean checkAssignmentConditions(Obj leftpar, Obj rightpar) {
        Struct left = leftpar.getType(), right = rightpar.getType();

        if (left.getKind() != Struct.Array && right.getKind() != Struct.Array) {
            if (left.getKind() == Struct.Class && right.getKind() == Struct.Class) {
                if (compatibleExtention(leftpar, rightpar)) {
                    leftpar.setLocals(right.getMembers());
                    return true;
                }

                return false;
            } else if ((left.getKind() != Struct.Class && right.getKind() == Struct.Class) || (
                left.getKind() == Struct.Class && right.getKind() != Struct.Class)) {
                return false;
            }

            return right.assignableTo(left);
        } else if (left.getKind() == Struct.Array && right.getKind() != Struct.Array) {
            if (left.getElemType().getKind() == Struct.Class && right.getKind() != Struct.Class) {
                return false;
            } else if (left.getElemType().getKind() != Struct.Class
                && right.getKind() == Struct.Class) {
                return false;
            } else if (left.getElemType().getKind() == Struct.Class
                && right.getKind() == Struct.Class) {
                if (compatibleExtention(leftpar, rightpar)) {
                    //left = new Struct(Struct.Array, new Struct(Struct.Class));
                    //left.getElemType().setMembers(right.getMembers());
                    leftpar.setLocals(right.getMembers());
                    return true;
                }

                return false;
            }

            return right.assignableTo(left.getElemType());
        } else if (left.getKind() == Struct.Array && right.getKind() == Struct.Array) {
            if (left.getElemType().getKind() == Struct.Class
                && right.getElemType().getKind() == Struct.Class) {

                if (compatibleExtention(leftpar, rightpar)) {
                    //left = new Struct(Struct.Array, new Struct(Struct.Class));
                    //left.getElemType().setMembers(right.getElemType().getMembers());
                    leftpar.setLocals(right.getElemType().getMembers());
                    return true;
                }

                return false;
            } else if ((left.getElemType().getKind() != Struct.Class
                && right.getElemType().getKind() == Struct.Class) || (
                left.getElemType().getKind() == Struct.Class
                    && right.getElemType().getKind() != Struct.Class)) {
                return false;
            }

            return right.getElemType().assignableTo(left.getElemType());
        } else if (left.getKind() != Struct.Array && right.getKind() == Struct.Array) {
            if (right.getElemType().getKind() == Struct.Class && left.getKind() != Struct.Class) {
                return false;
            } else if (right.getElemType().getKind() != Struct.Class
                && left.getKind() == Struct.Class) {
                return false;
            } else if (right.getElemType().getKind() == Struct.Class
                && left.getKind() == Struct.Class) {
                if (compatibleExtention(leftpar, rightpar)) {
                    //left = new Struct(Struct.Class);
                    //left.setMembers(right.getElemType().getMembers());
                    leftpar.setLocals(right.getElemType().getMembers());
                    return true;
                }

                return false;
            }
        }

        return false;
    }

    @Override public void visit(Assignment assignment) {
        checkAssignmentConditions(assignment.getDesignator().obj, assignment.getExpression().obj);

        Code.store(assignment.getDesignator().obj);

        if (newTypeObj != null) {
            if (assignment.getDesignator().obj.getKind() == Obj.Elem) {
                Code.load(Tab.find(assignment.getDesignator().obj.getName()));
                CounterVisitor.FindArrayIndex idx = new CounterVisitor.FindArrayIndex();
                assignment.getParent().traverseTopDown(idx);
                Code.loadConst(idx.counter);
            }

            Code.load(assignment.getDesignator().obj);
            Code.loadConst(newTypeObj.getAdr());
            Code.put(Code.putfield);
            Code.put2(0);
            newTypeObj = null;
        }

        this_var = null;
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
        constantFactor.obj = new Obj(Obj.NO_VALUE, "", constantFactor.getConstant().obj.getType());
        constantFactor.obj.setAdr(constantFactor.getConstant().obj.getAdr());
        if (constantFactor.getConstant().obj.getType() == Tab.intType) {
            budzevina = constantFactor.getConstant().obj;
            Code.load(constantFactor.getConstant().obj);
        } else if (constantFactor.getConstant().obj.getType() == Tab.charType) {
            Code.load(constantFactor.getConstant().obj);
        }
    }

    @Override public void visit(ConstructorFactor ConstructorFactor) {
        ConstructorFactor.obj = new Obj(Obj.NO_VALUE, "", ConstructorFactor.getType().struct);
        //report_debug("Line " + ConstructorFactor.getLine() + " found constructor factor");
        Code.put(Code.new_);
        Code.put2(currentType.getNumberOfFields() * 4);
        newTypeObj = Tab.find(ConstructorFactor.getType().getTypeName());
    }

    @Override public void visit(ConstructorArrayFactor ConstructorArrayFactor) {
        ConstructorArrayFactor.obj = new Obj(Obj.NO_VALUE, "",
            new Struct(Struct.Array, ConstructorArrayFactor.getType().struct));
        //report_debug(
        //    "Line " + ConstructorArrayFactor.getLine() + " found array constructor factor");
        Code.put(Code.newarray);
        Code.put(ConstructorArrayFactor.obj.getType() == Tab.charType ? 0 : 1);
    }

    @Override public void visit(ExpressionFactor ExpressionFactor) {
        ExpressionFactor.obj = ExpressionFactor.getExpression().obj;
        //report_debug("Line " + ExpressionFactor.getLine() + " found expression factor");
    }

    @Override public void visit(DesignatorFactor designatorFactor) {
        if (designatorFactor.getDesignator().obj != null) {
            Code.load(designatorFactor.getDesignator().obj);
        }
    }

    @Override public void visit(AddExpression AddExpression) {
        AddExpression.obj = (AddExpression.getAddition_term_list()).obj;
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
        continueNumber.push(0);
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

    @Override public void visit(WhileCondPcFix whileCondPcFix) {
        if (!continueNumber.empty()) {
            int numOfContinue = continueNumber.pop();
            while (numOfContinue > 0) {
                numOfContinue--;
                Code.fixup(continuePc.pop());
            }
        }
    }

    @Override public void visit(ContinueStatement continueStatement) {
        Code.putJump(0);
        continuePc.push(Code.pc - 2);
        continueNumber.push(continueNumber.pop() + 1);
    }

    @Override public void visit(BreakStatement breakStatement) {
        Code.putJump(0);
        breakPc.push(Code.pc - 2);
        breaksNumber.push(breaksNumber.pop() + 1);
    }

    @Override public void visit(Term Term) {
        Term.obj = Term.getMultiplication_factor_list().obj;
    }

    @Override public void visit(FactorList FactorList) {
        Code.put(Integer.parseInt(FactorList.getMultiplication_operator().string));
        FactorList.obj = FactorList.getMultiplication_factor_list().obj;
    }

    @Override public void visit(TerminalFactor TerminalFactor) {
        TerminalFactor.obj = TerminalFactor.getFactor().obj;
    }

    @Override public void visit(TermList TermList) {
        Code.put(Integer.parseInt(TermList.getAddition_operator().string));
        TermList.obj = TermList.getAddition_term_list().obj;
    }

    @Override public void visit(TerminalTerm TerminalTerm) {
        TerminalTerm.obj = TerminalTerm.getTerm().obj;
    }

    @Override public void visit(NegTerminalTerm NegTerminalTerm) {
        Code.put(Code.neg);
        NegTerminalTerm.obj = NegTerminalTerm.getTerm().obj;
    }

    @Override public void visit(MethodCall methodCall) {
        if (!virtualCall) {
            //Obj tmp = findLocalSymbol(
            //    ((MethodCallIdent) methodCall.getMethod_call_ident()).getDesignator().obj, "this");
            //if (tmp != Tab.noObj) {
            //    Code.load(tmp); // load this
            //}

            int offset =
                ((MethodCallIdent) methodCall.getMethod_call_ident()).getDesignator().obj.getAdr()
                    - Code.pc;

            Code.put(Code.call);
            Code.put2(offset);
        } else {
            Code.load(this_var);
            if (this_var.getType().getKind() == Struct.Array) {
                CounterVisitor.FindArrayIndex idx = new CounterVisitor.FindArrayIndex();
                ((MethodCallIdent)methodCall.getMethod_call_ident()).getDesignator().getParent().traverseBottomUp(idx);

                // Code.loadConst(idx.getCounter());
                Code.put(Code.aload);
            }

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

        this_var = null;
    }

    private Obj findLocalSymbol(Obj var, String symbol) {
        Collection<Obj> objs;

        if (var.getKind() == Obj.Elem) {
            objs = var.getType().getMembers().symbols();
        } else {
            objs = var.getLocalSymbols();
        }

        for (Obj tmp : objs) {
            if (tmp.getName().equals(symbol))
                return tmp;
        }

        return Tab.noObj;
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
                CounterVisitor.FindArrayIndex idx = new CounterVisitor.FindArrayIndex();
                ((MethodCallIdent)methodCallDesignator.getMethod_call_ident()).getDesignator().getParent().traverseBottomUp(idx);

               // Code.loadConst(idx.getCounter());
                Code.put(Code.aload);
            }

            Code.put(Code.getfield);
            Code.put2(0);

            Code.put(Code.invokevirtual);

            String methodName =
                ((MethodCallIdent) methodCallDesignator.getMethod_call_ident()).getDesignator().obj
                    .getName();
            Obj tmp = Tab.noObj;
            int i = 0;
            //do {
            tmp = findLocalSymbol(this_var, methodName);
            //                if (tmp != Tab.noObj) {
            //                    break;
            //                }
            //                methodName = "super." + methodName;
            //            } while (tmp == Tab.noObj && i++ < MAX_INHERITANCE_LEVEL);

            for (i = 0; i < methodName.length(); i++) {
                Code.put4(methodName.charAt(i));
            }

            Code.put4(-1);

            virtualCall = false;
        }
        this_var = null;
    }

    @Override public void visit(DesignatorSingle DesignatorSingle) {
        if (insideClass) {
            virtualCall = true;
        }

        Obj tmp = Tab.find(DesignatorSingle.getId());
        if (tmp == Tab.noObj) {
            //tmp = Tab.find(DesignatorSingle.getId());

            if (tmp == Tab.noObj) {
                /*if (insideClass) {
                    String superName = "super." + DesignatorSingle.getId();

                    for (int i = 1; tmp == Tab.noObj && i < MAX_INHERITANCE_LEVEL; i++) {
                        tmp = Tab.find(superName);
                        superName = "super." + superName;
                    }
                    //virtualCall = true;
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
        if (tmp.getKind() == Obj.Meth && currentMethodThis != null) {
            this_var = currentMethodThis;
            if (insideClass) {
                Code.load(this_var);
            }
            //Code.load(currentMethodThis);
        } else {
            if (this_var == null) {
                this_var = tmp;
            }
        }
    }

    @Override public void visit(DesignatorArray designatorArray) {
        designatorArray.obj = designatorArray.getArray_ident().obj;
    }

    @Override public void visit(ArrayIdent arrayIdent) {
        String ident = arrayIdent.getId();
        Obj tmp = Tab.find(ident);
        this_var = tmp;
        if (tmp == Tab.noObj) {
            /*for (int i = 0; i < MAX_INHERITANCE_LEVEL; i++) {
                ident = "super." + ident;
                tmp = Tab.find(ident);

                if (tmp != Tab.noObj) {
                    break;
                }
            }*/
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
        Obj constObj =
            Tab.insert(Obj.Con, ((ConstIdentifier) ConstId.getConst_identifier()).getId(),
                currentType);
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

        if (currentMethodThis == null && !insideClass) {// global var
            tmp.setAdr(additionalData++);
        }
    }

    @Override public void visit(VarID varID) {
        Obj tmp = Tab.insert(insideClass && currentMethod == null ? Obj.Fld : Obj.Var,
            ((VarIdentifier) varID.getVar_identifier()).getId(), currentType);
        tmp.setLevel(level);

        if (currentMethod == null && !insideClass) {// global var
            tmp.setAdr(additionalData++);
        }
    }

    @Override public void visit(FormalParameter FormalParameter) {
        Tab.currentScope().addToLocals(
            new Obj(Obj.Var, FormalParameter.getI2(), FormalParameter.getType().struct, 0, level));
    }

    @Override public void visit(FormalParameterArray FormalParameterArray) {
        Tab.currentScope().addToLocals(new Obj(Obj.Var, FormalParameterArray.getI2(),
            new Struct(Struct.Array, FormalParameterArray.getType().struct), 0, level));
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
