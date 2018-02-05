// generated with ast extension for cup
// version 0.8
// 5/1/2018 22:2:38


package rs.ac.bg.etf.pp1.ast;

public class Do_statementDerived1 extends Do_statement {

    private Remember_pc remember_pc;
    private Statement statement;
    private Condition condition;

    public Do_statementDerived1 (Remember_pc remember_pc, Statement statement, Condition condition) {
        this.remember_pc=remember_pc;
        if(remember_pc!=null) remember_pc.setParent(this);
        this.statement=statement;
        if(statement!=null) statement.setParent(this);
        this.condition=condition;
        if(condition!=null) condition.setParent(this);
    }

    public Remember_pc getRemember_pc() {
        return remember_pc;
    }

    public void setRemember_pc(Remember_pc remember_pc) {
        this.remember_pc=remember_pc;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement=statement;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition=condition;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(remember_pc!=null) remember_pc.accept(visitor);
        if(statement!=null) statement.accept(visitor);
        if(condition!=null) condition.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(remember_pc!=null) remember_pc.traverseTopDown(visitor);
        if(statement!=null) statement.traverseTopDown(visitor);
        if(condition!=null) condition.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(remember_pc!=null) remember_pc.traverseBottomUp(visitor);
        if(statement!=null) statement.traverseBottomUp(visitor);
        if(condition!=null) condition.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Do_statementDerived1(\n");

        if(remember_pc!=null)
            buffer.append(remember_pc.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(statement!=null)
            buffer.append(statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(condition!=null)
            buffer.append(condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Do_statementDerived1]");
        return buffer.toString();
    }
}
