// generated with ast extension for cup
// version 0.8
// 30/0/2018 19:5:1


package rs.ac.bg.etf.pp1.ast;

public class StatementDerived2 extends Statement {

    private Read_statement read_statement;

    public StatementDerived2 (Read_statement read_statement) {
        this.read_statement=read_statement;
        if(read_statement!=null) read_statement.setParent(this);
    }

    public Read_statement getRead_statement() {
        return read_statement;
    }

    public void setRead_statement(Read_statement read_statement) {
        this.read_statement=read_statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(read_statement!=null) read_statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(read_statement!=null) read_statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(read_statement!=null) read_statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementDerived2(\n");

        if(read_statement!=null)
            buffer.append(read_statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementDerived2]");
        return buffer.toString();
    }
}
