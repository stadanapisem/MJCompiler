// generated with ast extension for cup
// version 0.8
// 1/1/2018 17:58:9


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclarationLine extends Const_declaration_line {

    private Type type;
    private Const_id_list const_id_list;

    public ConstDeclarationLine (Type type, Const_id_list const_id_list) {
        this.type=type;
        if(type!=null) type.setParent(this);
        this.const_id_list=const_id_list;
        if(const_id_list!=null) const_id_list.setParent(this);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type=type;
    }

    public Const_id_list getConst_id_list() {
        return const_id_list;
    }

    public void setConst_id_list(Const_id_list const_id_list) {
        this.const_id_list=const_id_list;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(type!=null) type.accept(visitor);
        if(const_id_list!=null) const_id_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(type!=null) type.traverseTopDown(visitor);
        if(const_id_list!=null) const_id_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(type!=null) type.traverseBottomUp(visitor);
        if(const_id_list!=null) const_id_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclarationLine(\n");

        if(type!=null)
            buffer.append(type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(const_id_list!=null)
            buffer.append(const_id_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclarationLine]");
        return buffer.toString();
    }
}
