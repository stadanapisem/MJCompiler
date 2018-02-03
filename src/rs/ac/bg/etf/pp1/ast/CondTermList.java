// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:45:32


package rs.ac.bg.etf.pp1.ast;

public class CondTermList extends Condition_term_list {

    private Condition_term_list condition_term_list;
    private Or_operator or_operator;
    private Condition_term condition_term;

    public CondTermList (Condition_term_list condition_term_list, Or_operator or_operator, Condition_term condition_term) {
        this.condition_term_list=condition_term_list;
        if(condition_term_list!=null) condition_term_list.setParent(this);
        this.or_operator=or_operator;
        if(or_operator!=null) or_operator.setParent(this);
        this.condition_term=condition_term;
        if(condition_term!=null) condition_term.setParent(this);
    }

    public Condition_term_list getCondition_term_list() {
        return condition_term_list;
    }

    public void setCondition_term_list(Condition_term_list condition_term_list) {
        this.condition_term_list=condition_term_list;
    }

    public Or_operator getOr_operator() {
        return or_operator;
    }

    public void setOr_operator(Or_operator or_operator) {
        this.or_operator=or_operator;
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
        if(condition_term_list!=null) condition_term_list.accept(visitor);
        if(or_operator!=null) or_operator.accept(visitor);
        if(condition_term!=null) condition_term.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(condition_term_list!=null) condition_term_list.traverseTopDown(visitor);
        if(or_operator!=null) or_operator.traverseTopDown(visitor);
        if(condition_term!=null) condition_term.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(condition_term_list!=null) condition_term_list.traverseBottomUp(visitor);
        if(or_operator!=null) or_operator.traverseBottomUp(visitor);
        if(condition_term!=null) condition_term.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondTermList(\n");

        if(condition_term_list!=null)
            buffer.append(condition_term_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(or_operator!=null)
            buffer.append(or_operator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(condition_term!=null)
            buffer.append(condition_term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondTermList]");
        return buffer.toString();
    }
}
