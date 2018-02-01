// generated with ast extension for cup
// version 0.8
// 1/1/2018 17:58:9


package rs.ac.bg.etf.pp1.ast;

public class IfStatement extends If_statement {

    private If_statement_header if_statement_header;
    private Statement statement;
    private Optional_else optional_else;

    public IfStatement (If_statement_header if_statement_header, Statement statement, Optional_else optional_else) {
        this.if_statement_header=if_statement_header;
        if(if_statement_header!=null) if_statement_header.setParent(this);
        this.statement=statement;
        if(statement!=null) statement.setParent(this);
        this.optional_else=optional_else;
        if(optional_else!=null) optional_else.setParent(this);
    }

    public If_statement_header getIf_statement_header() {
        return if_statement_header;
    }

    public void setIf_statement_header(If_statement_header if_statement_header) {
        this.if_statement_header=if_statement_header;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement=statement;
    }

    public Optional_else getOptional_else() {
        return optional_else;
    }

    public void setOptional_else(Optional_else optional_else) {
        this.optional_else=optional_else;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(if_statement_header!=null) if_statement_header.accept(visitor);
        if(statement!=null) statement.accept(visitor);
        if(optional_else!=null) optional_else.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(if_statement_header!=null) if_statement_header.traverseTopDown(visitor);
        if(statement!=null) statement.traverseTopDown(visitor);
        if(optional_else!=null) optional_else.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(if_statement_header!=null) if_statement_header.traverseBottomUp(visitor);
        if(statement!=null) statement.traverseBottomUp(visitor);
        if(optional_else!=null) optional_else.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfStatement(\n");

        if(if_statement_header!=null)
            buffer.append(if_statement_header.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(statement!=null)
            buffer.append(statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(optional_else!=null)
            buffer.append(optional_else.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfStatement]");
        return buffer.toString();
    }
}
