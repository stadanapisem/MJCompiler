// generated with ast extension for cup
// version 0.8
// 24/0/2018 22:42:39


package rs.ac.bg.etf.pp1.ast;

public class NoParameters extends Formal_parameter_section {

    public NoParameters () {
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
        buffer.append("NoParameters(\n");

        buffer.append(tab);
        buffer.append(") [NoParameters]");
        return buffer.toString();
    }
}