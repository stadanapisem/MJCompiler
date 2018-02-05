// generated with ast extension for cup
// version 0.8
// 5/1/2018 22:2:38


package rs.ac.bg.etf.pp1.ast;

public class StatementDerived4 extends Statement {

    private Return_statement return_statement;

    public StatementDerived4 (Return_statement return_statement) {
        this.return_statement=return_statement;
        if(return_statement!=null) return_statement.setParent(this);
    }

    public Return_statement getReturn_statement() {
        return return_statement;
    }

    public void setReturn_statement(Return_statement return_statement) {
        this.return_statement=return_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(return_statement!=null) return_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(return_statement!=null) return_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(return_statement!=null) return_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDerived4(\n");

        if(return_statement!=null)
            buffer.append(return_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementDerived4]");
        return buffer.toString();
    }
}
