package com.roche.modules.re;

public interface RuleVisitor {
    void visit(RxKsRule rxKsRule);
    void visit(R4sRule r4sRule);
    void visit(NxRule nxRule);
}