// generated with ast extension for cup
// version 0.8
// 26/0/2018 19:57:51


package rs.ac.bg.etf.pp1.ast;

public class VarID extends Var_id {

    private Var_identifier var_identifier;

    public VarID (Var_identifier var_identifier) {
        this.var_identifier=var_identifier;
        if(var_identifier!=null) var_identifier.setParent(this);
    }

    public Var_identifier getVar_identifier() {
        return var_identifier;
    }

    public void setVar_identifier(Var_identifier var_identifier) {
        this.var_identifier=var_identifier;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(var_identifier!=null) var_identifier.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(var_identifier!=null) var_identifier.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(var_identifier!=null) var_identifier.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarID(\n");

        if(var_identifier!=null)
            buffer.append(var_identifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarID]");
        return buffer.toString();
    }
}
