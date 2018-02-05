// generated with ast extension for cup
// version 0.8
// 5/1/2018 22:2:38


package rs.ac.bg.etf.pp1.ast;

public class DeclarationVar extends Declaration {

    private Var_declaration_line var_declaration_line;

    public DeclarationVar (Var_declaration_line var_declaration_line) {
        this.var_declaration_line=var_declaration_line;
        if(var_declaration_line!=null) var_declaration_line.setParent(this);
    }

    public Var_declaration_line getVar_declaration_line() {
        return var_declaration_line;
    }

    public void setVar_declaration_line(Var_declaration_line var_declaration_line) {
        this.var_declaration_line=var_declaration_line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(var_declaration_line!=null) var_declaration_line.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(var_declaration_line!=null) var_declaration_line.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(var_declaration_line!=null) var_declaration_line.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationVar(\n");

        if(var_declaration_line!=null)
            buffer.append(var_declaration_line.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationVar]");
        return buffer.toString();
    }
}
