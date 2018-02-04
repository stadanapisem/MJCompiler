// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:47:4


package rs.ac.bg.etf.pp1.ast;

public class StatementDerived3 extends Statement {

    private Print_statement print_statement;

    public StatementDerived3 (Print_statement print_statement) {
        this.print_statement=print_statement;
        if(print_statement!=null) print_statement.setParent(this);
    }

    public Print_statement getPrint_statement() {
        return print_statement;
    }

    public void setPrint_statement(Print_statement print_statement) {
        this.print_statement=print_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(print_statement!=null) print_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(print_statement!=null) print_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(print_statement!=null) print_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDerived3(\n");

        if(print_statement!=null)
            buffer.append(print_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementDerived3]");
        return buffer.toString();
    }
}
