// generated with ast extension for cup
// version 0.8
// 5/1/2018 22:2:38


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArrayFld extends Designator {

    private Array_fld_header array_fld_header;
    private Array_fld_fix array_fld_fix;

    public DesignatorArrayFld (Array_fld_header array_fld_header, Array_fld_fix array_fld_fix) {
        this.array_fld_header=array_fld_header;
        if(array_fld_header!=null) array_fld_header.setParent(this);
        this.array_fld_fix=array_fld_fix;
        if(array_fld_fix!=null) array_fld_fix.setParent(this);
    }

    public Array_fld_header getArray_fld_header() {
        return array_fld_header;
    }

    public void setArray_fld_header(Array_fld_header array_fld_header) {
        this.array_fld_header=array_fld_header;
    }

    public Array_fld_fix getArray_fld_fix() {
        return array_fld_fix;
    }

    public void setArray_fld_fix(Array_fld_fix array_fld_fix) {
        this.array_fld_fix=array_fld_fix;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(array_fld_header!=null) array_fld_header.accept(visitor);
        if(array_fld_fix!=null) array_fld_fix.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(array_fld_header!=null) array_fld_header.traverseTopDown(visitor);
        if(array_fld_fix!=null) array_fld_fix.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(array_fld_header!=null) array_fld_header.traverseBottomUp(visitor);
        if(array_fld_fix!=null) array_fld_fix.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorArrayFld(\n");

        if(array_fld_header!=null)
            buffer.append(array_fld_header.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(array_fld_fix!=null)
            buffer.append(array_fld_fix.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArrayFld]");
        return buffer.toString();
    }
}
