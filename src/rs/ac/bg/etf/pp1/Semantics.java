package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class Semantics extends VisitorAdaptor {

    static {
        DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    private Logger logger;

    private Struct currentType;

    public Semantics() {
        logger = Logger.getLogger(Semantics.class);

        Tab.init();
        Tab.insert(Obj.Type, "bool", new Struct(Struct.Int));
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

    @Override public void visit(Declaration declaration) {
        super.visit(declaration);
    }

    @Override public void visit(Program_name program_name) {
        super.visit(program_name);
    }

    @Override public void visit(Constant constant) {
        super.visit(constant);
    }

    @Override public void visit(Char_const char_const) {
        super.visit(char_const);
    }

    @Override public void visit(Const_id_list const_id_list) {
        super.visit(const_id_list);
    }

    @Override public void visit(Var_id_list var_id_list) {
        super.visit(var_id_list);
    }

    @Override public void visit(Const_declaration_line const_declaration_line) {
        super.visit(const_declaration_line);
    }

    @Override public void visit(Var_identifier var_identifier) {
        super.visit(var_identifier);
    }

    @Override public void visit(Var_id var_id) {
        super.visit(var_id);
    }

    @Override public void visit(DeclarationSection declarationSection) {
        super.visit(declarationSection);
    }

    @Override public void visit(Var_declaration_line var_declaration_line) {
        super.visit(var_declaration_line);
    }

    @Override public void visit(Const_id const_id) {
        super.visit(const_id);
    }

    @Override public void visit(Bool_const bool_const) {
        super.visit(bool_const);
    }

    @Override public void visit(Const_identifier const_identifier) {
        super.visit(const_identifier);
    }

    @Override public void visit(Numeric_const numeric_const) {
        super.visit(numeric_const);
    }

    @Override public void visit(Method_return_type method_return_type) {
        super.visit(method_return_type);
    }

    @Override public void visit(Method_decl_list method_decl_list) {
        super.visit(method_decl_list);
    }

    @Override public void visit(VarIdentifier VarIdentifier) {
        super.visit(VarIdentifier);
    }

    @Override public void visit(VarIDArray VarIDArray) {
        super.visit(VarIDArray);
    }

    @Override public void visit(VarID VarID) {
        super.visit(VarID);
    }

    @Override public void visit(VarIDTerm VarIDTerm) {
        super.visit(VarIDTerm);
    }

    @Override public void visit(VarIDList VarIDList) {
        super.visit(VarIDList);
    }

    @Override public void visit(VarDeclarationLine VarDeclarationLine) {
        super.visit(VarDeclarationLine);
    }

    @Override public void visit(CharConst CharConst) {
        if (currentType != Tab.charType) {
            report_error("Line " + CharConst.getLine() + " Wrong operand type");
        }

        CharConst.obj =
            new Obj(Obj.Con, "", Tab.charType, Character.getNumericValue(CharConst.getVar()),
                Obj.NO_VALUE);
        report_debug("Line " + CharConst.getLine() + " Found char constant's value");
    }

    @Override public void visit(BoolConst BoolConst) {
        if (currentType != Tab.find("bool").getType()) {
            report_error("Line " + BoolConst.getLine() + " Wrong operand type");
        }

        BoolConst.obj = new Obj(Obj.Con, "", Tab.intType, BoolConst.getVar() ? 1 : 0, Obj.NO_VALUE);
        report_debug("Line " + BoolConst.getLine() + " Found Bool constant");
    }

    @Override public void visit(NumericConst NumericConst) {
        if (currentType != Tab.intType) {
            report_error("Line " + NumericConst.getLine() + " Wrong operand type");
        }

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
        } else {
            constObj.setAdr(ConstId.getConstant().obj.getAdr());
            report_debug("Line " + ConstId.getLine() + " Constant " + ((ConstIdentifier) ConstId
                .getConst_identifier()).getId() + " assigned with value: " + ConstId
                .getConstant().obj.getAdr());
        }
    }

    @Override public void visit(ConstIdTerm ConstIdTerm) {
        super.visit(ConstIdTerm);
    }

    @Override public void visit(ConstIdList ConstIdList) {
        super.visit(ConstIdList);
    }

    @Override public void visit(ConstDeclarationLine ConstDeclarationLine) {
    }

    @Override public void visit(DeclarationVar DeclarationVar) {
        super.visit(DeclarationVar);
    }

    @Override public void visit(DeclarationConst DeclarationConst) {
        super.visit(DeclarationConst);
    }

    @Override public void visit(NoDeclarations NoDeclarations) {
        super.visit(NoDeclarations);
    }

    @Override public void visit(VoidReturnType VoidReturnType) {
        super.visit(VoidReturnType);
    }

    @Override public void visit(MethodReturnType MethodReturnType) {
        super.visit(MethodReturnType);
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
