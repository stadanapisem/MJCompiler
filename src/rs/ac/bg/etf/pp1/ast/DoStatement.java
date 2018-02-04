// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:47:4


package rs.ac.bg.etf.pp1.ast;

public class DoStatement extends Statement {

    private Do_statement do_statement;

    public DoStatement (Do_statement do_statement) {
        this.do_statement=do_statement;
        if(do_statement!=null) do_statement.setParent(this);
    }

    public Do_statement getDo_statement() {
        return do_statement;
    }

    public void setDo_statement(Do_statement do_statement) {
        this.do_statement=do_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(do_statement!=null) do_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(do_statement!=null) do_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(do_statement!=null) do_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DoStatement(\n");

        if(do_statement!=null)
            buffer.append(do_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DoStatement]");
        return buffer.toString();
    }
}
