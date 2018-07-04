package com.github.dcano.wesgardpoc;

public class RuleEngine {

    public void processRequest(RuleEvaluationRequest ruleEvaluationRequest) {
        WestgardVisitor evalutionVisitor = new RuleEvalutionVisitor(ruleEvaluationRequest.getWestgardRuleContext(), ruleEvalutionResult -> System.out.println("Rule evaluated!!"));
        ruleEvaluationRequest.getRules().forEach(rule -> rule.accept(evalutionVisitor));
    }

}

