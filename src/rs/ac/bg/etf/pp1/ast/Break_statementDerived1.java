// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:45:32


package rs.ac.bg.etf.pp1.ast;

public class Break_statementDerived1 extends Break_statement {

    public Break_statementDerived1 () {
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
        buffer.append("Break_statementDerived1(\n");

        buffer.append(tab);
        buffer.append(") [Break_statementDerived1]");
        return buffer.toString();
    }
}
