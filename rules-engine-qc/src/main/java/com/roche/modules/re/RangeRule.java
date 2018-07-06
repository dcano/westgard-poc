package com.roche.modules.re;

class RangeRule extends QcRule {

    @Override
    void accept(RuleVisitor visitor) {
        visitor.visit(this);
    }
}
