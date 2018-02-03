// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:45:32


package rs.ac.bg.etf.pp1.ast;

public class MethodCall extends Factor {

    private Method_call_ident method_call_ident;
    private Actual_parameter_section actual_parameter_section;

    public MethodCall (Method_call_ident method_call_ident, Actual_parameter_section actual_parameter_section) {
        this.method_call_ident=method_call_ident;
        if(method_call_ident!=null) method_call_ident.setParent(this);
        this.actual_parameter_section=actual_parameter_section;
        if(actual_parameter_section!=null) actual_parameter_section.setParent(this);
    }

    public Method_call_ident getMethod_call_ident() {
        return method_call_ident;
    }

    public void setMethod_call_ident(Method_call_ident method_call_ident) {
        this.method_call_ident=method_call_ident;
    }

    public Actual_parameter_section getActual_parameter_section() {
        return actual_parameter_section;
    }

    public void setActual_parameter_section(Actual_parameter_section actual_parameter_section) {
        this.actual_parameter_section=actual_parameter_section;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_call_ident!=null) method_call_ident.accept(visitor);
        if(actual_parameter_section!=null) actual_parameter_section.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_call_ident!=null) method_call_ident.traverseTopDown(visitor);
        if(actual_parameter_section!=null) actual_parameter_section.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_call_ident!=null) method_call_ident.traverseBottomUp(visitor);
        if(actual_parameter_section!=null) actual_parameter_section.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodCall(\n");

        if(method_call_ident!=null)
            buffer.append(method_call_ident.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(actual_parameter_section!=null)
            buffer.append(actual_parameter_section.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodCall]");
        return buffer.toString();
    }
}
