// generated with ast extension for cup
// version 0.8
// 1/1/2018 17:58:9


package rs.ac.bg.etf.pp1.ast;

public class CondOpFactor extends Condition_factor {

    private Expression expression;
    private Rel_operator rel_operator;
    private Expression expression1;

    public CondOpFactor (Expression expression, Rel_operator rel_operator, Expression expression1) {
        this.expression=expression;
        if(expression!=null) expression.setParent(this);
        this.rel_operator=rel_operator;
        if(rel_operator!=null) rel_operator.setParent(this);
        this.expression1=expression1;
        if(expression1!=null) expression1.setParent(this);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression=expression;
    }

    public Rel_operator getRel_operator() {
        return rel_operator;
    }

    public void setRel_operator(Rel_operator rel_operator) {
        this.rel_operator=rel_operator;
    }

    public Expression getExpression1() {
        return expression1;
    }

    public void setExpression1(Expression expression1) {
        this.expression1=expression1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(expression!=null) expression.accept(visitor);
        if(rel_operator!=null) rel_operator.accept(visitor);
        if(expression1!=null) expression1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(expression!=null) expression.traverseTopDown(visitor);
        if(rel_operator!=null) rel_operator.traverseTopDown(visitor);
        if(expression1!=null) expression1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(expression!=null) expression.traverseBottomUp(visitor);
        if(rel_operator!=null) rel_operator.traverseBottomUp(visitor);
        if(expression1!=null) expression1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondOpFactor(\n");

        if(expression!=null)
            buffer.append(expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(rel_operator!=null)
            buffer.append(rel_operator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(expression1!=null)
            buffer.append(expression1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondOpFactor]");
        return buffer.toString();
    }
}
