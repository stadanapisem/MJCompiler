// generated with ast extension for cup
// version 0.8
// 31/0/2018 13:17:49


package rs.ac.bg.etf.pp1.ast;

public class ExpressionFactor extends Factor {

    private Expression expression;

    public ExpressionFactor (Expression expression) {
        this.expression=expression;
        if(expression!=null) expression.setParent(this);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression=expression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(expression!=null) expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(expression!=null) expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(expression!=null) expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExpressionFactor(\n");

        if(expression!=null)
            buffer.append(expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExpressionFactor]");
        return buffer.toString();
    }
}
