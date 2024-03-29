// generated with ast extension for cup
// version 0.8
// 6/1/2018 21:51:44


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Or_operator or_operator);
    public void visit(Var_declaration_line var_declaration_line);
    public void visit(Array_fld_ident array_fld_ident);
    public void visit(Optional_else optional_else);
    public void visit(Method_decl method_decl);
    public void visit(Class_declaration class_declaration);
    public void visit(Optional_extends optional_extends);
    public void visit(Statement_list statement_list);
    public void visit(Array_fld_fix array_fld_fix);
    public void visit(Var_declaration_list var_declaration_list);
    public void visit(Rel_operator rel_operator);
    public void visit(Const_identifier const_identifier);
    public void visit(Declaration_section declaration_section);
    public void visit(Multiplication_operator multiplication_operator);
    public void visit(Array_fld_header array_fld_header);
    public void visit(Method_call_ident method_call_ident);
    public void visit(Condition_term condition_term);
    public void visit(Chain_vars_fix chain_vars_fix);
    public void visit(Break_statement break_statement);
    public void visit(Char_const char_const);
    public void visit(Program_name program_name);
    public void visit(Const_id const_id);
    public void visit(And_operator and_operator);
    public void visit(Actual_parameter actual_parameter);
    public void visit(Return_statement return_statement);
    public void visit(Constant constant);
    public void visit(If_statement_header if_statement_header);
    public void visit(Condition condition);
    public void visit(Read_statement read_statement);
    public void visit(Var_id var_id);
    public void visit(Optional_method_decl_section optional_method_decl_section);
    public void visit(Const_declaration_line const_declaration_line);
    public void visit(Begin_cond_pc_fix begin_cond_pc_fix);
    public void visit(Var_identifier var_identifier);
    public void visit(Formal_parameter_list formal_parameter_list);
    public void visit(If_statement if_statement);
    public void visit(Addition_operator addition_operator);
    public void visit(Formal_parameter_section formal_parameter_section);
    public void visit(Actual_parameter_section actual_parameter_section);
    public void visit(Condition_factor_list condition_factor_list);
    public void visit(Class_identifier class_identifier);
    public void visit(Const_id_list const_id_list);
    public void visit(Multiplication_factor_list multiplication_factor_list);
    public void visit(Array_ident array_ident);
    public void visit(Continue_statement continue_statement);
    public void visit(Bool_const bool_const);
    public void visit(Formal_parameter formal_parameter);
    public void visit(Expression expression);
    public void visit(Condition_term_list condition_term_list);
    public void visit(Addition_term_list addition_term_list);
    public void visit(Print_statement print_statement);
    public void visit(Method_decl_list method_decl_list);
    public void visit(Designator_statement designator_statement);
    public void visit(Remember_pc remember_pc);
    public void visit(Designator designator);
    public void visit(Var_id_list var_id_list);
    public void visit(Else_jmp_fix else_jmp_fix);
    public void visit(Method_identifier method_identifier);
    public void visit(Formal_param_num_fix formal_param_num_fix);
    public void visit(Actual_parameter_list actual_parameter_list);
    public void visit(Method_definition method_definition);
    public void visit(Statement statement);
    public void visit(Factor factor);
    public void visit(Method_decl_section method_decl_section);
    public void visit(Do_statement do_statement);
    public void visit(Declaration declaration);
    public void visit(Condition_factor condition_factor);
    public void visit(Numeric_const numeric_const);
    public void visit(Method_return_type method_return_type);
    public void visit(OperatorLeq OperatorLeq);
    public void visit(OperatorLss OperatorLss);
    public void visit(OperatorGrteq OperatorGrteq);
    public void visit(OperatorGrt OperatorGrt);
    public void visit(OperatorNeq OperatorNeq);
    public void visit(OperatorEq OperatorEq);
    public void visit(CondOpFactor CondOpFactor);
    public void visit(TermCondFactor TermCondFactor);
    public void visit(OperatorAnd OperatorAnd);
    public void visit(TerminalCondFactor TerminalCondFactor);
    public void visit(CondFactorList CondFactorList);
    public void visit(CondTerm CondTerm);
    public void visit(OperatorOr OperatorOr);
    public void visit(CondTerminalTerm CondTerminalTerm);
    public void visit(CondTermList CondTermList);
    public void visit(ConditionDerived1 ConditionDerived1);
    public void visit(CondExpression CondExpression);
    public void visit(MethodCall MethodCall);
    public void visit(DesignatorFactor DesignatorFactor);
    public void visit(ConstructorArrayFactor ConstructorArrayFactor);
    public void visit(ConstructorFactor ConstructorFactor);
    public void visit(ExpressionFactor ExpressionFactor);
    public void visit(ConstantFactor ConstantFactor);
    public void visit(SignMod SignMod);
    public void visit(SignDiv SignDiv);
    public void visit(SignMul SignMul);
    public void visit(TerminalFactor TerminalFactor);
    public void visit(FactorList FactorList);
    public void visit(Term Term);
    public void visit(SignSub SignSub);
    public void visit(SignAdd SignAdd);
    public void visit(NegTerminalTerm NegTerminalTerm);
    public void visit(TerminalTerm TerminalTerm);
    public void visit(TermList TermList);
    public void visit(AddExpression AddExpression);
    public void visit(ActualParameter ActualParameter);
    public void visit(Actual_parameter_listDerived2 Actual_parameter_listDerived2);
    public void visit(Actual_parameter_listDerived1 Actual_parameter_listDerived1);
    public void visit(Actual_parameter_sectionDerived2 Actual_parameter_sectionDerived2);
    public void visit(Actual_parameter_sectionDerived1 Actual_parameter_sectionDerived1);
    public void visit(Array_fld_fixDerived1 Array_fld_fixDerived1);
    public void visit(DesignatorFieldArray DesignatorFieldArray);
    public void visit(ArrayFldIdent ArrayFldIdent);
    public void visit(ArrayIdent ArrayIdent);
    public void visit(DesignatorThisArray DesignatorThisArray);
    public void visit(DesignatorThis DesignatorThis);
    public void visit(DesignatorArrayFld DesignatorArrayFld);
    public void visit(DesignatorFieldSingle DesignatorFieldSingle);
    public void visit(DesignatorArray DesignatorArray);
    public void visit(DesignatorSingle DesignatorSingle);
    public void visit(MethodCallIdent MethodCallIdent);
    public void visit(MethodCallDesignator MethodCallDesignator);
    public void visit(Decrement Decrement);
    public void visit(Increment Increment);
    public void visit(Designator_statementDerived1 Designator_statementDerived1);
    public void visit(Assignment Assignment);
    public void visit(Continue_statementDerived1 Continue_statementDerived1);
    public void visit(Break_statementDerived1 Break_statementDerived1);
    public void visit(RememberPc RememberPc);
    public void visit(WhileCondPcFix WhileCondPcFix);
    public void visit(Do_statementDerived1 Do_statementDerived1);
    public void visit(ElseFix ElseFix);
    public void visit(Optional_elseDerived2 Optional_elseDerived2);
    public void visit(Optional_elseDerived1 Optional_elseDerived1);
    public void visit(If_statement_headerDerived1 If_statement_headerDerived1);
    public void visit(IfStatement IfStatement);
    public void visit(ComplexPrintStatement ComplexPrintStatement);
    public void visit(PrintStatement PrintStatement);
    public void visit(ReadStatement ReadStatement);
    public void visit(ReturnVoid ReturnVoid);
    public void visit(ReturnStatement ReturnStatement);
    public void visit(StatementDerived6 StatementDerived6);
    public void visit(ContinueStatement ContinueStatement);
    public void visit(BreakStatement BreakStatement);
    public void visit(DoStatement DoStatement);
    public void visit(StatementDerived5 StatementDerived5);
    public void visit(StatementDerived4 StatementDerived4);
    public void visit(StatementDerived3 StatementDerived3);
    public void visit(StatementDerived2 StatementDerived2);
    public void visit(StatementDerived1 StatementDerived1);
    public void visit(NoStatements NoStatements);
    public void visit(StatementList StatementList);
    public void visit(MethodDefinition MethodDefinition);
    public void visit(FormalParameterArray FormalParameterArray);
    public void visit(FormalParameter FormalParameter);
    public void visit(Formal_parameter_listDerived1 Formal_parameter_listDerived1);
    public void visit(FormalTermParameter FormalTermParameter);
    public void visit(FormalParameterList FormalParameterList);
    public void visit(NoParameters NoParameters);
    public void visit(Formal_parameter_sectionDerived1 Formal_parameter_sectionDerived1);
    public void visit(FormalParameters FormalParameters);
    public void visit(MethodIdentifier MethodIdentifier);
    public void visit(FormalParamNumFix FormalParamNumFix);
    public void visit(MethodDeclaration MethodDeclaration);
    public void visit(MethodTerm MethodTerm);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(MethodDeclSection MethodDeclSection);
    public void visit(NoMethodSection NoMethodSection);
    public void visit(OptionalMethodDeclSection OptionalMethodDeclSection);
    public void visit(Optional_extendsDerived2 Optional_extendsDerived2);
    public void visit(Optional_extendsDerived1 Optional_extendsDerived1);
    public void visit(Extends Extends);
    public void visit(ClassIdentifier ClassIdentifier);
    public void visit(ChainVars ChainVars);
    public void visit(ClassDeclaration ClassDeclaration);
    public void visit(VarIdentifier VarIdentifier);
    public void visit(VarIDArray VarIDArray);
    public void visit(VarID VarID);
    public void visit(Var_id_listDerived1 Var_id_listDerived1);
    public void visit(VarIDTerm VarIDTerm);
    public void visit(VarIDList VarIDList);
    public void visit(Var_declaration_lineDerived1 Var_declaration_lineDerived1);
    public void visit(VarDeclarationLine VarDeclarationLine);
    public void visit(NoVariableDeclarations NoVariableDeclarations);
    public void visit(VarDeclarationList VarDeclarationList);
    public void visit(CharConst CharConst);
    public void visit(BoolConst BoolConst);
    public void visit(NumericConst NumericConst);
    public void visit(ConstChar ConstChar);
    public void visit(ConstBool ConstBool);
    public void visit(ConstNumber ConstNumber);
    public void visit(ConstIdentifier ConstIdentifier);
    public void visit(ConstId ConstId);
    public void visit(ConstIdTerm ConstIdTerm);
    public void visit(ConstIdList ConstIdList);
    public void visit(ConstDeclarationLine ConstDeclarationLine);
    public void visit(DeclarationClass DeclarationClass);
    public void visit(DeclarationVar DeclarationVar);
    public void visit(DeclarationConst DeclarationConst);
    public void visit(NoDeclarations NoDeclarations);
    public void visit(DeclarationSection DeclarationSection);
    public void visit(VoidReturnType VoidReturnType);
    public void visit(MethodReturnType MethodReturnType);
    public void visit(Type Type);
    public void visit(ProgramName ProgramName);
    public void visit(Program Program);

}
