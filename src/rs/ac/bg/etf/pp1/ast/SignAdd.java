// generated with ast extension for cup
// version 0.8
// 6/1/2018 21:51:44


package rs.ac.bg.etf.pp1.ast;

public class SignAdd extends Addition_operator {

    public SignAdd () {
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
        buffer.append("SignAdd(\n");

        buffer.append(tab);
        buffer.append(") [SignAdd]");
        return buffer.toString();
    }
}
