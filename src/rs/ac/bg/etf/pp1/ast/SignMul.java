// generated with ast extension for cup
// version 0.8
// 31/0/2018 13:17:49


package rs.ac.bg.etf.pp1.ast;

public class SignMul extends Multiplication_operator {

    public SignMul () {
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
        buffer.append("SignMul(\n");

        buffer.append(tab);
        buffer.append(") [SignMul]");
        return buffer.toString();
    }
}
