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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class MmonoRuleTest {
    private static final String ASCENDING = "ascending";
    private static final String DESCENDING = "ascending";
    private static final double MEAN = 4;
    private static final double SD = 1;
    private String tenantId = UUID.randomUUID().toString();
    private int M;
    private List<QcResult> resultsUnderEvaluation;
    private EvaluationResult expected;
    private String trend;

    public MmonoRuleTest(int numberOfResults, String trend, EvaluationResult expected) {
        this.expected = expected;
        this.M = numberOfResults;
        this.trend = trend;
    }

    @Before
    public void setup() {
        resultsUnderEvaluation = new ArrayList<>();
        tenantId = UUID.randomUUID().toString();
        resultsUnderEvaluation = IntStream.rangeClosed(1, M).mapToObj(this::randomResult).collect(Collectors.toList());
    }

    @Test
    public void should_evaluate_qc_results_against_mmono_rule() {
        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, MEAN, SD, resultsUnderEvaluation.get(resultsUnderEvaluation.size()-1), resultsUnderEvaluation);
        RuleVisitor evaluationVisitor = new RuleEvaluationVisitor(westgardRuleContext, ruleEvaluationResult -> assertThat(ruleEvaluationResult.getEvaluationResult()).as("Rule matches").isEqualTo(expected));
        QcRule rule = new MxBarRule(M);
        rule.accept(evaluationVisitor);
    }

    private QcResult randomResult(int iteration) {
        if(trend.equals(ASCENDING)) {
            return new QcResult(RandomStringUtils.randomAlphanumeric(10), MEAN + (0.2*iteration), ZonedDateTime.now());
        }
        else {
            return new QcResult(RandomStringUtils.randomAlphanumeric(10), MEAN - (0.2*iteration), ZonedDateTime.now());
        }
    }

    @Parameterized.Parameters
    public static Collection results() {
        return Arrays.asList(new Object[][] {
                {7, ASCENDING, EvaluationResult.MATCH},
                {8, ASCENDING, EvaluationResult.MATCH},
                {9, ASCENDING, EvaluationResult.MATCH},
                {10, ASCENDING, EvaluationResult.MATCH},
                {12, ASCENDING, EvaluationResult.MATCH},
                {7, DESCENDING, EvaluationResult.MATCH},
                {8, DESCENDING, EvaluationResult.MATCH},
                {9, DESCENDING, EvaluationResult.MATCH},
                {10, DESCENDING, EvaluationResult.MATCH},
                {12, DESCENDING, EvaluationResult.MATCH}
        });
    }

}
