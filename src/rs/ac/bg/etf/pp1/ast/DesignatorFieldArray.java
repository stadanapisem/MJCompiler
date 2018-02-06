// generated with ast extension for cup
// version 0.8
// 6/1/2018 21:51:44


package rs.ac.bg.etf.pp1.ast;

public class DesignatorFieldArray extends Array_fld_header {

    private Designator designator;
    private Array_fld_ident array_fld_ident;

    public DesignatorFieldArray (Designator designator, Array_fld_ident array_fld_ident) {
        this.designator=designator;
        if(designator!=null) designator.setParent(this);
        this.array_fld_ident=array_fld_ident;
        if(array_fld_ident!=null) array_fld_ident.setParent(this);
    }

    public Designator getDesignator() {
        return designator;
    }

    public void setDesignator(Designator designator) {
        this.designator=designator;
    }

    public Array_fld_ident getArray_fld_ident() {
        return array_fld_ident;
    }

    public void setArray_fld_ident(Array_fld_ident array_fld_ident) {
        this.array_fld_ident=array_fld_ident;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(designator!=null) designator.accept(visitor);
        if(array_fld_ident!=null) array_fld_ident.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(designator!=null) designator.traverseTopDown(visitor);
        if(array_fld_ident!=null) array_fld_ident.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(designator!=null) designator.traverseBottomUp(visitor);
        if(array_fld_ident!=null) array_fld_ident.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorFieldArray(\n");

        if(designator!=null)
            buffer.append(designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(array_fld_ident!=null)
            buffer.append(array_fld_ident.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorFieldArray]");
        return buffer.toString();
    }
}
