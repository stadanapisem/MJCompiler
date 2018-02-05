// generated with ast extension for cup
// version 0.8
// 5/1/2018 22:2:38


package rs.ac.bg.etf.pp1.ast;

public class BreakStatement extends Statement {

    private Break_statement break_statement;

    public BreakStatement (Break_statement break_statement) {
        this.break_statement=break_statement;
        if(break_statement!=null) break_statement.setParent(this);
    }

    public Break_statement getBreak_statement() {
        return break_statement;
    }

    public void setBreak_statement(Break_statement break_statement) {
        this.break_statement=break_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(break_statement!=null) break_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(break_statement!=null) break_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(break_statement!=null) break_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BreakStatement(\n");

        if(break_statement!=null)
            buffer.append(break_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BreakStatement]");
        return buffer.toString();
    }
}
