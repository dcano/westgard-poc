package com.roche.modules.re;

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
        private final QcRule westgardRule;
        private final EvaluationResult evaluationResult;
        private final String resultId;

        RuleEvalutionResult(QcRule westgardRule, EvaluationResult evaluationResult, String resultId) {
            this.westgardRule = westgardRule;
            this.evaluationResult = evaluationResult;
            this.resultId = resultId;
        }

        String getResultId() {
            return this.resultId;
        }

        QcRule getWestgardRule() {
            return westgardRule;
        }

        EvaluationResult getEvaluationResult() {
            return evaluationResult;
        }

        static RuleEvalutionResult matchingResultFor(QcRule westgardRule, String resultId) {
            return new RuleEvalutionResult(westgardRule, EvaluationResult.MATCH, resultId);
        }

        static RuleEvalutionResult nonMatchingResultFor(QcRule westgardRule, String resultId) {
            return new RuleEvalutionResult(westgardRule, EvaluationResult.NOT_MATCH, resultId);
        }

    }

}
