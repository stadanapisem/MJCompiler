// generated with ast extension for cup
// version 0.8
// 31/0/2018 13:17:49


package rs.ac.bg.etf.pp1.ast;

public class ConstBool extends Constant {

    private Bool_const bool_const;

    public ConstBool (Bool_const bool_const) {
        this.bool_const=bool_const;
        if(bool_const!=null) bool_const.setParent(this);
    }

    public Bool_const getBool_const() {
        return bool_const;
    }

    public void setBool_const(Bool_const bool_const) {
        this.bool_const=bool_const;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(bool_const!=null) bool_const.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(bool_const!=null) bool_const.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(bool_const!=null) bool_const.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstBool(\n");

        if(bool_const!=null)
            buffer.append(bool_const.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstBool]");
        return buffer.toString();
    }
}
