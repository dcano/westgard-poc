package com.roche.modules.re;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.roche.modules.re.ResultLocation.ABOVE_MEAN;
import static com.roche.modules.re.ResultLocation.BELOW_MEAN;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class NxRuleTest {

    private static final double MEAN = 4;
    private static final double SD = 1;
    private final ResultLocation resultLocation;
    private String tenantId = UUID.randomUUID().toString();
    private int N;
    private List<QcResult> resultsUnderEvaluation;
    private EvaluationResult expected;

    public NxRuleTest(int numberOfResults, ResultLocation resultLocation, EvaluationResult expected) {
        this.expected = expected;
        this.resultLocation = resultLocation;
        this.N = numberOfResults;
    }

    @Before
    public void setup() {
        resultsUnderEvaluation = new ArrayList<>();
        tenantId = UUID.randomUUID().toString();
        resultsUnderEvaluation = IntStream.rangeClosed(1, N).mapToObj(i -> randomResult()).collect(Collectors.toList());
    }


    @Test
    public void should_evaluate_qc_results_against_NxS_rule() {
        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, MEAN, SD, resultsUnderEvaluation.get(resultsUnderEvaluation.size()-1), resultsUnderEvaluation);
        RuleVisitor evaluationVisitor = new RuleEvaluationVisitor(westgardRuleContext, ruleEvaluationResult -> assertThat(ruleEvaluationResult.getEvaluationResult()).as("Rule matches").isEqualTo(expected));
        QcRule rule = new NxRule(N);
        rule.accept(evaluationVisitor);
    }

    private QcResult randomResult() {
        if(resultLocation.equals(ABOVE_MEAN)) {
            return new QcResult(RandomStringUtils.randomAlphanumeric(10), MEAN + 0.1 + Integer.parseInt(RandomStringUtils.randomNumeric(1)), ZonedDateTime.now());
        }
        else {
            return new QcResult(RandomStringUtils.randomAlphanumeric(10), MEAN - 0.1 - Integer.parseInt(RandomStringUtils.randomNumeric(1)), ZonedDateTime.now());
        }
    }

    @Parameterized.Parameters
    public static Collection results() {
        return Arrays.asList(new Object[][] {
                {7, ABOVE_MEAN, EvaluationResult.MATCH},
                {8, ABOVE_MEAN, EvaluationResult.MATCH},
                {9, ABOVE_MEAN, EvaluationResult.MATCH},
                {10, ABOVE_MEAN, EvaluationResult.MATCH},
                {12, ABOVE_MEAN, EvaluationResult.MATCH},
                {7, BELOW_MEAN, EvaluationResult.MATCH},
                {8, BELOW_MEAN, EvaluationResult.MATCH},
                {9, BELOW_MEAN, EvaluationResult.MATCH},
                {10, BELOW_MEAN, EvaluationResult.MATCH},
                {12, BELOW_MEAN, EvaluationResult.MATCH}
        });
    }

}
