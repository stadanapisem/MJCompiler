// generated with ast extension for cup
// version 0.8
// 26/0/2018 19:57:51


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclList extends Method_decl_list {

    private Method_decl_list method_decl_list;
    private Method_decl method_decl;

    public MethodDeclList (Method_decl_list method_decl_list, Method_decl method_decl) {
        this.method_decl_list=method_decl_list;
        if(method_decl_list!=null) method_decl_list.setParent(this);
        this.method_decl=method_decl;
        if(method_decl!=null) method_decl.setParent(this);
    }

    public Method_decl_list getMethod_decl_list() {
        return method_decl_list;
    }

    public void setMethod_decl_list(Method_decl_list method_decl_list) {
        this.method_decl_list=method_decl_list;
    }

    public Method_decl getMethod_decl() {
        return method_decl;
    }

    public void setMethod_decl(Method_decl method_decl) {
        this.method_decl=method_decl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_decl_list!=null) method_decl_list.accept(visitor);
        if(method_decl!=null) method_decl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_decl_list!=null) method_decl_list.traverseTopDown(visitor);
        if(method_decl!=null) method_decl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_decl_list!=null) method_decl_list.traverseBottomUp(visitor);
        if(method_decl!=null) method_decl.traverseBottomUp(visitor);
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

        if(method_decl!=null)
            buffer.append(method_decl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclList]");
        return buffer.toString();
    }
}
