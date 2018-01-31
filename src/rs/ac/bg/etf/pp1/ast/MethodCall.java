// generated with ast extension for cup
// version 0.8
// 31/0/2018 13:17:49


package rs.ac.bg.etf.pp1.ast;

public class MethodCall extends Factor {

    private Designator designator;
    private Actual_parameters actual_parameters;

    public MethodCall (Designator designator, Actual_parameters actual_parameters) {
        this.designator=designator;
        if(designator!=null) designator.setParent(this);
        this.actual_parameters=actual_parameters;
        if(actual_parameters!=null) actual_parameters.setParent(this);
    }

    public Designator getDesignator() {
        return designator;
    }

    public void setDesignator(Designator designator) {
        this.designator=designator;
    }

    public Actual_parameters getActual_parameters() {
        return actual_parameters;
    }

    public void setActual_parameters(Actual_parameters actual_parameters) {
        this.actual_parameters=actual_parameters;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(designator!=null) designator.accept(visitor);
        if(actual_parameters!=null) actual_parameters.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(designator!=null) designator.traverseTopDown(visitor);
        if(actual_parameters!=null) actual_parameters.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(designator!=null) designator.traverseBottomUp(visitor);
        if(actual_parameters!=null) actual_parameters.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodCall(\n");

        if(designator!=null)
            buffer.append(designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(actual_parameters!=null)
            buffer.append(actual_parameters.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodCall]");
        return buffer.toString();
    }
}
