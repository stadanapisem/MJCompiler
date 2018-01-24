// generated with ast extension for cup
// version 0.8
// 24/0/2018 22:42:39


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclaration extends Method_decl {

    private Method_return_type method_return_type;
    private Method_identifier method_identifier;
    private Formal_parameter_section formal_parameter_section;
    private Var_declaration_list var_declaration_list;

    public MethodDeclaration (Method_return_type method_return_type, Method_identifier method_identifier, Formal_parameter_section formal_parameter_section, Var_declaration_list var_declaration_list) {
        this.method_return_type=method_return_type;
        if(method_return_type!=null) method_return_type.setParent(this);
        this.method_identifier=method_identifier;
        if(method_identifier!=null) method_identifier.setParent(this);
        this.formal_parameter_section=formal_parameter_section;
        if(formal_parameter_section!=null) formal_parameter_section.setParent(this);
        this.var_declaration_list=var_declaration_list;
        if(var_declaration_list!=null) var_declaration_list.setParent(this);
    }

    public Method_return_type getMethod_return_type() {
        return method_return_type;
    }

    public void setMethod_return_type(Method_return_type method_return_type) {
        this.method_return_type=method_return_type;
    }

    public Method_identifier getMethod_identifier() {
        return method_identifier;
    }

    public void setMethod_identifier(Method_identifier method_identifier) {
        this.method_identifier=method_identifier;
    }

    public Formal_parameter_section getFormal_parameter_section() {
        return formal_parameter_section;
    }

    public void setFormal_parameter_section(Formal_parameter_section formal_parameter_section) {
        this.formal_parameter_section=formal_parameter_section;
    }

    public Var_declaration_list getVar_declaration_list() {
        return var_declaration_list;
    }

    public void setVar_declaration_list(Var_declaration_list var_declaration_list) {
        this.var_declaration_list=var_declaration_list;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_return_type!=null) method_return_type.accept(visitor);
        if(method_identifier!=null) method_identifier.accept(visitor);
        if(formal_parameter_section!=null) formal_parameter_section.accept(visitor);
        if(var_declaration_list!=null) var_declaration_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_return_type!=null) method_return_type.traverseTopDown(visitor);
        if(method_identifier!=null) method_identifier.traverseTopDown(visitor);
        if(formal_parameter_section!=null) formal_parameter_section.traverseTopDown(visitor);
        if(var_declaration_list!=null) var_declaration_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_return_type!=null) method_return_type.traverseBottomUp(visitor);
        if(method_identifier!=null) method_identifier.traverseBottomUp(visitor);
        if(formal_parameter_section!=null) formal_parameter_section.traverseBottomUp(visitor);
        if(var_declaration_list!=null) var_declaration_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclaration(\n");

        if(method_return_type!=null)
            buffer.append(method_return_type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(method_identifier!=null)
            buffer.append(method_identifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(formal_parameter_section!=null)
            buffer.append(formal_parameter_section.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(var_declaration_list!=null)
            buffer.append(var_declaration_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclaration]");
        return buffer.toString();
    }
}
