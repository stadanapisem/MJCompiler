// generated with ast extension for cup
// version 0.8
// 26/0/2018 19:57:51


package rs.ac.bg.etf.pp1.ast;

public class MethodIdentifier extends Method_identifier {

    private String id;

    public MethodIdentifier (String id) {
        this.id=id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodIdentifier(\n");

        buffer.append(" "+tab+id);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodIdentifier]");
        return buffer.toString();
    }
}
