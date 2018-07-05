package com.roche.modules.re;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RuleEngine {

    private Map<String, QcRule> registeredRules = new ConcurrentHashMap<>();


    public void processRequest(RuleEvaluationRequest ruleEvaluationRequest) {
        RuleVisitor evalutionVisitor = new RuleEvaluationVisitor(ruleEvaluationRequest.getWestgardRuleContext(), ruleEvalutionResult -> System.out.println("Rule evaluated!!"));
        ruleEvaluationRequest.getRulesToExecute().forEach(rule -> rule.accept(evalutionVisitor));
    }

    public void registerRule(String ruleId, QcRule qcRule) {
        this.registeredRules.put(ruleId, qcRule);
    }

}

