// generated with ast extension for cup
// version 0.8
// 6/1/2018 21:51:44


package rs.ac.bg.etf.pp1.ast;

public class ConstIdTerm extends Const_id_list {

    private Const_id const_id;

    public ConstIdTerm (Const_id const_id) {
        this.const_id=const_id;
        if(const_id!=null) const_id.setParent(this);
    }

    public Const_id getConst_id() {
        return const_id;
    }

    public void setConst_id(Const_id const_id) {
        this.const_id=const_id;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(const_id!=null) const_id.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(const_id!=null) const_id.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(const_id!=null) const_id.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstIdTerm(\n");

        if(const_id!=null)
            buffer.append(const_id.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstIdTerm]");
        return buffer.toString();
    }
}
