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
public class R4sRuleTest {

    private static final double MEAN = 4;
    private static final double SD = 1;
    private String tenantId = UUID.randomUUID().toString();
    private List<QcResult> resultsUnderEvaluation;
    private EvaluationResult expected;

    public R4sRuleTest(List<QcResult> results, EvaluationResult expected) {
        this.resultsUnderEvaluation = results;
        this.expected = expected;
    }

    @Before
    public void setup() {
        tenantId = UUID.randomUUID().toString();
    }


    @Test
    public void should_evaluate_qc_results_against_r4s_rule() {
        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, MEAN, SD, resultsUnderEvaluation.get(resultsUnderEvaluation.size()-1), resultsUnderEvaluation);
        RuleVisitor evaluationVisitor = new RuleEvaluationVisitor(westgardRuleContext, ruleEvaluationResult -> assertThat(ruleEvaluationResult.getEvaluationResult()).as("Rule matches").isEqualTo(expected));
        QcRule rule = new R4sRule();
        rule.accept(evaluationVisitor);
    }

    @Parameterized.Parameters
    public static Collection results() {
        return Arrays.asList(new Object[][] {
                {Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 6, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 1, ZonedDateTime.now())), EvaluationResult.MATCH},
                {Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.5, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3.0, ZonedDateTime.now())), EvaluationResult.NOT_MATCH}
        });
    }

}
