// generated with ast extension for cup
// version 0.8
// 26/0/2018 19:57:51


package rs.ac.bg.etf.pp1.ast;

public class Program implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Program_name program_name;
    private Declaration_section declaration_section;
    private Method_decl_section method_decl_section;

    public Program (Program_name program_name, Declaration_section declaration_section, Method_decl_section method_decl_section) {
        this.program_name=program_name;
        if(program_name!=null) program_name.setParent(this);
        this.declaration_section=declaration_section;
        if(declaration_section!=null) declaration_section.setParent(this);
        this.method_decl_section=method_decl_section;
        if(method_decl_section!=null) method_decl_section.setParent(this);
    }

    public Program_name getProgram_name() {
        return program_name;
    }

    public void setProgram_name(Program_name program_name) {
        this.program_name=program_name;
    }

    public Declaration_section getDeclaration_section() {
        return declaration_section;
    }

    public void setDeclaration_section(Declaration_section declaration_section) {
        this.declaration_section=declaration_section;
    }

    public Method_decl_section getMethod_decl_section() {
        return method_decl_section;
    }

    public void setMethod_decl_section(Method_decl_section method_decl_section) {
        this.method_decl_section=method_decl_section;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(program_name!=null) program_name.accept(visitor);
        if(declaration_section!=null) declaration_section.accept(visitor);
        if(method_decl_section!=null) method_decl_section.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(program_name!=null) program_name.traverseTopDown(visitor);
        if(declaration_section!=null) declaration_section.traverseTopDown(visitor);
        if(method_decl_section!=null) method_decl_section.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(program_name!=null) program_name.traverseBottomUp(visitor);
        if(declaration_section!=null) declaration_section.traverseBottomUp(visitor);
        if(method_decl_section!=null) method_decl_section.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Program(\n");

        if(program_name!=null)
            buffer.append(program_name.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(declaration_section!=null)
            buffer.append(declaration_section.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(method_decl_section!=null)
            buffer.append(method_decl_section.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Program]");
        return buffer.toString();
    }
}
