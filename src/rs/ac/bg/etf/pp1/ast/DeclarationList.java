// generated with ast extension for cup
// version 0.8
// 24/0/2018 22:42:39


package rs.ac.bg.etf.pp1.ast;

public class DeclarationList extends Declaration_list {

    private Declaration_list declaration_list;
    private Declaration declaration;

    public DeclarationList (Declaration_list declaration_list, Declaration declaration) {
        this.declaration_list=declaration_list;
        if(declaration_list!=null) declaration_list.setParent(this);
        this.declaration=declaration;
        if(declaration!=null) declaration.setParent(this);
    }

    public Declaration_list getDeclaration_list() {
        return declaration_list;
    }

    public void setDeclaration_list(Declaration_list declaration_list) {
        this.declaration_list=declaration_list;
    }

    public Declaration getDeclaration() {
        return declaration;
    }

    public void setDeclaration(Declaration declaration) {
        this.declaration=declaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(declaration_list!=null) declaration_list.accept(visitor);
        if(declaration!=null) declaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(declaration_list!=null) declaration_list.traverseTopDown(visitor);
        if(declaration!=null) declaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(declaration_list!=null) declaration_list.traverseBottomUp(visitor);
        if(declaration!=null) declaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationList(\n");

        if(declaration_list!=null)
            buffer.append(declaration_list.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(declaration!=null)
            buffer.append(declaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationList]");
        return buffer.toString();
    }
}
