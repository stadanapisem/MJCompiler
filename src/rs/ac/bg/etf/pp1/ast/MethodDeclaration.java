// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:47:4


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclaration extends Method_decl {

    private Method_identifier method_identifier;
    private Formal_parameter_section formal_parameter_section;
    private Var_declaration_list var_declaration_list;

    public MethodDeclaration (Method_identifier method_identifier, Formal_parameter_section formal_parameter_section, Var_declaration_list var_declaration_list) {
        this.method_identifier=method_identifier;
        if(method_identifier!=null) method_identifier.setParent(this);
        this.formal_parameter_section=formal_parameter_section;
        if(formal_parameter_section!=null) formal_parameter_section.setParent(this);
        this.var_declaration_list=var_declaration_list;
        if(var_declaration_list!=null) var_declaration_list.setParent(this);
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
        if(method_identifier!=null) method_identifier.accept(visitor);
        if(formal_parameter_section!=null) formal_parameter_section.accept(visitor);
        if(var_declaration_list!=null) var_declaration_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_identifier!=null) method_identifier.traverseTopDown(visitor);
        if(formal_parameter_section!=null) formal_parameter_section.traverseTopDown(visitor);
        if(var_declaration_list!=null) var_declaration_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_identifier!=null) method_identifier.traverseBottomUp(visitor);
        if(formal_parameter_section!=null) formal_parameter_section.traverseBottomUp(visitor);
        if(var_declaration_list!=null) var_declaration_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclaration(\n");

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
