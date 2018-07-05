package com.github.dcano.wesgardpoc;

public interface WestgardVisitor {
    void visit(RxKsRule rxKsRule);
    void visit(R4sRule r4sRule);
    void visit(NxRule nxRule);
}
