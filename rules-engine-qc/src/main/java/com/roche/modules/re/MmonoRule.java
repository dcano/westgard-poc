package com.roche.modules.re;

class MmonoRule extends QcRule {

    private final int m;

    MmonoRule(int m) {
        this.m = m;
    }

    int getM() {
        return this.m;
    }

    @Override
    void accept(RuleVisitor visitor) {
        visitor.visit(this);
    }
}
