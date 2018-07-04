package com.github.dcano.wesgardpoc;

class RxKsRule extends WestgardRule {

    private final int r;
    private final int k;

    RxKsRule(int r, int k) {
        this.r = r;
        this.k = k;
    }

    @Override
    void accept(WestgardVisitor visitor) {
        visitor.visit(this);
    }

    int getR() {
        return r;
    }

    int getK() {
        return k;
    }
}
