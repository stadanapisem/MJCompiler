// generated with ast extension for cup
// version 0.8
// 5/1/2018 22:2:38


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationLine extends Var_declaration_line {

    private Type type;
    private Var_id_list var_id_list;

    public VarDeclarationLine (Type type, Var_id_list var_id_list) {
        this.type=type;
        if(type!=null) type.setParent(this);
        this.var_id_list=var_id_list;
        if(var_id_list!=null) var_id_list.setParent(this);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type=type;
    }

    public Var_id_list getVar_id_list() {
        return var_id_list;
    }

    public void setVar_id_list(Var_id_list var_id_list) {
        this.var_id_list=var_id_list;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(type!=null) type.accept(visitor);
        if(var_id_list!=null) var_id_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(type!=null) type.traverseTopDown(visitor);
        if(var_id_list!=null) var_id_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(type!=null) type.traverseBottomUp(visitor);
        if(var_id_list!=null) var_id_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationLine(\n");

        if(type!=null)
            buffer.append(type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(var_id_list!=null)
            buffer.append(var_id_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationLine]");
        return buffer.toString();
    }
}
