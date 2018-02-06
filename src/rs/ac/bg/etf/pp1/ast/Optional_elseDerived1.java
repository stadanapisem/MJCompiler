// generated with ast extension for cup
// version 0.8
// 6/1/2018 21:51:44


package rs.ac.bg.etf.pp1.ast;

public class Optional_elseDerived1 extends Optional_else {

    private Else_jmp_fix else_jmp_fix;
    private Statement statement;

    public Optional_elseDerived1 (Else_jmp_fix else_jmp_fix, Statement statement) {
        this.else_jmp_fix=else_jmp_fix;
        if(else_jmp_fix!=null) else_jmp_fix.setParent(this);
        this.statement=statement;
        if(statement!=null) statement.setParent(this);
    }

    public Else_jmp_fix getElse_jmp_fix() {
        return else_jmp_fix;
    }

    public void setElse_jmp_fix(Else_jmp_fix else_jmp_fix) {
        this.else_jmp_fix=else_jmp_fix;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement=statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(else_jmp_fix!=null) else_jmp_fix.accept(visitor);
        if(statement!=null) statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(else_jmp_fix!=null) else_jmp_fix.traverseTopDown(visitor);
        if(statement!=null) statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(else_jmp_fix!=null) else_jmp_fix.traverseBottomUp(visitor);
        if(statement!=null) statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Optional_elseDerived1(\n");

        if(else_jmp_fix!=null)
            buffer.append(else_jmp_fix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(statement!=null)
            buffer.append(statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Optional_elseDerived1]");
        return buffer.toString();
    }
}
