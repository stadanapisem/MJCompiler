// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:47:4


package rs.ac.bg.etf.pp1.ast;

public class CondFactorList extends Condition_factor_list {

    private Condition_factor_list condition_factor_list;
    private And_operator and_operator;
    private Condition_factor condition_factor;

    public CondFactorList (Condition_factor_list condition_factor_list, And_operator and_operator, Condition_factor condition_factor) {
        this.condition_factor_list=condition_factor_list;
        if(condition_factor_list!=null) condition_factor_list.setParent(this);
        this.and_operator=and_operator;
        if(and_operator!=null) and_operator.setParent(this);
        this.condition_factor=condition_factor;
        if(condition_factor!=null) condition_factor.setParent(this);
    }

    public Condition_factor_list getCondition_factor_list() {
        return condition_factor_list;
    }

    public void setCondition_factor_list(Condition_factor_list condition_factor_list) {
        this.condition_factor_list=condition_factor_list;
    }

    public And_operator getAnd_operator() {
        return and_operator;
    }

    public void setAnd_operator(And_operator and_operator) {
        this.and_operator=and_operator;
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
        if(condition_factor_list!=null) condition_factor_list.accept(visitor);
        if(and_operator!=null) and_operator.accept(visitor);
        if(condition_factor!=null) condition_factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(condition_factor_list!=null) condition_factor_list.traverseTopDown(visitor);
        if(and_operator!=null) and_operator.traverseTopDown(visitor);
        if(condition_factor!=null) condition_factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(condition_factor_list!=null) condition_factor_list.traverseBottomUp(visitor);
        if(and_operator!=null) and_operator.traverseBottomUp(visitor);
        if(condition_factor!=null) condition_factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFactorList(\n");

        if(condition_factor_list!=null)
            buffer.append(condition_factor_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(and_operator!=null)
            buffer.append(and_operator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(condition_factor!=null)
            buffer.append(condition_factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFactorList]");
        return buffer.toString();
    }
}
