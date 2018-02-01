// generated with ast extension for cup
// version 0.8
// 1/1/2018 17:58:9


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationList extends Var_declaration_list {

    private Var_declaration_list var_declaration_list;
    private Var_declaration_line var_declaration_line;

    public VarDeclarationList (Var_declaration_list var_declaration_list, Var_declaration_line var_declaration_line) {
        this.var_declaration_list=var_declaration_list;
        if(var_declaration_list!=null) var_declaration_list.setParent(this);
        this.var_declaration_line=var_declaration_line;
        if(var_declaration_line!=null) var_declaration_line.setParent(this);
    }

    public Var_declaration_list getVar_declaration_list() {
        return var_declaration_list;
    }

    public void setVar_declaration_list(Var_declaration_list var_declaration_list) {
        this.var_declaration_list=var_declaration_list;
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
        if(var_declaration_list!=null) var_declaration_list.accept(visitor);
        if(var_declaration_line!=null) var_declaration_line.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(var_declaration_list!=null) var_declaration_list.traverseTopDown(visitor);
        if(var_declaration_line!=null) var_declaration_line.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(var_declaration_list!=null) var_declaration_list.traverseBottomUp(visitor);
        if(var_declaration_line!=null) var_declaration_line.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationList(\n");

        if(var_declaration_list!=null)
            buffer.append(var_declaration_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(var_declaration_line!=null)
            buffer.append(var_declaration_line.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationList]");
        return buffer.toString();
    }
}
