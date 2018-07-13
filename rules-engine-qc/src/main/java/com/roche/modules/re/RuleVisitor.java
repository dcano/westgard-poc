package com.roche.modules.re;

public interface RuleVisitor {
    void visit(RxKsRule rxKsRule);
    void visit(R4sRule r4sRule);
    void visit(MxBarRule nxRule);
    void visit(MmonoRule mmonoRule);
    void visit(RangeRule rangeRule);
    void visit(AlphanumericRule alphanumericRule);
    void visit(TwoOfThree2s twoOfThree2s);
}
