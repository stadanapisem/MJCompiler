// generated with ast extension for cup
// version 0.8
// 30/0/2018 19:5:1


package rs.ac.bg.etf.pp1.ast;

public class FormalParameters extends Formal_parameter_section {

    private Formal_parameter_list formal_parameter_list;

    public FormalParameters (Formal_parameter_list formal_parameter_list) {
        this.formal_parameter_list=formal_parameter_list;
        if(formal_parameter_list!=null) formal_parameter_list.setParent(this);
    }

    public Formal_parameter_list getFormal_parameter_list() {
        return formal_parameter_list;
    }

    public void setFormal_parameter_list(Formal_parameter_list formal_parameter_list) {
        this.formal_parameter_list=formal_parameter_list;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(formal_parameter_list!=null) formal_parameter_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(formal_parameter_list!=null) formal_parameter_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(formal_parameter_list!=null) formal_parameter_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormalParameters(\n");

        if(formal_parameter_list!=null)
            buffer.append(formal_parameter_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormalParameters]");
        return buffer.toString();
    }
}
