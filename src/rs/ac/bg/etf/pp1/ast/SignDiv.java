// generated with ast extension for cup
// version 0.8
// 30/0/2018 19:5:1


package rs.ac.bg.etf.pp1.ast;

public class SignDiv extends Multiplication_operator {

    public SignDiv () {
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
        buffer.append("SignDiv(\n");

        buffer.append(tab);
        buffer.append(") [SignDiv]");
        return buffer.toString();
    }
}