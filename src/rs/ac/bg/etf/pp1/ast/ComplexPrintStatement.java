// generated with ast extension for cup
// version 0.8
// 6/1/2018 21:51:44


package rs.ac.bg.etf.pp1.ast;

public class ComplexPrintStatement extends Print_statement {

    private Expression expression;
    private Numeric_const numeric_const;

    public ComplexPrintStatement (Expression expression, Numeric_const numeric_const) {
        this.expression=expression;
        if(expression!=null) expression.setParent(this);
        this.numeric_const=numeric_const;
        if(numeric_const!=null) numeric_const.setParent(this);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression=expression;
    }

    public Numeric_const getNumeric_const() {
        return numeric_const;
    }

    public void setNumeric_const(Numeric_const numeric_const) {
        this.numeric_const=numeric_const;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(expression!=null) expression.accept(visitor);
        if(numeric_const!=null) numeric_const.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(expression!=null) expression.traverseTopDown(visitor);
        if(numeric_const!=null) numeric_const.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(expression!=null) expression.traverseBottomUp(visitor);
        if(numeric_const!=null) numeric_const.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ComplexPrintStatement(\n");

        if(expression!=null)
            buffer.append(expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(numeric_const!=null)
            buffer.append(numeric_const.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ComplexPrintStatement]");
        return buffer.toString();
    }
}
