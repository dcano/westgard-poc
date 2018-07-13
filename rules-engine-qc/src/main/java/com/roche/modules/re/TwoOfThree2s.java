package com.roche.modules.re;

class TwoOfThree2s extends QcRule {


    @Override
    void accept(RuleVisitor visitor) {
        visitor.visit(this);
    }
}
