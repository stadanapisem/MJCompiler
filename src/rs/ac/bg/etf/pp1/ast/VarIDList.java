// generated with ast extension for cup
// version 0.8
// 6/1/2018 21:51:44


package rs.ac.bg.etf.pp1.ast;

public class VarIDList extends Var_id_list {

    private Var_id_list var_id_list;
    private Var_id var_id;

    public VarIDList (Var_id_list var_id_list, Var_id var_id) {
        this.var_id_list=var_id_list;
        if(var_id_list!=null) var_id_list.setParent(this);
        this.var_id=var_id;
        if(var_id!=null) var_id.setParent(this);
    }

    public Var_id_list getVar_id_list() {
        return var_id_list;
    }

    public void setVar_id_list(Var_id_list var_id_list) {
        this.var_id_list=var_id_list;
    }

    public Var_id getVar_id() {
        return var_id;
    }

    public void setVar_id(Var_id var_id) {
        this.var_id=var_id;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(var_id_list!=null) var_id_list.accept(visitor);
        if(var_id!=null) var_id.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(var_id_list!=null) var_id_list.traverseTopDown(visitor);
        if(var_id!=null) var_id.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(var_id_list!=null) var_id_list.traverseBottomUp(visitor);
        if(var_id!=null) var_id.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarIDList(\n");

        if(var_id_list!=null)
            buffer.append(var_id_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(var_id!=null)
            buffer.append(var_id.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarIDList]");
        return buffer.toString();
    }
}
