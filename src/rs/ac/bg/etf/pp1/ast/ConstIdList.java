// generated with ast extension for cup
// version 0.8
// 30/0/2018 19:5:1


package rs.ac.bg.etf.pp1.ast;

public class ConstIdList extends Const_id_list {

    private Const_id_list const_id_list;
    private Const_id const_id;

    public ConstIdList (Const_id_list const_id_list, Const_id const_id) {
        this.const_id_list=const_id_list;
        if(const_id_list!=null) const_id_list.setParent(this);
        this.const_id=const_id;
        if(const_id!=null) const_id.setParent(this);
    }

    public Const_id_list getConst_id_list() {
        return const_id_list;
    }

    public void setConst_id_list(Const_id_list const_id_list) {
        this.const_id_list=const_id_list;
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
        if(const_id_list!=null) const_id_list.accept(visitor);
        if(const_id!=null) const_id.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(const_id_list!=null) const_id_list.traverseTopDown(visitor);
        if(const_id!=null) const_id.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(const_id_list!=null) const_id_list.traverseBottomUp(visitor);
        if(const_id!=null) const_id.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstIdList(\n");

        if(const_id_list!=null)
            buffer.append(const_id_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(const_id!=null)
            buffer.append(const_id.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstIdList]");
        return buffer.toString();
    }
}
