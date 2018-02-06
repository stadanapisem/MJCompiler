// generated with ast extension for cup
// version 0.8
// 6/1/2018 21:51:44


package rs.ac.bg.etf.pp1.ast;

public class CondExpression extends Condition {

    private Condition_term_list condition_term_list;

    public CondExpression (Condition_term_list condition_term_list) {
        this.condition_term_list=condition_term_list;
        if(condition_term_list!=null) condition_term_list.setParent(this);
    }

    public Condition_term_list getCondition_term_list() {
        return condition_term_list;
    }

    public void setCondition_term_list(Condition_term_list condition_term_list) {
        this.condition_term_list=condition_term_list;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(condition_term_list!=null) condition_term_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(condition_term_list!=null) condition_term_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(condition_term_list!=null) condition_term_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondExpression(\n");

        if(condition_term_list!=null)
            buffer.append(condition_term_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondExpression]");
        return buffer.toString();
    }
}
