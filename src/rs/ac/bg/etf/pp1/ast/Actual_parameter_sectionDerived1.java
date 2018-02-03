// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:45:32


package rs.ac.bg.etf.pp1.ast;

public class Actual_parameter_sectionDerived1 extends Actual_parameter_section {

    private Actual_parameter_list actual_parameter_list;

    public Actual_parameter_sectionDerived1 (Actual_parameter_list actual_parameter_list) {
        this.actual_parameter_list=actual_parameter_list;
        if(actual_parameter_list!=null) actual_parameter_list.setParent(this);
    }

    public Actual_parameter_list getActual_parameter_list() {
        return actual_parameter_list;
    }

    public void setActual_parameter_list(Actual_parameter_list actual_parameter_list) {
        this.actual_parameter_list=actual_parameter_list;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(actual_parameter_list!=null) actual_parameter_list.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(actual_parameter_list!=null) actual_parameter_list.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(actual_parameter_list!=null) actual_parameter_list.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Actual_parameter_sectionDerived1(\n");

        if(actual_parameter_list!=null)
            buffer.append(actual_parameter_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Actual_parameter_sectionDerived1]");
        return buffer.toString();
    }
}
