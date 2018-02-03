package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

import java.util.Collection;

public class SemanticAnalyzer extends VisitorAdaptor {

    public static final Integer MAX_INHERITANCE_LEVEL = 10;
    public static Obj boolType = null;

    static {
        DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    private int returnsFound = 0;
    private Logger logger;
    private Struct currentType;

    private Obj currentMethod, currentMethodCall;
    private boolean foundBool = false;
    private int level = 0;
    private int actualParameterIndex = 0;
    private int controlPaths = 0;
    private boolean insideClass = false;

    public SemanticAnalyzer() {
        logger = Logger.getLogger(SemanticAnalyzer.class);

        Tab.init();
        boolType = Tab.insert(Obj.Type, "bool", new Struct(Struct.Char));
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

    @Override public void visit(VarIDArray VarIDArray) {
        String ident = ((VarIdentifier) VarIDArray.getVar_identifier()).getId();

        if (Tab.find(ident) != Tab.noObj) {
            report_error("Line " + VarIDArray.getLine() + " Symbol already defined");
        } else {
            Obj tmp = Tab.insert(insideClass && currentMethod == null ? Obj.Fld : Obj.Var, ident,
                new Struct(Struct.Array, currentType));
            tmp.setLevel(level);

            report_info("Line " + VarIDArray.getLine() + " Defined array variable: " + ident);
        }
    }

    @Override public void visit(VarID VarID) {
        String ident = ((VarIdentifier) VarID.getVar_identifier()).getId();

        if (Tab.find(ident) != Tab.noObj) {
            report_error("Line " + VarID.getLine() + " Symbol already defined");
        } else {
            Obj tmp = Tab.insert(insideClass && currentMethod == null ? Obj.Fld : Obj.Var, ident,
                currentType);
            tmp.setLevel(level);

            report_info("Line " + VarID.getLine() + " Defined variable: " + ident);
        }
    }

    @Override public void visit(CharConst CharConst) {
        CharConst.obj = new Obj(Obj.Con, "", Tab.charType, (int) CharConst.getVar(), Obj.NO_VALUE);
        report_debug("Line " + CharConst.getLine() + " Found char constant's value");
    }

    @Override public void visit(BoolConst BoolConst) {
        BoolConst.obj =
            new Obj(Obj.Con, "", boolType.getType(), BoolConst.getVar() ? 1 : 0, Obj.NO_VALUE);
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

    @Override public void visit(ConstIdentifier ConstIdentifier) {
        if (Tab.find(ConstIdentifier.getId()) != Tab.noObj) {
            report_error("Line " + ConstIdentifier.getLine() + " Symbol already defined");
        } else {
            Tab.insert(Obj.Con, ConstIdentifier.getId(), currentType);

            report_info(
                "Line " + ConstIdentifier.getLine() + " Defined constant: " + ConstIdentifier
                    .getId());
        }
    }

    @Override public void visit(ConstDeclarationLine constDeclarationLine) {
        foundBool = false;
    }

    @Override public void visit(ConstId ConstId) {
        Obj constObj = Tab.find(((ConstIdentifier) ConstId.getConst_identifier()).getId());
        if (constObj == Tab.noObj) {
            report_error("Line " + ConstId.getLine() + " Assignment into undefined constant");
        } else if (!ConstId.getConstant().obj.getType().compatibleWith(currentType)) {
            report_error("Line " + ConstId.getLine() + " Assignment value not of the same type");
        } else if (foundBool && ConstId.getConstant().obj.getAdr() > 1) {
            report_error("Line " + ConstId.getLine() + " Assignment value not of the same type");
        } else {
            constObj.setAdr(ConstId.getConstant().obj.getAdr());
            report_debug("Line " + ConstId.getLine() + " Constant " + ((ConstIdentifier) ConstId
                .getConst_identifier()).getId() + " assigned with value: " + ConstId
                .getConstant().obj.getAdr());
        }
    }

    @Override public void visit(MethodIdentifier MethodIdentifier) {
        if (Tab.find(MethodIdentifier.getId()) != Tab.noObj) {
            report_error("Line " + MethodIdentifier.getLine() + " Symbol already defined!");
        }

        MethodIdentifier.obj = Tab.insert(Obj.Meth, MethodIdentifier.getId(),
            MethodIdentifier.getMethod_return_type().struct);

        currentMethod = MethodIdentifier.obj;
        Tab.openScope();
        level++;
        returnsFound = 0;
        controlPaths = 1;

        if (insideClass) {
            Tab.insert(Obj.Var, "this", Tab.noType);
        }

        report_info(
            "Line " + MethodIdentifier.getLine() + " Found method " + MethodIdentifier.getId());
    }

    @Override public void visit(FormalParameter FormalParameter) {
        Tab.currentScope().addToLocals(
            new Obj(Obj.Var, FormalParameter.getI2(), FormalParameter.getType().struct, 0, level));
    }

    @Override public void visit(FormalParameterArray FormalParameterArray) {
        Tab.currentScope().addToLocals(new Obj(Obj.Var, FormalParameterArray.getI2(),
            new Struct(Struct.Array, FormalParameterArray.getType().struct), 0, level));
    }

    @Override public void visit(MethodDeclaration methodDeclaration) {
        Tab.chainLocalSymbols(currentMethod);
    }

    @Override public void visit(MethodDefinition MethodDefinition) {
        if (returnsFound == 0 && currentMethod.getType() != Tab.noType) {
            report_error("Line " + MethodDefinition.getLine() + " method missing return statement");
        } /*else if (returnsFound < controlPaths && currentMethod.getType() != Tab.noType) {
            report_error("In method " + currentMethod.getName()
                + " not all control paths have a return statement");
        }*/

        //Tab.chainLocalSymbols(currentMethod);
        Tab.closeScope();

        currentMethod = null;
        returnsFound = 0;
        controlPaths = 1;
        level--;
        report_debug("Line " + MethodDefinition.getLine() + " Method definition complete");
    }

    @Override public void visit(ReturnStatement ReturnStatement) {
        if (!currentMethod.getType().compatibleWith(ReturnStatement.getExpression().struct)) {
            report_error(
                "Line " + ReturnStatement.getLine() + " Wrong operand type in return statement");
        }

        returnsFound++;
        report_debug("Line " + ReturnStatement.getLine() + " found return");
    }

    @Override public void visit(ReturnVoid returnVoid) {
        if (currentMethod.getType() != Tab.noType) {
            report_error("Line " + returnVoid.getLine() + " Return statement needs an operand");
        }

        returnsFound++;
    }

    @Override public void visit(ConstantFactor ConstantFactor) {
        ConstantFactor.struct = ConstantFactor.getConstant().obj.getType();
        //report_debug("Line " + ConstantFactor.getLine() + " found constant factor");
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
    }

    @Override public void visit(ExpressionFactor ExpressionFactor) {
        ExpressionFactor.struct = ExpressionFactor.getExpression().struct;
        //report_debug("Line " + ExpressionFactor.getLine() + " found expression factor");
    }

    @Override public void visit(DesignatorFactor designatorFactor) {
        designatorFactor.struct = designatorFactor.getDesignator().obj.getType();
    }

    @Override public void visit(AddExpression AddExpression) {
        AddExpression.struct = (AddExpression.getAddition_term_list()).struct;
        //report_debug("Line " + AddExpression.getLine() + " found add expression");
    }

    @Override public void visit(Term Term) {
        Term.struct = Term.getMultiplication_factor_list().struct;
    }

    @Override public void visit(FactorList FactorList) {
        if (FactorList.getFactor().struct != Tab.intType
            || FactorList.getMultiplication_factor_list().struct != Tab.intType) {
            report_error("Line " + FactorList.getLine() + " Only integer arithmetic is supported");
        }

        FactorList.struct = FactorList.getMultiplication_factor_list().struct;
    }

    @Override public void visit(TerminalFactor TerminalFactor) {
        TerminalFactor.struct = TerminalFactor.getFactor().struct;
    }

    @Override public void visit(TermList TermList) {
        if (TermList.getTerm().struct != Tab.intType
            || TermList.getAddition_term_list().struct != Tab.intType) {
            report_error("Line " + TermList.getLine() + " Only integer arithmetic is supported");
        }

        TermList.struct = TermList.getAddition_term_list().struct;
    }

    @Override public void visit(TerminalTerm TerminalTerm) {
        TerminalTerm.struct = TerminalTerm.getTerm().struct;
    }

    @Override public void visit(NegTerminalTerm NegTerminalTerm) {
        NegTerminalTerm.struct = NegTerminalTerm.getTerm().struct;
    }

    @Override public void visit(Assignment assignment) {
        Obj tmp = assignment.getDesignator().obj;

        if (tmp.getKind() == Obj.Var || tmp.getKind() == Obj.Elem || tmp.getKind() == Obj.Fld) {
            if (!assignment.getExpression().struct.assignableTo(tmp.getType())) {
                report_error("Line " + assignment.getLine() + " assignment types not compatible");
            }
        } else {
            report_error("Line " + assignment.getLine()
                + " assignment left side must be variable or array element");
        }

    }

    @Override public void visit(Increment increment) {
        Obj tmp = increment.getDesignator().obj;

        if (tmp.getKind() == Obj.Var || tmp.getKind() == Obj.Elem) {
            if (!tmp.getType().compatibleWith(Tab.intType)) {
                report_error("Line " + increment.getLine() + " only integers can be incremented");
            }
        } else {
            report_error("Line " + increment.getLine() + " wrong operand type");
        }
    }

    @Override public void visit(Decrement decrement) {
        Obj tmp = decrement.getDesignator().obj;

        if (tmp.getKind() == Obj.Var || tmp.getKind() == Obj.Elem) {
            if (!tmp.getType().compatibleWith(Tab.intType)) {
                report_error("Line " + decrement.getLine() + " only integers can be decremented");
            }
        } else {
            report_error("Line " + decrement.getLine() + " wrong operand type");
        }
    }

    @Override public void visit(DesignatorSingle DesignatorSingle) {
        Obj tmp = Tab.find(DesignatorSingle.getId());
        if (tmp == Tab.noObj) {

            /*if (insideClass) { // maybe we need inherited fld/meth
                String superName = "super." + DesignatorSingle.getId();

                for (int i = 1; tmp == Tab.noObj && i < MAX_INHERITANCE_LEVEL; i++) {
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

        DesignatorSingle.obj = tmp;
    }

    @Override public void visit(DesignatorArray designatorArray) {
        if (!designatorArray.getExpression().struct.compatibleWith(Tab.intType)) {
            report_error("Line " + designatorArray.getLine() + " array index must be an integer");
        }

        designatorArray.obj = designatorArray.getArray_ident().obj;
    }

    private Obj findLocalSymbol(Obj var, String symbol) {
        Collection<Obj> objs = var.getType().getMembers().symbols();
        for (Obj tmp : objs) {
            if (tmp.getName().equals(symbol))
                return tmp;
        }

        return Tab.noObj;
    }

    @Override public void visit(DesignatorFieldSingle designatorFieldSingle) {
        Obj tmp = findLocalSymbol(designatorFieldSingle.getDesignator().obj,
            designatorFieldSingle.getId());

        if (tmp == Tab.noObj) {
            /*if (designatorFieldSingle.getDesignator().obj.getType().getKind() == Struct.Class) { // maybe we need inherited fld/meth
                String superName = "super." + designatorFieldSingle.getId();

                for (int i = 1; tmp == Tab.noObj && i < MAX_INHERITANCE_LEVEL; i++) {
                    tmp = findLocalSymbol(designatorFieldSingle.getDesignator().obj, superName);
                    superName = "super." + superName;
                }
            }*/

            if (tmp == Tab.noObj) {
                report_error(
                    "Line " + designatorFieldSingle.getLine() + " Name " + designatorFieldSingle
                        .getId() + " not found");
            }
        }

        designatorFieldSingle.obj = tmp;
    }

    @Override public void visit(DesignatorFieldArray designatorFieldArray) {
        Obj tmp = findLocalSymbol(designatorFieldArray.getDesignator().obj,
            ((ArrayFldIdent) designatorFieldArray.getArray_fld_ident()).getId());

        if (tmp == Tab.noObj) {
            report_error("Line " + designatorFieldArray.getLine() + " Name "
                + ((ArrayFldIdent) designatorFieldArray.getArray_fld_ident()).getId()
                + " not found");
        }

        designatorFieldArray.obj = tmp;
    }

    @Override public void visit(DesignatorArrayFld designatorArrayFld) {
        designatorArrayFld.obj = designatorArrayFld.getArray_fld_header().obj;
    }

    @Override public void visit(ArrayIdent arrayIdent) {
        String ident = arrayIdent.getId();
        Obj tmp = Tab.find(ident);
        if (tmp == Tab.noObj) {
            report_error("Line " + arrayIdent.getLine() + " name " + ident + " not declared");
        }

        arrayIdent.obj = new Obj(Obj.Elem, tmp.getName(), tmp.getType().getElemType());
    }

    @Override public void visit(DesignatorThis designatorThis) {
        if (!insideClass) {
            report_error(
                "Line " + designatorThis.getLine() + " 'this' can only be used in class methods");
        }

        designatorThis.obj = Tab.find(designatorThis.getId());

        if (designatorThis.obj == Tab.noObj) {
            report_error("Line " + designatorThis.getLine() + " name " + designatorThis.getId()
                + " not declared");
        }
    }

    @Override public void visit(MethodCallIdent methodCallIdent) {
        currentMethodCall = methodCallIdent.getDesignator().obj;
        actualParameterIndex = 0;
    }

    @Override public void visit(MethodCall methodCall) {
        methodCall.struct = currentMethodCall.getType();
    }

    @Override public void visit(MethodCallDesignator methodCallDesignator) {
    }

    @Override public void visit(ActualParameter actualParameter) {
        Obj[] params = currentMethodCall.getLocalSymbols()
            .toArray(new Obj[currentMethodCall.getLocalSymbols().size()]);

        if (params[0].getName().equals("this")) {
            actualParameterIndex++;
        }

        if (!params[actualParameterIndex].getType()
            .compatibleWith(actualParameter.getExpression().struct)) {
            report_error(
                "Line " + actualParameter.getLine() + " Parameter " + (actualParameterIndex + 1)
                    + " wrong type");
        }

        actualParameterIndex++;
    }

    @Override public void visit(TermCondFactor termCondFactor) {
        if (termCondFactor.getExpression().struct != boolType.getType()) {
            report_error("Line " + termCondFactor.getLine() + " operand not boolean type");
        }

        termCondFactor.struct = termCondFactor.getExpression().struct;
    }

    @Override public void visit(CondOpFactor condOpFactor) {
        if (!condOpFactor.getExpression().struct
            .compatibleWith(condOpFactor.getExpression1().struct)) {
            report_error("Line " + condOpFactor.getLine() + " wrong operand types");
        }

        condOpFactor.struct = boolType.getType();
    }

    @Override public void visit(TerminalCondFactor terminalCondFactor) {
        terminalCondFactor.struct = terminalCondFactor.getCondition_factor().struct;
    }

    @Override public void visit(CondFactorList condFactorList) {
        if (condFactorList.getCondition_factor().struct != boolType.getType()
            || condFactorList.getCondition_factor_list().struct != boolType.getType()) {
            report_error("Line " + condFactorList.getLine() + " Incompatible types");
        }

        condFactorList.struct = condFactorList.getCondition_factor().struct;
    }

    @Override public void visit(CondTerm condTerm) {
        condTerm.struct = condTerm.getCondition_factor_list().struct;
    }

    @Override public void visit(CondTermList condTermList) {
        if (condTermList.getCondition_term().struct != boolType.getType()
            || condTermList.getCondition_term_list().struct != boolType.getType()) {
            report_error("Line " + condTermList.getLine() + " Incompatible types");
        }

        condTermList.struct = condTermList.getCondition_term().struct;
    }

    @Override public void visit(CondTerminalTerm condTerminalTerm) {
        condTerminalTerm.struct = condTerminalTerm.getCondition_term().struct;
    }

    @Override public void visit(CondExpression condExpression) {
        condExpression.struct = condExpression.getCondition_term_list().struct;
    }

    @Override public void visit(IfStatement ifStatement) {
        controlPaths++;
    }

    @Override public void visit(DoStatement doStatement) {
        controlPaths++;
    }

    @Override public void visit(ClassDeclaration classDeclaration) {
        classDeclaration.obj = classDeclaration.getClass_identifier().obj;
        classDeclaration.obj.getType().setMembers(Tab.currentScope().getLocals());

        Tab.chainLocalSymbols(classDeclaration.obj);
        Tab.closeScope();
        insideClass = false;
    }

    @Override public void visit(ClassIdentifier classIdentifier) {
        classIdentifier.obj =
            Tab.insert(Obj.Type, classIdentifier.getId(), new Struct(Struct.Class));
        Tab.openScope();
        insideClass = true;
    }

    @Override public void visit(Extends extend) {
        Obj parentObj = Tab.find(extend.getType().getTypeName());

        if (parentObj == Tab.noObj) {
            report_error("Line " + extend.getLine() + " Base class identifier not found");
        }

        if (parentObj.getType().getKind() != Struct.Class) {
            report_error("Line " + extend.getLine() + " Cannot extend a non-class type");
        }

        parentObj.getType().getMembers().symbols().forEach(tmp -> {
            if (!tmp.getName().equals("_vtable")) {
                Obj inherited = Tab.insert(tmp.getKind(), tmp.getName(), tmp.getType());
                inherited.setAdr(tmp.getAdr());

                if (tmp.getKind() == Obj.Meth) {
                    Tab.openScope();
                    tmp.getLocalSymbols().forEach(member -> Tab.insert(member.getKind(), member.getName(), member.getType()));
                    Tab.chainLocalSymbols(inherited);
                    Tab.closeScope();
                }
            }
        });
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
        if (typeNode.getName().equals("bool")) {
            foundBool = true;
            Type.struct = boolType.getType();
        }
        report_debug("Type " + Type.getLine());
    }

    @Override public void visit(MethodReturnType MethodReturnType) {
        MethodReturnType.struct = MethodReturnType.getType().struct;
    }

    @Override public void visit(VoidReturnType VoidReturnType) {
        VoidReturnType.struct = Tab.noType;
    }

    @Override public void visit(ProgramName ProgramName) {
        ProgramName.obj = Tab.insert(Obj.Prog, ProgramName.getId(), Tab.noType);
        Tab.openScope();
        //report_debug("Line " + ProgramName.getLine() + " ");
    }

    @Override public void visit(Program Program) {
        Tab.chainLocalSymbols(Program.getProgram_name().obj);
        Tab.closeScope();
        //report_debug("Program " + Program.getLine());
    }

    @Override public void visit() {
        super.visit();
    }
}
