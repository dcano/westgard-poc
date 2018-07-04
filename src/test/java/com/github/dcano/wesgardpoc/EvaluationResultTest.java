package com.github.dcano.wesgardpoc;

import org.junit.Before;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class EvaluationResultTest {

    private String requestId = UUID.randomUUID().toString();
    private String tenantId = UUID.randomUUID().toString();

    @Before
    public void setup() {
        requestId = UUID.randomUUID().toString();
        tenantId = UUID.randomUUID().toString();
    }

    @Test
    public void should_match_qc_results_for_1x2s_above_mean() {
        String mainResultId = UUID.randomUUID().toString();
        QcResult controlUnderEvaluation = new QcResult(mainResultId, 4.4, ZonedDateTime.now());
        List<QcResult> contextControls = new ArrayList<>();
        contextControls.add(controlUnderEvaluation);
        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, 4.1, 0.1, controlUnderEvaluation, contextControls);
        int R = 1;
        int K = 2;

        WestgardVisitor evaluationVisitor = new RuleEvaluationVisitor(westgardRuleContext, ruleEvalutionResult -> assertThat(ruleEvalutionResult.getEvaluationResult()).as("Rule matches").isEqualTo(EvaluationResult.MATCH));
        WestgardRule rule = new RxKsRule(R, K);
        rule.accept(evaluationVisitor);
    }


    @Test
    public void should_match_qc_results_for_1x2s_below_mean() {
        String mainResultId = UUID.randomUUID().toString();
        QcResult controlUnderEvaluation = new QcResult(mainResultId, 3.7, ZonedDateTime.now());
        List<QcResult> contextControls = new ArrayList<>();
        contextControls.add(controlUnderEvaluation);

        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, 4.1, 0.1, controlUnderEvaluation, contextControls);
        int R = 1;
        int K = 2;

        WestgardVisitor evaluationVisitor = new RuleEvaluationVisitor(westgardRuleContext, ruleEvaluationResult -> assertThat(ruleEvaluationResult.getEvaluationResult()).as("Rule matches").isEqualTo(EvaluationResult.MATCH));
        WestgardRule rule = new RxKsRule(R, K);
        rule.accept(evaluationVisitor);
    }

    @Test
    public void should_match_qc_results_for_1x3s_above_mean() {
        String mainResultId = UUID.randomUUID().toString();
        QcResult controlUnderEvaluation = new QcResult(mainResultId, 4.5, ZonedDateTime.now());
        List<QcResult> contextControls = new ArrayList<>();
        contextControls.add(controlUnderEvaluation);
        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, 4.1, 0.1, controlUnderEvaluation, contextControls);
        int R = 1;
        int K = 3;

        WestgardVisitor evaluationVisitor = new RuleEvaluationVisitor(westgardRuleContext, ruleEvaluationResult -> assertThat(ruleEvaluationResult.getEvaluationResult()).as("Rule matches").isEqualTo(EvaluationResult.MATCH));
        WestgardRule rule = new RxKsRule(R, K);
        rule.accept(evaluationVisitor);
    }

    @Test
    public void should_match_qc_results_for_1x3s_below_mean() {
        String mainResultId = UUID.randomUUID().toString();
        QcResult controlUnderEvaluation = new QcResult(mainResultId, 3.6, ZonedDateTime.now());
        List<QcResult> contextControls = new ArrayList<>();
        contextControls.add(controlUnderEvaluation);
        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, 4.1, 0.1, controlUnderEvaluation, contextControls);
        int R = 1;
        int K = 3;

        WestgardVisitor evaluationVisitor = new RuleEvaluationVisitor(westgardRuleContext, ruleEvaluationResult -> assertThat(ruleEvaluationResult.getEvaluationResult()).as("Rule matches").isEqualTo(EvaluationResult.MATCH));
        WestgardRule rule = new RxKsRule(R, K);
        rule.accept(evaluationVisitor);
    }


    @Test
    public void should_match_qc_results_for_2x2s_above_mean() {
        String mainResultId = UUID.randomUUID().toString();
        QcResult previousControl = new QcResult(mainResultId, 4.7, ZonedDateTime.now().minusDays(1));
        QcResult controlUnderEvaluation = new QcResult(mainResultId, 4.5, ZonedDateTime.now());
        List<QcResult> contextControls = new ArrayList<>();
        contextControls.add(previousControl);
        contextControls.add(controlUnderEvaluation);
        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, 4.1, 0.1, controlUnderEvaluation, contextControls);
        int R = 2;
        int K = 2;

        WestgardVisitor evaluationVisitor = new RuleEvaluationVisitor(westgardRuleContext, ruleEvaluationResult -> assertThat(ruleEvaluationResult.getEvaluationResult()).as("Rule matches").isEqualTo(EvaluationResult.MATCH));
        WestgardRule rule = new RxKsRule(R, K);
        rule.accept(evaluationVisitor);
    }

    @Test
    public void should_match_qc_results_for_2x2s_below_mean() {
        String mainResultId = UUID.randomUUID().toString();
        QcResult previousControl = new QcResult(mainResultId, 3.6, ZonedDateTime.now().minusDays(1));
        QcResult controlUnderEvaluation = new QcResult(mainResultId, 3.5, ZonedDateTime.now());
        List<QcResult> contextControls = new ArrayList<>();
        contextControls.add(previousControl);
        contextControls.add(controlUnderEvaluation);
        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, 4.1, 0.1, controlUnderEvaluation, contextControls);
        int R = 2;
        int K = 2;

        WestgardVisitor evaluationVisitor = new RuleEvaluationVisitor(westgardRuleContext, ruleEvaluationResult -> assertThat(ruleEvaluationResult.getEvaluationResult()).as("Rule matches").isEqualTo(EvaluationResult.MATCH));
        WestgardRule rule = new RxKsRule(R, K);
        rule.accept(evaluationVisitor);
    }

}
