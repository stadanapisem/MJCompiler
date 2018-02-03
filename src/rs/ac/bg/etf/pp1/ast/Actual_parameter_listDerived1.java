// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:45:32


package rs.ac.bg.etf.pp1.ast;

public class Actual_parameter_listDerived1 extends Actual_parameter_list {

    private Actual_parameter_list actual_parameter_list;
    private Actual_parameter actual_parameter;

    public Actual_parameter_listDerived1 (Actual_parameter_list actual_parameter_list, Actual_parameter actual_parameter) {
        this.actual_parameter_list=actual_parameter_list;
        if(actual_parameter_list!=null) actual_parameter_list.setParent(this);
        this.actual_parameter=actual_parameter;
        if(actual_parameter!=null) actual_parameter.setParent(this);
    }

    public Actual_parameter_list getActual_parameter_list() {
        return actual_parameter_list;
    }

    public void setActual_parameter_list(Actual_parameter_list actual_parameter_list) {
        this.actual_parameter_list=actual_parameter_list;
    }

    public Actual_parameter getActual_parameter() {
        return actual_parameter;
    }

    public void setActual_parameter(Actual_parameter actual_parameter) {
        this.actual_parameter=actual_parameter;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(actual_parameter_list!=null) actual_parameter_list.accept(visitor);
        if(actual_parameter!=null) actual_parameter.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(actual_parameter_list!=null) actual_parameter_list.traverseTopDown(visitor);
        if(actual_parameter!=null) actual_parameter.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(actual_parameter_list!=null) actual_parameter_list.traverseBottomUp(visitor);
        if(actual_parameter!=null) actual_parameter.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Actual_parameter_listDerived1(\n");

        if(actual_parameter_list!=null)
            buffer.append(actual_parameter_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(actual_parameter!=null)
            buffer.append(actual_parameter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Actual_parameter_listDerived1]");
        return buffer.toString();
    }
}
