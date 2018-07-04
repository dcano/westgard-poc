package com.github.dcano.wesgardpoc;

import com.github.dcano.wesgardpoc.EvaluationResult;
import com.github.dcano.wesgardpoc.QcResult;
import com.github.dcano.wesgardpoc.RuleEvalutionVisitor;
import com.github.dcano.wesgardpoc.RxKsRule;
import com.github.dcano.wesgardpoc.WestgardRule;
import com.github.dcano.wesgardpoc.WestgardRuleContext;
import com.github.dcano.wesgardpoc.WestgardVisitor;
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
    public void should_match_qc_results() {
        String mainResultId = UUID.randomUUID().toString();
        QcResult controlUnderEvaluation = new QcResult(mainResultId, 4.3, ZonedDateTime.now());
        List<QcResult> contextControls = new ArrayList<>();
        contextControls.add(controlUnderEvaluation);
        WestgardRuleContext westgardRuleContext = new WestgardRuleContext(tenantId, 4.1, 0.1, controlUnderEvaluation, contextControls);
        int R = 1;
        int K = 1;

        WestgardVisitor evaluationVisitor = new RuleEvalutionVisitor(westgardRuleContext, ruleEvalutionResult -> assertThat(ruleEvalutionResult.getEvaluationResult()).as("Rule matches").isEqualTo(EvaluationResult.MATCH));
        WestgardRule rule = new RxKsRule(R, K);
        rule.accept(evaluationVisitor);
    }

}
