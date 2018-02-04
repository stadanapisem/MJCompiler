// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:47:4


package rs.ac.bg.etf.pp1.ast;

public class CondTerminalTerm extends Condition_term_list {

    private Condition_term condition_term;

    public CondTerminalTerm (Condition_term condition_term) {
        this.condition_term=condition_term;
        if(condition_term!=null) condition_term.setParent(this);
    }

    public Condition_term getCondition_term() {
        return condition_term;
    }

    public void setCondition_term(Condition_term condition_term) {
        this.condition_term=condition_term;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(condition_term!=null) condition_term.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(condition_term!=null) condition_term.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(condition_term!=null) condition_term.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondTerminalTerm(\n");

        if(condition_term!=null)
            buffer.append(condition_term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondTerminalTerm]");
        return buffer.toString();
    }
}
