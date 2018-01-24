// generated with ast extension for cup
// version 0.8
// 24/0/2018 22:42:39


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclaration extends Class_declaration {

    private Class_identifier class_identifier;
    private Var_declaration_list var_declaration_list;
    private Optional_method_decl_section optional_method_decl_section;

    public ClassDeclaration (Class_identifier class_identifier, Var_declaration_list var_declaration_list, Optional_method_decl_section optional_method_decl_section) {
        this.class_identifier=class_identifier;
        if(class_identifier!=null) class_identifier.setParent(this);
        this.var_declaration_list=var_declaration_list;
        if(var_declaration_list!=null) var_declaration_list.setParent(this);
        this.optional_method_decl_section=optional_method_decl_section;
        if(optional_method_decl_section!=null) optional_method_decl_section.setParent(this);
    }

    public Class_identifier getClass_identifier() {
        return class_identifier;
    }

    public void setClass_identifier(Class_identifier class_identifier) {
        this.class_identifier=class_identifier;
    }

    public Var_declaration_list getVar_declaration_list() {
        return var_declaration_list;
    }

    public void setVar_declaration_list(Var_declaration_list var_declaration_list) {
        this.var_declaration_list=var_declaration_list;
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
        if(var_declaration_list!=null) var_declaration_list.accept(visitor);
        if(optional_method_decl_section!=null) optional_method_decl_section.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(class_identifier!=null) class_identifier.traverseTopDown(visitor);
        if(var_declaration_list!=null) var_declaration_list.traverseTopDown(visitor);
        if(optional_method_decl_section!=null) optional_method_decl_section.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(class_identifier!=null) class_identifier.traverseBottomUp(visitor);
        if(var_declaration_list!=null) var_declaration_list.traverseBottomUp(visitor);
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

        if(var_declaration_list!=null)
            buffer.append(var_declaration_list.toString("  "+tab));
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
