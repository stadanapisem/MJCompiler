// generated with ast extension for cup
// version 0.8
// 5/1/2018 22:2:38


package rs.ac.bg.etf.pp1.ast;

public class ConstNumber extends Constant {

    private Numeric_const numeric_const;

    public ConstNumber (Numeric_const numeric_const) {
        this.numeric_const=numeric_const;
        if(numeric_const!=null) numeric_const.setParent(this);
    }

    public Numeric_const getNumeric_const() {
        return numeric_const;
    }

    public void setNumeric_const(Numeric_const numeric_const) {
        this.numeric_const=numeric_const;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(numeric_const!=null) numeric_const.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(numeric_const!=null) numeric_const.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(numeric_const!=null) numeric_const.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstNumber(\n");

        if(numeric_const!=null)
            buffer.append(numeric_const.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstNumber]");
        return buffer.toString();
    }
}
