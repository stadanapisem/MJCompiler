// generated with ast extension for cup
// version 0.8
// 30/0/2018 19:5:1


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArray extends Designator {

    private String id;
    private Expression expression;

    public DesignatorArray (String id, Expression expression) {
        this.id=id;
        this.expression=expression;
        if(expression!=null) expression.setParent(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
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
        buffer.append("DesignatorArray(\n");

        buffer.append(" "+tab+id);
        buffer.append("\n");

        if(expression!=null)
            buffer.append(expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArray]");
        return buffer.toString();
    }
}
