package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {

    static {
        DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    boolean returnFound = false;
    private Logger logger;
    private Struct currentType;

    private Obj currentMethod;
    private int level = 0;

    public SemanticAnalyzer() {
        logger = Logger.getLogger(SemanticAnalyzer.class);

        Tab.init();
        Tab.insert(Obj.Type, "bool", Tab.intType);
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
            Obj tmp = Tab.insert(Obj.Var, ident, new Struct(Struct.Array, currentType));
            tmp.setLevel(level);

            report_info("Line " + VarIDArray.getLine() + " Defined array variable: " + ident);
        }
    }

    @Override public void visit(VarID VarID) {
        String ident = ((VarIdentifier) VarID.getVar_identifier()).getId();

        if (Tab.find(ident) != Tab.noObj) {
            report_error("Line " + VarID.getLine() + " Symbol already defined");
        } else {
            Obj tmp = Tab.insert(Obj.Var, ident, currentType);
            tmp.setLevel(level);

            report_info("Line " + VarID.getLine() + " Defined variable: " + ident);
        }
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

    @Override public void visit(ConstId ConstId) {
        Obj constObj = Tab.find(((ConstIdentifier) ConstId.getConst_identifier()).getId());
        if (constObj == Tab.noObj) {
            report_error("Line " + ConstId.getLine() + " Assignment into undefined constant");
        } else if (!ConstId.getConstant().obj.getType().compatibleWith(currentType)) {
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
        returnFound = false;
        level++;

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

    @Override public void visit(MethodDefinition MethodDefinition) {
        if (!returnFound && currentMethod.getType() != Tab.noType) {
            report_error("Line " + MethodDefinition.getLine() + " method missing return statement");
        }

        Tab.chainLocalSymbols(currentMethod);
        Tab.closeScope();

        currentMethod = null;
        returnFound = false;
        level--;
        report_debug("Line " + MethodDefinition.getLine() + " Method definition complete");
    }

    @Override public void visit(ReturnStatement ReturnStatement) {
        if (!currentMethod.getType().compatibleWith(ReturnStatement.getExpression().struct)) {
            report_error(
                "Line " + ReturnStatement.getLine() + " Wrong operand type in return statement");
        }

        returnFound = true;
        report_debug("Line " + ReturnStatement.getLine() + " found return");
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
        ConstructorArrayFactor.struct = ConstructorArrayFactor.getType().struct;
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
        if (!FactorList.getFactor().struct
            .compatibleWith(FactorList.getMultiplication_factor_list().struct)) {
            report_error("Line " + FactorList.getLine() + " Types not compatible");
        }

        FactorList.struct = FactorList.getMultiplication_factor_list().struct;
    }

    @Override public void visit(TerminalFactor TerminalFactor) {
        TerminalFactor.struct = TerminalFactor.getFactor().struct;
    }

    @Override public void visit(TermList TermList) {
        if (!TermList.getTerm().struct.compatibleWith(TermList.getAddition_term_list().struct)) {
            report_error("Line " + TermList.getLine() + " Types not compatible");
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

        if (tmp.getKind() == Obj.Var || tmp.getKind() == Obj.Elem) {
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
            report_error("Line " + DesignatorSingle.getLine() + " name " + DesignatorSingle.getId()
                + " not declared");
        }

        DesignatorSingle.obj = tmp;
    }

    @Override public void visit(DesignatorArray designatorArray) {
        Obj tmp = Tab.find(designatorArray.getId());
        if (tmp == Tab.noObj) {
            report_error("Line " + designatorArray.getLine() + " name " + designatorArray.getId()
                + " not declared");
        }

        if (!designatorArray.getExpression().struct.compatibleWith(Tab.intType)) {
            report_error("Line " + designatorArray.getLine() + " array index must be an integer");
        }

        designatorArray.obj = tmp;
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
