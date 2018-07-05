package com.roche.modules.re;

class R4sRule extends QcRule {

    @Override
    void accept(RuleVisitor visitor) {
        visitor.visit(this);
    }
}
