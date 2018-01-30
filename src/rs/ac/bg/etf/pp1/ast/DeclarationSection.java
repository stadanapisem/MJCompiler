// generated with ast extension for cup
// version 0.8
// 30/0/2018 19:5:1


package rs.ac.bg.etf.pp1.ast;

public class DeclarationSection extends Declaration_section {

    private Declaration_section declaration_section;
    private Declaration declaration;

    public DeclarationSection (Declaration_section declaration_section, Declaration declaration) {
        this.declaration_section=declaration_section;
        if(declaration_section!=null) declaration_section.setParent(this);
        this.declaration=declaration;
        if(declaration!=null) declaration.setParent(this);
    }

    public Declaration_section getDeclaration_section() {
        return declaration_section;
    }

    public void setDeclaration_section(Declaration_section declaration_section) {
        this.declaration_section=declaration_section;
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
        if(declaration_section!=null) declaration_section.accept(visitor);
        if(declaration!=null) declaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(declaration_section!=null) declaration_section.traverseTopDown(visitor);
        if(declaration!=null) declaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(declaration_section!=null) declaration_section.traverseBottomUp(visitor);
        if(declaration!=null) declaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DeclarationSection(\n");

        if(declaration_section!=null)
            buffer.append(declaration_section.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(declaration!=null)
            buffer.append(declaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DeclarationSection]");
        return buffer.toString();
    }
}
