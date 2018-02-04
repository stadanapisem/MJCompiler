// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:47:4


package rs.ac.bg.etf.pp1.ast;

public class DeclarationConst extends Declaration {

    private Const_declaration_line const_declaration_line;

    public DeclarationConst (Const_declaration_line const_declaration_line) {
        this.const_declaration_line=const_declaration_line;
        if(const_declaration_line!=null) const_declaration_line.setParent(this);
    }

    public Const_declaration_line getConst_declaration_line() {
        return const_declaration_line;
    }

    public void setConst_declaration_line(Const_declaration_line const_declaration_line) {
        this.const_declaration_line=const_declaration_line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(const_declaration_line!=null) const_declaration_line.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(const_declaration_line!=null) const_declaration_line.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(const_declaration_line!=null) const_declaration_line.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationConst(\n");

        if(const_declaration_line!=null)
            buffer.append(const_declaration_line.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationConst]");
        return buffer.toString();
    }
}
