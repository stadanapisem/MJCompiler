package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.FormalParameter;
import rs.ac.bg.etf.pp1.ast.FormalParameterArray;
import rs.ac.bg.etf.pp1.ast.VarIdentifier;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

    protected int counter = 0;

    public int getCounter() {
        return counter;
    }

    public static class VariableCounter extends CounterVisitor {
        @Override public void visit(VarIdentifier varIdentifier) {
            counter++;
        }
    }

    public static class FormalParamCounter extends CounterVisitor {
        @Override public void visit(FormalParameter formalParameter) {
            counter ++;
        }

        @Override public void visit(FormalParameterArray formalParameterArray) {
            counter ++;
        }
    }
}
