package com.roche.modules.re;

import org.apache.commons.lang3.RandomStringUtils;
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
public class AlphanumericResultTest {

    private static final double MEAN = 4;
    private static final double SD = 1;
    private String tenantId = UUID.randomUUID().toString();
    private QcResult qcResult;
    private EvaluationResult expectedEvaluationResult;
    private List<String> ruleValues;

    public AlphanumericResultTest(QcResult qcResult, List<String> ruleValues, EvaluationResult expectedEvaluationResult) {
        this.qcResult = qcResult;
        this.expectedEvaluationResult = expectedEvaluationResult;
        this.ruleValues = ruleValues;
    }

    @Test
    public void should_evaluate_qc_results_against_alphanumeric_rule() {
        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, MEAN, SD, qcResult, Collections.singletonList(qcResult));
        RuleVisitor evaluationVisitor = new RuleEvaluationVisitor(westgardRuleContext, ruleEvaluationResult -> assertThat(ruleEvaluationResult.getEvaluationResult()).as("Rule matches").isEqualTo(expectedEvaluationResult));
        QcRule rule = new AlphanumericRule(ruleValues);
        rule.accept(evaluationVisitor);
    }

    @Parameterized.Parameters
    public static Collection results() {
        return Arrays.asList(new Object[][] {
                {new QcResult(RandomStringUtils.randomAlphanumeric(10), 6, ZonedDateTime.now(), 4, 7, Arrays.asList("AAAA", "BBBB")), Arrays.asList("DDDD", "EEEE", "AAAA"), EvaluationResult.MATCH},
                {new QcResult(RandomStringUtils.randomAlphanumeric(10), 6, ZonedDateTime.now(), 2, 5, Arrays.asList("CCCC", "ZZZZ")), Arrays.asList("DDDD", "EEEE", "AAAA"), EvaluationResult.NOT_MATCH}
        });
    }

}
