package com.roche.modules.re;

import java.util.List;

public class RuleEvaluationRequest {

    private final String requestId;
    private final WestgardRuleContext westgardRuleContext;
    private final List<QcRule> rulesToExecute;

    RuleEvaluationRequest(String id, WestgardRuleContext westgardRuleContext, List<QcRule> rulesToExecute) {
        this.requestId = id;
        this.westgardRuleContext = westgardRuleContext;
        this.rulesToExecute = rulesToExecute;
    }

    public String getRequestId() {
        return requestId;
    }

    WestgardRuleContext getWestgardRuleContext() {
        return westgardRuleContext;
    }

    List<QcRule> getRulesToExecute() {
        return rulesToExecute;
    }

}
