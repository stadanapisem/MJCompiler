// generated with ast extension for cup
// version 0.8
// 5/1/2018 22:2:38


package rs.ac.bg.etf.pp1.ast;

public class AddExpression extends Expression {

    private Addition_term_list addition_term_list;

    public AddExpression (Addition_term_list addition_term_list) {
        this.addition_term_list=addition_term_list;
        if(addition_term_list!=null) addition_term_list.setParent(this);
    }

    public Addition_term_list getAddition_term_list() {
        return addition_term_list;
    }

    public void setAddition_term_list(Addition_term_list addition_term_list) {
        this.addition_term_list=addition_term_list;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(addition_term_list!=null) addition_term_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(addition_term_list!=null) addition_term_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(addition_term_list!=null) addition_term_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddExpression(\n");

        if(addition_term_list!=null)
            buffer.append(addition_term_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddExpression]");
        return buffer.toString();
    }
}
