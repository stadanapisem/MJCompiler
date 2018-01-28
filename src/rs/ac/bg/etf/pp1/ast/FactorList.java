// generated with ast extension for cup
// version 0.8
// 28/0/2018 21:0:58


package rs.ac.bg.etf.pp1.ast;

public class FactorList extends Multiplication_factor_list {

    private Multiplication_factor_list multiplication_factor_list;
    private Multiplication_operator multiplication_operator;
    private Factor factor;

    public FactorList (Multiplication_factor_list multiplication_factor_list, Multiplication_operator multiplication_operator, Factor factor) {
        this.multiplication_factor_list=multiplication_factor_list;
        if(multiplication_factor_list!=null) multiplication_factor_list.setParent(this);
        this.multiplication_operator=multiplication_operator;
        if(multiplication_operator!=null) multiplication_operator.setParent(this);
        this.factor=factor;
        if(factor!=null) factor.setParent(this);
    }

    public Multiplication_factor_list getMultiplication_factor_list() {
        return multiplication_factor_list;
    }

    public void setMultiplication_factor_list(Multiplication_factor_list multiplication_factor_list) {
        this.multiplication_factor_list=multiplication_factor_list;
    }

    public Multiplication_operator getMultiplication_operator() {
        return multiplication_operator;
    }

    public void setMultiplication_operator(Multiplication_operator multiplication_operator) {
        this.multiplication_operator=multiplication_operator;
    }

    public Factor getFactor() {
        return factor;
    }

    public void setFactor(Factor factor) {
        this.factor=factor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(multiplication_factor_list!=null) multiplication_factor_list.accept(visitor);
        if(multiplication_operator!=null) multiplication_operator.accept(visitor);
        if(factor!=null) factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(multiplication_factor_list!=null) multiplication_factor_list.traverseTopDown(visitor);
        if(multiplication_operator!=null) multiplication_operator.traverseTopDown(visitor);
        if(factor!=null) factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(multiplication_factor_list!=null) multiplication_factor_list.traverseBottomUp(visitor);
        if(multiplication_operator!=null) multiplication_operator.traverseBottomUp(visitor);
        if(factor!=null) factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorList(\n");

        if(multiplication_factor_list!=null)
            buffer.append(multiplication_factor_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(multiplication_operator!=null)
            buffer.append(multiplication_operator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(factor!=null)
            buffer.append(factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorList]");
        return buffer.toString();
    }
}
