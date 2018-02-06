package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

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
            counter++;
        }

        @Override public void visit(FormalParameterArray formalParameterArray) {
            counter++;
        }
    }


    public static class FindArrayIndex extends CounterVisitor {
        private boolean first = false;
        private boolean found = false;

        @Override public void visit(DesignatorArray designatorArray) {
            found = true;
        }

        @Override public void visit(AddExpression addExpression) {



        }

        @Override public void visit(NumericConst numericConst) {
            Code.loadConst(numericConst.getVar());
        }

        @Override public void visit(Term Term) {
            Term.obj = Term.getMultiplication_factor_list().obj;
        }

        @Override public void visit(FactorList FactorList) {
            Code.put(Integer.parseInt(FactorList.getMultiplication_operator().string));
            FactorList.obj = FactorList.getMultiplication_factor_list().obj;
        }

        @Override public void visit(TerminalFactor TerminalFactor) {
            TerminalFactor.obj = TerminalFactor.getFactor().obj;
        }

        @Override public void visit(TermList TermList) {
            Code.put(Integer.parseInt(TermList.getAddition_operator().string));
            TermList.obj = TermList.getAddition_term_list().obj;
        }

        @Override public void visit(TerminalTerm TerminalTerm) {
            TerminalTerm.obj = TerminalTerm.getTerm().obj;
        }

        @Override public void visit(NegTerminalTerm NegTerminalTerm) {
            Code.put(Code.neg);
            NegTerminalTerm.obj = NegTerminalTerm.getTerm().obj;
        }

        @Override public void visit(DesignatorFactor designatorFactor) {
            if (designatorFactor.getDesignator().obj != null) {
                Code.load(designatorFactor.getDesignator().obj);
            }
        }

        @Override public void visit(DesignatorSingle DesignatorSingle) {
            DesignatorSingle.obj = Tab.find(DesignatorSingle.getId());
        }
    }
}
