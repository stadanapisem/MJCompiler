// generated with ast extension for cup
// version 0.8
// 1/1/2018 17:58:9


package rs.ac.bg.etf.pp1.ast;

public class CondTerm extends Condition_term {

    private Condition_factor_list condition_factor_list;

    public CondTerm (Condition_factor_list condition_factor_list) {
        this.condition_factor_list=condition_factor_list;
        if(condition_factor_list!=null) condition_factor_list.setParent(this);
    }

    public Condition_factor_list getCondition_factor_list() {
        return condition_factor_list;
    }

    public void setCondition_factor_list(Condition_factor_list condition_factor_list) {
        this.condition_factor_list=condition_factor_list;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(condition_factor_list!=null) condition_factor_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(condition_factor_list!=null) condition_factor_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(condition_factor_list!=null) condition_factor_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondTerm(\n");

        if(condition_factor_list!=null)
            buffer.append(condition_factor_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondTerm]");
        return buffer.toString();
    }
}
