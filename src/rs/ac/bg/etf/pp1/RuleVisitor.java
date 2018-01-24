package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;

public class RuleVisitor extends VisitorAdaptor {
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

    @Override public void visit(Declaration_list declaration_list) {
        super.visit(declaration_list);
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
        super.visit(CharConst);
    }

    @Override public void visit(BoolConst BoolConst) {
        super.visit(BoolConst);
    }

    @Override public void visit(NumericConst NumericConst) {
        super.visit(NumericConst);
    }

    @Override public void visit(ConstChar ConstChar) {
        super.visit(ConstChar);
    }

    @Override public void visit(ConstBool ConstBool) {
        super.visit(ConstBool);
    }

    @Override public void visit(ConstNumber ConstNumber) {
        super.visit(ConstNumber);
    }

    @Override public void visit(ConstIdentifier ConstIdentifier) {
        super.visit(ConstIdentifier);
    }

    @Override public void visit(ConstId ConstId) {
        super.visit(ConstId);
    }

    @Override public void visit(ConstIdTerm ConstIdTerm) {
        super.visit(ConstIdTerm);
    }

    @Override public void visit(ConstIdList ConstIdList) {
        super.visit(ConstIdList);
    }

    @Override public void visit(ConstDeclarationLine ConstDeclarationLine) {
        super.visit(ConstDeclarationLine);
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

    @Override public void visit(DeclarationList DeclarationList) {
        super.visit(DeclarationList);
    }

    @Override public void visit(VoidReturnType VoidReturnType) {
        super.visit(VoidReturnType);
    }

    @Override public void visit(MethodReturnType MethodReturnType) {
        super.visit(MethodReturnType);
    }

    @Override public void visit(Type Type) {
        super.visit(Type);
    }

    @Override public void visit(ProgramName ProgramName) {
        super.visit(ProgramName);

        System.out.println("ProgramName " + ProgramName.getLine());
    }

    @Override public void visit(Program Program) {
        super.visit(Program);

        System.out.println("Program " + Program.getLine());
    }

    @Override public void visit() {
        super.visit();
    }
}
