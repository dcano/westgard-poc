package com.github.dcano.wesgardpoc;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class RxKsRuleTest {

    private static final double MEAN = 4;
    private static final double SD = 1;
    private String tenantId = UUID.randomUUID().toString();
    private int K;
    private int R;
    private List<QcResult> resultsUnderEvaluation;
    private EvaluationResult expectedEvaluationResult;

    @Before
    public void setup() {
        tenantId = UUID.randomUUID().toString();
    }

    public RxKsRuleTest(int R, int K, final List<QcResult> results, EvaluationResult expected) {
        this.K = K;
        this.R = R;
        this.resultsUnderEvaluation = results;
        this.expectedEvaluationResult = expected;
    }

    @Test
    public void should_evaluate_qc_results_against_RxKs_rule() {
        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, MEAN, SD, resultsUnderEvaluation.get(resultsUnderEvaluation.size()-1), resultsUnderEvaluation);
        WestgardVisitor evaluationVisitor = new RuleEvaluationVisitor(westgardRuleContext, ruleEvaluationResult -> assertThat(ruleEvaluationResult.getEvaluationResult()).as("Rule matches").isEqualTo(expectedEvaluationResult));
        WestgardRule rule = new RxKsRule(R, K);
        rule.accept(evaluationVisitor);
    }


    @Parameterized.Parameters
    public static Collection results() {
        return Arrays.asList(new Object[][] {
                //1x2s above mean MATCH
                {1, 2, Collections.singletonList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 6, ZonedDateTime.now())), EvaluationResult.MATCH},
                //1x2s below mean MATCH
                {1, 2, Collections.singletonList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 2, ZonedDateTime.now())), EvaluationResult.MATCH},
                //1x3s above mean MATCH
                {1, 3, Collections.singletonList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 7, ZonedDateTime.now())), EvaluationResult.MATCH},
                //1x3s below mean MATCH
                {1, 3, Collections.singletonList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 1, ZonedDateTime.now())), EvaluationResult.MATCH},
                //2x2s above mean MATCH
                {2, 2, Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 6, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 7, ZonedDateTime.now())), EvaluationResult.MATCH},
                //2x2s below mean MATCH
                {2, 2, Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 2, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 1, ZonedDateTime.now())), EvaluationResult.MATCH},
                //3x1s above mean MATCH
                {3, 1, Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 5, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 6, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 5, ZonedDateTime.now())), EvaluationResult.MATCH},
                //3x1s below mean MATCH
                {3, 1, Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 3, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 2, ZonedDateTime.now())), EvaluationResult.MATCH},
                //4x1s above mean MATCH
                {4, 1, Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 5, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 6, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 7, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 5, ZonedDateTime.now())), EvaluationResult.MATCH},
                //4x1s below mean MATCH
                {4, 1, Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 3, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 2, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3, ZonedDateTime.now())), EvaluationResult.MATCH},


                //1x2s above mean NOT_MATCH
                {1, 2, Collections.singletonList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.1, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},
                //1x2s below mean NOT_MATCH
                {1, 2, Collections.singletonList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 3.9, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},
                //1x3s above mean NOT_MATCH
                {1, 3, Collections.singletonList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 5, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},
                //1x3s below mean NOT_MATCH
                {1, 3, Collections.singletonList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 2, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},
                //2x2s above mean NOT_MATCH
                {2, 2, Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.1, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.3, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},
                //2x2s below mean NOT_MATCH
                {2, 2, Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 2, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3.9, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},
                //3x1s above mean NOT_MATCH
                {3, 1, Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.2, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.3, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.1, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},
                //3x1s below mean NOT_MATCH
                {3, 1, Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 2.9, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3.4, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3.9, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},
                //4x1s above mean NOT_MATCH
                {4, 1, Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.1, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.2, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 5, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 4.1, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},
                //4x1s below mean NOT_MATCH
                {4, 1, Arrays.asList(new QcResult(RandomStringUtils.randomAlphanumeric(10), 3, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3.3, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 3.2, ZonedDateTime.now()), new QcResult(RandomStringUtils.randomAlphanumeric(10), 4, ZonedDateTime.now())), EvaluationResult.NOT_MATCH},

        });
    }

}
