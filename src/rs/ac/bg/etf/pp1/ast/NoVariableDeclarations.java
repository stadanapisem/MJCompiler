// generated with ast extension for cup
// version 0.8
// 24/0/2018 22:42:39


package rs.ac.bg.etf.pp1.ast;

public class NoVariableDeclarations extends Var_declaration_list {

    public NoVariableDeclarations () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoVariableDeclarations(\n");

        buffer.append(tab);
        buffer.append(") [NoVariableDeclarations]");
        return buffer.toString();
    }
}