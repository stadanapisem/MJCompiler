// generated with ast extension for cup
// version 0.8
// 3/1/2018 21:47:4


package rs.ac.bg.etf.pp1.ast;

public class MethodIdentifier extends Method_identifier {

    private Method_return_type method_return_type;
    private String id;

    public MethodIdentifier (Method_return_type method_return_type, String id) {
        this.method_return_type=method_return_type;
        if(method_return_type!=null) method_return_type.setParent(this);
        this.id=id;
    }

    public Method_return_type getMethod_return_type() {
        return method_return_type;
    }

    public void setMethod_return_type(Method_return_type method_return_type) {
        this.method_return_type=method_return_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(method_return_type!=null) method_return_type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(method_return_type!=null) method_return_type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(method_return_type!=null) method_return_type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodIdentifier(\n");

        if(method_return_type!=null)
            buffer.append(method_return_type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+id);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodIdentifier]");
        return buffer.toString();
    }
}
