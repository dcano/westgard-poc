package com.github.dcano.wesgardpoc;

import java.util.List;

public class RuleEvaluationRequest {

    private final String requestId;
    private final WestgardRuleContext westgardRuleContext;
    private final List<WestgardRule> rules;

    RuleEvaluationRequest(String id, WestgardRuleContext westgardRuleContext, List<WestgardRule> rules) {
        this.requestId = id;
        this.westgardRuleContext = westgardRuleContext;
        this.rules = rules;
    }

    public String getRequestId() {
        return requestId;
    }

    WestgardRuleContext getWestgardRuleContext() {
        return westgardRuleContext;
    }

    List<WestgardRule> getRules() {
        return rules;
    }
}
