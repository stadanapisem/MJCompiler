// generated with ast extension for cup
// version 0.8
// 1/1/2018 17:58:9


package rs.ac.bg.etf.pp1.ast;

public class SignMod extends Multiplication_operator {

    public SignMod () {
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
        buffer.append("SignMod(\n");

        buffer.append(tab);
        buffer.append(") [SignMod]");
        return buffer.toString();
    }
}
