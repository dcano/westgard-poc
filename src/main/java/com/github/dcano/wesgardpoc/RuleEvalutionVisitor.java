package com.github.dcano.wesgardpoc;

import com.github.rpaulkennedy.jarules.rule.And;
import com.github.rpaulkennedy.jarules.rule.Expr;
import com.github.rpaulkennedy.jarules.rule.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RuleEvalutionVisitor implements WestgardVisitor {

    private final WestgardRuleContext context;
    private final Consumer<RuleEvaluationRespose.RuleEvalutionResult> callback;

    public RuleEvalutionVisitor(WestgardRuleContext context, Consumer<RuleEvaluationRespose.RuleEvalutionResult> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void visit(RxKsRule rxKsRule) {
        List<Rule> rules = new ArrayList<>();

        for(int i = 0; i<rxKsRule.getR(); i++) {
            rules.add(ruleFor(rxKsRule.getK(), i));
        }

        Rule rule = null;

        if(rules.size() > 1) {
            rule = And.of(rules);
        }
        else {
            rule = rules.get(0);
        }

        if(rule.matches(context)) {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.matchingResultFor(rxKsRule));
        }
        else {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.nonMatchingResultFor(rxKsRule));
        }
    }

    private Rule ruleFor(int k, int resultIndex) {
        return Expr.of("contextControls["+resultIndex+"].result > " + k + "*" + context.getSd());
    }

}
