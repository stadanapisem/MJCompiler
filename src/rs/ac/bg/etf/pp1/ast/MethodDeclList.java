// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:45:32


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclList extends Method_decl_list {

    private Method_decl_list method_decl_list;
    private Method_definition method_definition;

    public MethodDeclList (Method_decl_list method_decl_list, Method_definition method_definition) {
        this.method_decl_list=method_decl_list;
        if(method_decl_list!=null) method_decl_list.setParent(this);
        this.method_definition=method_definition;
        if(method_definition!=null) method_definition.setParent(this);
    }

    public Method_decl_list getMethod_decl_list() {
        return method_decl_list;
    }

    public void setMethod_decl_list(Method_decl_list method_decl_list) {
        this.method_decl_list=method_decl_list;
    }

    public Method_definition getMethod_definition() {
        return method_definition;
    }

    public void setMethod_definition(Method_definition method_definition) {
        this.method_definition=method_definition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_decl_list!=null) method_decl_list.accept(visitor);
        if(method_definition!=null) method_definition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_decl_list!=null) method_decl_list.traverseTopDown(visitor);
        if(method_definition!=null) method_definition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_decl_list!=null) method_decl_list.traverseBottomUp(visitor);
        if(method_definition!=null) method_definition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclList(\n");

        if(method_decl_list!=null)
            buffer.append(method_decl_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(method_definition!=null)
            buffer.append(method_definition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclList]");
        return buffer.toString();
    }
}
