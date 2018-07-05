package com.github.dcano.wesgardpoc;

public class NxRule extends WestgardRule {

    private final int n;

    public NxRule(int n) {
        this.n = n;
    }

    public int getN() {
        return this.n;
    }

    @Override
    void accept(WestgardVisitor visitor) {
        visitor.visit(this);
    }
}
