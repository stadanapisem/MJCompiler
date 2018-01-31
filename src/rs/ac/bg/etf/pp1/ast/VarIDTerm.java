// generated with ast extension for cup
// version 0.8
// 31/0/2018 13:17:49


package rs.ac.bg.etf.pp1.ast;

public class VarIDTerm extends Var_id_list {

    private Var_id var_id;

    public VarIDTerm (Var_id var_id) {
        this.var_id=var_id;
        if(var_id!=null) var_id.setParent(this);
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
        if(var_id!=null) var_id.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(var_id!=null) var_id.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(var_id!=null) var_id.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarIDTerm(\n");

        if(var_id!=null)
            buffer.append(var_id.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarIDTerm]");
        return buffer.toString();
    }
}
