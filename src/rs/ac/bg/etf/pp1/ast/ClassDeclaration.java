// generated with ast extension for cup
// version 0.8
// 6/1/2018 21:51:44


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclaration extends Class_declaration {

    private Class_identifier class_identifier;
    private Optional_extends optional_extends;
    private Var_declaration_list var_declaration_list;
    private Chain_vars_fix chain_vars_fix;
    private Optional_method_decl_section optional_method_decl_section;

    public ClassDeclaration (Class_identifier class_identifier, Optional_extends optional_extends, Var_declaration_list var_declaration_list, Chain_vars_fix chain_vars_fix, Optional_method_decl_section optional_method_decl_section) {
        this.class_identifier=class_identifier;
        if(class_identifier!=null) class_identifier.setParent(this);
        this.optional_extends=optional_extends;
        if(optional_extends!=null) optional_extends.setParent(this);
        this.var_declaration_list=var_declaration_list;
        if(var_declaration_list!=null) var_declaration_list.setParent(this);
        this.chain_vars_fix=chain_vars_fix;
        if(chain_vars_fix!=null) chain_vars_fix.setParent(this);
        this.optional_method_decl_section=optional_method_decl_section;
        if(optional_method_decl_section!=null) optional_method_decl_section.setParent(this);
    }

    public Class_identifier getClass_identifier() {
        return class_identifier;
    }

    public void setClass_identifier(Class_identifier class_identifier) {
        this.class_identifier=class_identifier;
    }

    public Optional_extends getOptional_extends() {
        return optional_extends;
    }

    public void setOptional_extends(Optional_extends optional_extends) {
        this.optional_extends=optional_extends;
    }

    public Var_declaration_list getVar_declaration_list() {
        return var_declaration_list;
    }

    public void setVar_declaration_list(Var_declaration_list var_declaration_list) {
        this.var_declaration_list=var_declaration_list;
    }

    public Chain_vars_fix getChain_vars_fix() {
        return chain_vars_fix;
    }

    public void setChain_vars_fix(Chain_vars_fix chain_vars_fix) {
        this.chain_vars_fix=chain_vars_fix;
    }

    public Optional_method_decl_section getOptional_method_decl_section() {
        return optional_method_decl_section;
    }

    public void setOptional_method_decl_section(Optional_method_decl_section optional_method_decl_section) {
        this.optional_method_decl_section=optional_method_decl_section;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(class_identifier!=null) class_identifier.accept(visitor);
        if(optional_extends!=null) optional_extends.accept(visitor);
        if(var_declaration_list!=null) var_declaration_list.accept(visitor);
        if(chain_vars_fix!=null) chain_vars_fix.accept(visitor);
        if(optional_method_decl_section!=null) optional_method_decl_section.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(class_identifier!=null) class_identifier.traverseTopDown(visitor);
        if(optional_extends!=null) optional_extends.traverseTopDown(visitor);
        if(var_declaration_list!=null) var_declaration_list.traverseTopDown(visitor);
        if(chain_vars_fix!=null) chain_vars_fix.traverseTopDown(visitor);
        if(optional_method_decl_section!=null) optional_method_decl_section.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(class_identifier!=null) class_identifier.traverseBottomUp(visitor);
        if(optional_extends!=null) optional_extends.traverseBottomUp(visitor);
        if(var_declaration_list!=null) var_declaration_list.traverseBottomUp(visitor);
        if(chain_vars_fix!=null) chain_vars_fix.traverseBottomUp(visitor);
        if(optional_method_decl_section!=null) optional_method_decl_section.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclaration(\n");

        if(class_identifier!=null)
            buffer.append(class_identifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(optional_extends!=null)
            buffer.append(optional_extends.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(var_declaration_list!=null)
            buffer.append(var_declaration_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(chain_vars_fix!=null)
            buffer.append(chain_vars_fix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(optional_method_decl_section!=null)
            buffer.append(optional_method_decl_section.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclaration]");
        return buffer.toString();
    }
}
