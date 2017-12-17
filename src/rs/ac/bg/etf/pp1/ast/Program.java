// generated with ast extension for cup
// version 0.8
// 17/11/2017 19:57:29


package rs.ac.bg.etf.pp1.ast;

public class Program implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Prog_id prog_id;

    public Program (Prog_id prog_id) {
        this.prog_id=prog_id;
        if(prog_id!=null) prog_id.setParent(this);
    }

    public Prog_id getProg_id() {
        return prog_id;
    }

    public void setProg_id(Prog_id prog_id) {
        this.prog_id=prog_id;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(prog_id!=null) prog_id.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(prog_id!=null) prog_id.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(prog_id!=null) prog_id.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Program(\n");

        if(prog_id!=null)
            buffer.append(prog_id.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Program]");
        return buffer.toString();
    }
}
