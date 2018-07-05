package com.roche.modules.re;

class NxRule extends QcRule {

    private final int n;

    NxRule(int n) {
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
