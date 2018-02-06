// generated with ast extension for cup
// version 0.8
// 6/1/2018 21:51:44


package rs.ac.bg.etf.pp1.ast;

public class DesignatorThisArray extends Designator {

    private Array_ident array_ident;
    private Expression expression;

    public DesignatorThisArray (Array_ident array_ident, Expression expression) {
        this.array_ident=array_ident;
        if(array_ident!=null) array_ident.setParent(this);
        this.expression=expression;
        if(expression!=null) expression.setParent(this);
    }

    public Array_ident getArray_ident() {
        return array_ident;
    }

    public void setArray_ident(Array_ident array_ident) {
        this.array_ident=array_ident;
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
        if(array_ident!=null) array_ident.accept(visitor);
        if(expression!=null) expression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(array_ident!=null) array_ident.traverseTopDown(visitor);
        if(expression!=null) expression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(array_ident!=null) array_ident.traverseBottomUp(visitor);
        if(expression!=null) expression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorThisArray(\n");

        if(array_ident!=null)
            buffer.append(array_ident.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(expression!=null)
            buffer.append(expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorThisArray]");
        return buffer.toString();
    }
}
