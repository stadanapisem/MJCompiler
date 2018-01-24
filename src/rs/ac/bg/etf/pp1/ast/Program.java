// generated with ast extension for cup
// version 0.8
// 24/0/2018 22:42:39


package rs.ac.bg.etf.pp1.ast;

public class Program implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Program_name program_name;
    private Declaration_list declaration_list;
    private Method_decl_section method_decl_section;

    public Program (Program_name program_name, Declaration_list declaration_list, Method_decl_section method_decl_section) {
        this.program_name=program_name;
        if(program_name!=null) program_name.setParent(this);
        this.declaration_list=declaration_list;
        if(declaration_list!=null) declaration_list.setParent(this);
        this.method_decl_section=method_decl_section;
        if(method_decl_section!=null) method_decl_section.setParent(this);
    }

    public Program_name getProgram_name() {
        return program_name;
    }

    public void setProgram_name(Program_name program_name) {
        this.program_name=program_name;
    }

    public Declaration_list getDeclaration_list() {
        return declaration_list;
    }

    public void setDeclaration_list(Declaration_list declaration_list) {
        this.declaration_list=declaration_list;
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
        if(declaration_list!=null) declaration_list.accept(visitor);
        if(method_decl_section!=null) method_decl_section.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(program_name!=null) program_name.traverseTopDown(visitor);
        if(declaration_list!=null) declaration_list.traverseTopDown(visitor);
        if(method_decl_section!=null) method_decl_section.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(program_name!=null) program_name.traverseBottomUp(visitor);
        if(declaration_list!=null) declaration_list.traverseBottomUp(visitor);
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

        if(declaration_list!=null)
            buffer.append(declaration_list.toString("  "+tab));
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
