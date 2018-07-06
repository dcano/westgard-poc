package com.roche.modules.re;

import java.util.List;

public class AlphanumericRule extends QcRule {

    private List<String> alphanumericList;

    public AlphanumericRule(List<String> alphanumericList) {
        this.alphanumericList = alphanumericList;
    }

    public List<String> getAlphanumericList() {
        return alphanumericList;
    }

    @Override
    void accept(RuleVisitor visitor) {
        visitor.visit(this);
    }
}
