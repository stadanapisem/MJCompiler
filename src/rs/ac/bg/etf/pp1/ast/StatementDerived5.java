// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:45:32


package rs.ac.bg.etf.pp1.ast;

public class StatementDerived5 extends Statement {

    private If_statement if_statement;

    public StatementDerived5 (If_statement if_statement) {
        this.if_statement=if_statement;
        if(if_statement!=null) if_statement.setParent(this);
    }

    public If_statement getIf_statement() {
        return if_statement;
    }

    public void setIf_statement(If_statement if_statement) {
        this.if_statement=if_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(if_statement!=null) if_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(if_statement!=null) if_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(if_statement!=null) if_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDerived5(\n");

        if(if_statement!=null)
            buffer.append(if_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementDerived5]");
        return buffer.toString();
    }
}
