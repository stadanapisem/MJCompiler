// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:47:4


package rs.ac.bg.etf.pp1.ast;

public class TerminalCondFactor extends Condition_factor_list {

    private Condition_factor condition_factor;

    public TerminalCondFactor (Condition_factor condition_factor) {
        this.condition_factor=condition_factor;
        if(condition_factor!=null) condition_factor.setParent(this);
    }

    public Condition_factor getCondition_factor() {
        return condition_factor;
    }

    public void setCondition_factor(Condition_factor condition_factor) {
        this.condition_factor=condition_factor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(condition_factor!=null) condition_factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(condition_factor!=null) condition_factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(condition_factor!=null) condition_factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TerminalCondFactor(\n");

        if(condition_factor!=null)
            buffer.append(condition_factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TerminalCondFactor]");
        return buffer.toString();
    }
}
