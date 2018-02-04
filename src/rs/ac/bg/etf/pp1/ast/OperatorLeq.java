// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:47:4


package rs.ac.bg.etf.pp1.ast;

public class OperatorLeq extends Rel_operator {

    public OperatorLeq () {
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
        buffer.append("OperatorLeq(\n");

        buffer.append(tab);
        buffer.append(") [OperatorLeq]");
        return buffer.toString();
    }
}
