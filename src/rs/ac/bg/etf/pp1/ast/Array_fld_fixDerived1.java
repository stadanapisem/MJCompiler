// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:45:32


package rs.ac.bg.etf.pp1.ast;

public class Array_fld_fixDerived1 extends Array_fld_fix {

    private Expression expression;

    public Array_fld_fixDerived1 (Expression expression) {
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
        buffer.append("Array_fld_fixDerived1(\n");

        if(expression!=null)
            buffer.append(expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Array_fld_fixDerived1]");
        return buffer.toString();
    }
}
