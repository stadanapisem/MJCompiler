// generated with ast extension for cup
// version 0.8
// 28/0/2018 21:0:58


package rs.ac.bg.etf.pp1.ast;

public class Multiplication_operatorDerived2 extends Multiplication_operator {

    public Multiplication_operatorDerived2 () {
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
        buffer.append("Multiplication_operatorDerived2(\n");

        buffer.append(tab);
        buffer.append(") [Multiplication_operatorDerived2]");
        return buffer.toString();
    }
}
