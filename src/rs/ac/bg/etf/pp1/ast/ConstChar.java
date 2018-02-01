// generated with ast extension for cup
// version 0.8
// 1/1/2018 17:58:9


package rs.ac.bg.etf.pp1.ast;

public class ConstChar extends Constant {

    private Char_const char_const;

    public ConstChar (Char_const char_const) {
        this.char_const=char_const;
        if(char_const!=null) char_const.setParent(this);
    }

    public Char_const getChar_const() {
        return char_const;
    }

    public void setChar_const(Char_const char_const) {
        this.char_const=char_const;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(char_const!=null) char_const.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(char_const!=null) char_const.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(char_const!=null) char_const.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstChar(\n");

        if(char_const!=null)
            buffer.append(char_const.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstChar]");
        return buffer.toString();
    }
}
