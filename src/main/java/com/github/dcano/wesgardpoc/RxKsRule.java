package com.github.dcano.wesgardpoc;

class RxKsRule extends WestgardRule {

    private final int r;
    private final int k;

    public RxKsRule(int r, int k) {
        this.r = r;
        this.k = k;
    }

    @Override
    void accept(WestgardVisitor visitor) {
        visitor.visit(this);
    }

    public int getR() {
        return r;
    }

    public int getK() {
        return k;
    }
}
