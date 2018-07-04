package com.github.dcano.wesgardpoc;

import java.util.List;

class RuleEvaluationRespose {

    private final String requestId;
    private final List<RuleEvalutionResult> results;

    RuleEvaluationRespose(String requestId, List<RuleEvalutionResult> results) {
        this.requestId = requestId;
        this.results = results;
    }

    String getRequestId() {
        return requestId;
    }

    List<RuleEvalutionResult> getResults() {
        return results;
    }

    static class RuleEvalutionResult {
        private final WestgardRule westgardRule;
        private final EvaluationResult evaluationResult;

        RuleEvalutionResult(WestgardRule westgardRule, EvaluationResult evaluationResult) {
            this.westgardRule = westgardRule;
            this.evaluationResult = evaluationResult;
        }

        WestgardRule getWestgardRule() {
            return westgardRule;
        }

        EvaluationResult getEvaluationResult() {
            return evaluationResult;
        }

        static RuleEvalutionResult matchingResultFor(WestgardRule westgardRule) {
            return new RuleEvalutionResult(westgardRule, EvaluationResult.MATCH);
        }

        static RuleEvalutionResult nonMatchingResultFor(WestgardRule westgardRule) {
            return new RuleEvalutionResult(westgardRule, EvaluationResult.NOT_MATCH);
        }

    }

}
