// generated with ast extension for cup
// version 0.8
// 5/1/2018 22:2:38


package rs.ac.bg.etf.pp1.ast;

public class ConstId extends Const_id {

    private Const_identifier const_identifier;
    private Constant constant;

    public ConstId (Const_identifier const_identifier, Constant constant) {
        this.const_identifier=const_identifier;
        if(const_identifier!=null) const_identifier.setParent(this);
        this.constant=constant;
        if(constant!=null) constant.setParent(this);
    }

    public Const_identifier getConst_identifier() {
        return const_identifier;
    }

    public void setConst_identifier(Const_identifier const_identifier) {
        this.const_identifier=const_identifier;
    }

    public Constant getConstant() {
        return constant;
    }

    public void setConstant(Constant constant) {
        this.constant=constant;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(const_identifier!=null) const_identifier.accept(visitor);
        if(constant!=null) constant.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(const_identifier!=null) const_identifier.traverseTopDown(visitor);
        if(constant!=null) constant.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(const_identifier!=null) const_identifier.traverseBottomUp(visitor);
        if(constant!=null) constant.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstId(\n");

        if(const_identifier!=null)
            buffer.append(const_identifier.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(constant!=null)
            buffer.append(constant.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstId]");
        return buffer.toString();
    }
}
