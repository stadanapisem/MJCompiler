// generated with ast extension for cup
// version 0.8
// 6/1/2018 21:51:44


package rs.ac.bg.etf.pp1.ast;

public class FormalParamNumFix extends Formal_param_num_fix {

    public FormalParamNumFix () {
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
        buffer.append("FormalParamNumFix(\n");

        buffer.append(tab);
        buffer.append(") [FormalParamNumFix]");
        return buffer.toString();
    }
}
