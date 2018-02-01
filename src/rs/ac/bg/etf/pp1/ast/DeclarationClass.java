// generated with ast extension for cup
// version 0.8
// 1/1/2018 17:58:9


package rs.ac.bg.etf.pp1.ast;

public class DeclarationClass extends Declaration {

    private Class_declaration class_declaration;

    public DeclarationClass (Class_declaration class_declaration) {
        this.class_declaration=class_declaration;
        if(class_declaration!=null) class_declaration.setParent(this);
    }

    public Class_declaration getClass_declaration() {
        return class_declaration;
    }

    public void setClass_declaration(Class_declaration class_declaration) {
        this.class_declaration=class_declaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(class_declaration!=null) class_declaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(class_declaration!=null) class_declaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(class_declaration!=null) class_declaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationClass(\n");

        if(class_declaration!=null)
            buffer.append(class_declaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationClass]");
        return buffer.toString();
    }
}
