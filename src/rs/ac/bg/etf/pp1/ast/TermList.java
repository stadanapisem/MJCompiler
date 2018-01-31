// generated with ast extension for cup
// version 0.8
// 31/0/2018 13:17:49


package rs.ac.bg.etf.pp1.ast;

public class TermList extends Addition_term_list {

    private Addition_term_list addition_term_list;
    private Addition_operator addition_operator;
    private Term term;

    public TermList (Addition_term_list addition_term_list, Addition_operator addition_operator, Term term) {
        this.addition_term_list=addition_term_list;
        if(addition_term_list!=null) addition_term_list.setParent(this);
        this.addition_operator=addition_operator;
        if(addition_operator!=null) addition_operator.setParent(this);
        this.term=term;
        if(term!=null) term.setParent(this);
    }

    public Addition_term_list getAddition_term_list() {
        return addition_term_list;
    }

    public void setAddition_term_list(Addition_term_list addition_term_list) {
        this.addition_term_list=addition_term_list;
    }

    public Addition_operator getAddition_operator() {
        return addition_operator;
    }

    public void setAddition_operator(Addition_operator addition_operator) {
        this.addition_operator=addition_operator;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term=term;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(addition_term_list!=null) addition_term_list.accept(visitor);
        if(addition_operator!=null) addition_operator.accept(visitor);
        if(term!=null) term.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(addition_term_list!=null) addition_term_list.traverseTopDown(visitor);
        if(addition_operator!=null) addition_operator.traverseTopDown(visitor);
        if(term!=null) term.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(addition_term_list!=null) addition_term_list.traverseBottomUp(visitor);
        if(addition_operator!=null) addition_operator.traverseBottomUp(visitor);
        if(term!=null) term.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TermList(\n");

        if(addition_term_list!=null)
            buffer.append(addition_term_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(addition_operator!=null)
            buffer.append(addition_operator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(term!=null)
            buffer.append(term.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TermList]");
        return buffer.toString();
    }
}
