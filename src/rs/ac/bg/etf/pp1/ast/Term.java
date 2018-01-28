// generated with ast extension for cup
// version 0.8
// 28/0/2018 21:0:58


package rs.ac.bg.etf.pp1.ast;

public class Term implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private Multiplication_factor_list multiplication_factor_list;

    public Term (Multiplication_factor_list multiplication_factor_list) {
        this.multiplication_factor_list=multiplication_factor_list;
        if(multiplication_factor_list!=null) multiplication_factor_list.setParent(this);
    }

    public Multiplication_factor_list getMultiplication_factor_list() {
        return multiplication_factor_list;
    }

    public void setMultiplication_factor_list(Multiplication_factor_list multiplication_factor_list) {
        this.multiplication_factor_list=multiplication_factor_list;
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
        if(multiplication_factor_list!=null) multiplication_factor_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(multiplication_factor_list!=null) multiplication_factor_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(multiplication_factor_list!=null) multiplication_factor_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Term(\n");

        if(multiplication_factor_list!=null)
            buffer.append(multiplication_factor_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Term]");
        return buffer.toString();
    }
}
