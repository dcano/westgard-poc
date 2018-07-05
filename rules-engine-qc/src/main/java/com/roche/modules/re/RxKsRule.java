package com.roche.modules.re;

class RxKsRule extends QcRule {

    private final int r;
    private final int k;

    RxKsRule(int r, int k) {
        this.r = r;
        this.k = k;
    }

    @Override
    void accept(RuleVisitor visitor) {
        visitor.visit(this);
    }

    int getR() {
        return r;
    }

    int getK() {
        return k;
    }
}
