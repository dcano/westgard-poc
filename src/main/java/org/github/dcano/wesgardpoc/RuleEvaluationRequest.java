package org.github.dcano.wesgardpoc;

import java.util.List;

public class RuleEvaluationRequest {

    private final String id;
    private final WestgardRuleContext westgardRuleContext;
    private final List<WestgardRule> rules;

    RuleEvaluationRequest(String id, WestgardRuleContext westgardRuleContext, List<WestgardRule> rules) {
        this.id = id;
        this.westgardRuleContext = westgardRuleContext;
        this.rules = rules;
    }

    public String getId() {
        return id;
    }

    WestgardRuleContext getWestgardRuleContext() {
        return westgardRuleContext;
    }

    List<WestgardRule> getRules() {
        return rules;
    }
}
