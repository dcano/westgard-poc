package com.roche.modules.re;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class TwoOfThree2sTest {

    private static final double MEAN = 4;
    private static final double SD = 1;
    private String tenantId = UUID.randomUUID().toString();
    private List<QcResult> resultsUnderEvaluation;
    private EvaluationResult expectedEvaluationResult;

    @Before
    public void setup() {
        tenantId = UUID.randomUUID().toString();
    }

    public TwoOfThree2sTest(final List<QcResult> results, EvaluationResult expected) {
        this.resultsUnderEvaluation = results;
        this.expectedEvaluationResult = expected;
    }

    @Test
    public void should_evaluate_qc_results_against_RxKs_rule() {
        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, MEAN, SD, resultsUnderEvaluation.get(resultsUnderEvaluation.size()-1), resultsUnderEvaluation);
        RuleVisitor evaluationVisitor = new RuleEvaluationVisitor(westgardRuleContext, ruleEvaluationResult -> assertThat(ruleEvaluationResult.getEvaluationResult()).as("Rule matches").isEqualTo(expectedEvaluationResult));
        QcRule rule = new TwoOfThree2s();
        rule.accept(evaluationVisitor);
    }

    @Parameterized.Parameters
    public static Collection results() {
        return Arrays.asList(new Object[][] {
                //2of3_2s above mean 2 MATCH
                {Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 6.1, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 4, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 6.5, ZonedDateTime.now())), EvaluationResult.MATCH},
                //2of3_2s below mean 2 MATCH
                {Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 1.7, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 1.2, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3.8, ZonedDateTime.now())), EvaluationResult.MATCH},

                //2of3_2s above mean NOT_MATCH
                {Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.1, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 4, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 6.2, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},
                //2of3_2s below mean NOT_MATCH
                {Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 2.2, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 1.8, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3.1, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},
                //2of3_2s above mean NONE_MATCH NOT_MATCH
                {Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.2, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.3, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.1, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},
                //2of3_2s below mean NONE_MATCH NOT_MATCH
                {Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 2.9, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3.4, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3.9, ZonedDateTime.now())), EvaluationResult.NOT_MATCH}
        });
    }

}
