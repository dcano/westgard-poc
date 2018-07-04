package com.github.dcano.wesgardpoc;

class R4sRule extends WestgardRule {

    @Override
    void accept(WestgardVisitor visitor) {
        visitor.visit(this);
    }
}
