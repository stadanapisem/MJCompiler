// generated with ast extension for cup
// version 0.8
// 31/0/2018 13:17:49


package rs.ac.bg.etf.pp1.ast;

public class OptionalMethodDeclSection extends Optional_method_decl_section {

    private Method_decl_section method_decl_section;

    public OptionalMethodDeclSection (Method_decl_section method_decl_section) {
        this.method_decl_section=method_decl_section;
        if(method_decl_section!=null) method_decl_section.setParent(this);
    }

    public Method_decl_section getMethod_decl_section() {
        return method_decl_section;
    }

    public void setMethod_decl_section(Method_decl_section method_decl_section) {
        this.method_decl_section=method_decl_section;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_decl_section!=null) method_decl_section.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_decl_section!=null) method_decl_section.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_decl_section!=null) method_decl_section.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OptionalMethodDeclSection(\n");

        if(method_decl_section!=null)
            buffer.append(method_decl_section.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OptionalMethodDeclSection]");
        return buffer.toString();
    }
}
