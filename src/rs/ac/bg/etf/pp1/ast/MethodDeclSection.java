// generated with ast extension for cup
// version 0.8
// 24/0/2018 22:42:39


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclSection extends Method_decl_section {

    private Method_decl_list method_decl_list;

    public MethodDeclSection (Method_decl_list method_decl_list) {
        this.method_decl_list=method_decl_list;
        if(method_decl_list!=null) method_decl_list.setParent(this);
    }

    public Method_decl_list getMethod_decl_list() {
        return method_decl_list;
    }

    public void setMethod_decl_list(Method_decl_list method_decl_list) {
        this.method_decl_list=method_decl_list;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_decl_list!=null) method_decl_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_decl_list!=null) method_decl_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_decl_list!=null) method_decl_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclSection(\n");

        if(method_decl_list!=null)
            buffer.append(method_decl_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclSection]");
        return buffer.toString();
    }
}
