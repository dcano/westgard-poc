package com.roche.modules.re;

class MxBarRule extends QcRule {

    private final int n;

    MxBarRule(int n) {
        this.n = n;
    }

    int getN() {
        return this.n;
    }

    @Override
    void accept(RuleVisitor visitor) {
        visitor.visit(this);
    }
}
