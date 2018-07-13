package com.roche.modules.re;

import com.github.rpaulkennedy.jarules.rule.And;
import com.github.rpaulkennedy.jarules.rule.Expr;
import com.github.rpaulkennedy.jarules.rule.Or;
import com.github.rpaulkennedy.jarules.rule.Rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RuleEvaluationVisitor implements RuleVisitor {

    private final WestgardRuleContext context;
    private final Consumer<RuleEvaluationRespose.RuleEvalutionResult> callback;

    public RuleEvaluationVisitor(WestgardRuleContext context, Consumer<RuleEvaluationRespose.RuleEvalutionResult> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    public void visit(RxKsRule rxKsRule) {
        Rule rule;
        List<Rule> aboveMeanRules = new ArrayList<>();
        List<Rule> belowMeanRules = new ArrayList<>();
        int resultUnderEvaluationIndex = context.getContextControls().size() - 1;

        for(int i = resultUnderEvaluationIndex; i > resultUnderEvaluationIndex - rxKsRule.getR(); i--) {
            aboveMeanRules.add(rxKsAboveRuleFor(rxKsRule.getK(), i));
            belowMeanRules.add(rxKsBelowRuleFor(rxKsRule.getK(), i));
        }


        if(rxKsRule.getR() > 1) {
            rule = And.of(aboveMeanRules).or(And.of(belowMeanRules));
        }
        else {
            rule = Or.of(aboveMeanRules.get(0), belowMeanRules.get(0));
        }

        if(rule.matches(context)) {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.matchingResultFor(rxKsRule, context.getControlUnderEvaluation().getId()));
        }
        else {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.nonMatchingResultFor(rxKsRule, context.getControlUnderEvaluation().getId()));
        }
    }

    @Override
    public void visit(R4sRule r4sRule) {
        Rule rule = Expr.of("(T(java.lang.Math).abs((contextControls[0].result - " + context.getMean() + ")) +  T(java.lang.Math).abs((contextControls[1].result - " + context.getMean() + "))) >= 4*" + context.getSd());
        if(rule.matches(context)) {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.matchingResultFor(r4sRule, context.getControlUnderEvaluation().getId()));
        }
        else {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.nonMatchingResultFor(r4sRule, context.getControlUnderEvaluation().getId()));
        }
    }

    @Override
    public void visit(MxBarRule nxRule) {
        if(context.getContextControls().size() < nxRule.getN()) throw new IllegalStateException("No enough results available"); //TODO consider using custom runtime exception

        Rule rule;

        List<Rule> aboveRules = new ArrayList<>();
        List<Rule> belowRules = new ArrayList<>();

        int resultUnderEvaluationIndex = context.getContextControls().size() - 1;
        for(int i = resultUnderEvaluationIndex; i > resultUnderEvaluationIndex - nxRule.getN() + 1; i--) {
            aboveRules.add(nxAboveRuleFor(i));
            belowRules.add(nxBelowRuleFor(i));
        }

        rule = And.of(aboveRules).or(And.of(belowRules));

        if(rule.matches(context)) {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.matchingResultFor(nxRule, context.getControlUnderEvaluation().getId()));
        }
        else {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.nonMatchingResultFor(nxRule, context.getControlUnderEvaluation().getId()));
        }

    }

    @Override
    public void visit(MmonoRule mmonoRule) {
        if(context.getContextControls().size() < mmonoRule.getM()) throw new IllegalStateException("No enough results available"); //TODO consider using custom runtime exception
        Rule rule;
        List<Rule> ascendantTrendRules = new ArrayList<>();
        List<Rule> descendantTrendRules = new ArrayList<>();

        int resultUnderEvaluationIndex = context.getContextControls().size() - 1;
        for(int i = resultUnderEvaluationIndex; i > resultUnderEvaluationIndex - mmonoRule.getM(); i--) {
            ascendantTrendRules.add(mmonoAscendantRuleFor(i));
            descendantTrendRules.add(mmonoDescendantRuleFor(i));
        }

        rule = And.of(ascendantTrendRules).or(And.of(descendantTrendRules));

        if(rule.matches(context)) {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.matchingResultFor(mmonoRule, context.getControlUnderEvaluation().getId()));
        }
        else {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.nonMatchingResultFor(mmonoRule, context.getControlUnderEvaluation().getId()));
        }

    }

    @Override
    public void visit(RangeRule rangeRule) {
        Rule rule = Expr.of("T(com.roche.modules.re.RuleEvaluationVisitor).isOutsideRange(contextControls[0].min, contextControls[0].max, contextControls[0].result)");
        if(rule.matches(context)) {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.matchingResultFor(rangeRule, context.getControlUnderEvaluation().getId()));
        }
        else {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.nonMatchingResultFor(rangeRule, context.getControlUnderEvaluation().getId()));
        }
    }

    @Override
    public void visit(AlphanumericRule alphanumericRule) {
        Rule rule = Expr.of("T(com.roche.modules.re.RuleEvaluationVisitor).contains(T(java.util.Arrays).asList("+alphanumericRule.getAlphanumericList().stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(","))+"), contextControls[0].alphanumericResultList)");
        if(rule.matches(context)) {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.matchingResultFor(alphanumericRule, context.getControlUnderEvaluation().getId()));
        }
        else {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.nonMatchingResultFor(alphanumericRule, context.getControlUnderEvaluation().getId()));
        }
    }

    @Override
    public void visit(TwoOfThree2s twoOfThree2s) {
        if(context.getContextControls().size() < 3) throw new IllegalStateException("No enough results available"); //TODO consider using custom runtime exception

        Rule rule;

        List<Rule> aboveRules = new ArrayList<>();
        List<Rule> belowRules = new ArrayList<>();

        int resultUnderEvaluationIndex = context.getContextControls().size() - 1;
        for(int i = resultUnderEvaluationIndex; i > resultUnderEvaluationIndex - 3; i--) {
            aboveRules.add(twoOfThreeAboveRuleFor(i));
            belowRules.add(twoOfThreeBelowRuleFor(i));
        }

        Rule twoOfThreeRule = Expr.of("T(com.roche.modules.re.RuleEvaluationVisitor).twoOfThree2s("+context.getMean()+",2,3,"+context.getSd()+",T(java.util.Arrays).asList(contextControls[0], contextControls[1], contextControls[2]))");
        rule = (And.of(aboveRules).or(And.of(belowRules))).and(twoOfThreeRule);
        if(rule.matches(context)) {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.matchingResultFor(twoOfThree2s, context.getControlUnderEvaluation().getId()));
        }
        else {
            callback.accept(RuleEvaluationRespose.RuleEvalutionResult.nonMatchingResultFor(twoOfThree2s, context.getControlUnderEvaluation().getId()));
        }
    }

    private Rule twoOfThreeBelowRuleFor(int resultIndex) {
        return Expr.of("contextControls["+resultIndex+"].result <= " + context.getMean());
    }

    private Rule twoOfThreeAboveRuleFor(int resultIndex) {
        return Expr.of("contextControls["+resultIndex+"].result >= " + context.getMean());
    }


    public static boolean twoOfThree2s(double mean, int n, int of, double sd, List<QcResult> results) {
        return results.stream().limit(of).filter(result -> Math.abs(result.getResult() - mean) > (2 * sd)).count() >= n;
    }


    private Rule mmonoAscendantRuleFor(int resultIndex) {
        return Expr.of("contextControls["+resultIndex+"].result > contextControls["+(resultIndex-1)+"].result");
    }

    private Rule mmonoDescendantRuleFor(int resultIndex) {
        return Expr.of("contextControls["+resultIndex+"].result < contextControls["+(resultIndex-1)+"].result");
    }

    private Rule nxAboveRuleFor(int resultIndex) {
        return Expr.of("contextControls["+resultIndex+"].result > " + context.getMean());
    }

    private Rule nxBelowRuleFor(int resultIndex) {
        return Expr.of("contextControls["+resultIndex+"].result < " + context.getMean());
    }

    private Rule rxKsAboveRuleFor(int k, int resultIndex) {
        return Expr.of("(contextControls["+resultIndex+"].result > " +context.getMean()+ ") && (contextControls["+resultIndex+"].result - " + context.getMean() + ") >= (" + k + "*" + context.getSd() + ")");
    }

    private Rule rxKsBelowRuleFor(int k, int resultIndex) {
        return Expr.of("(contextControls["+resultIndex+"].result < " +context.getMean()+ ") && (contextControls["+resultIndex+"].result - " + context.getMean() + ") <= (" + k + "*" + context.getSd() + " * (-1) )");
    }

    public static boolean isOutsideRange(double min, double max, double result) {
        return (result >= min) && (result <= max);
    }

    public static boolean contains(List<String> reference, List<String> values) {
        return values.stream().anyMatch(reference::contains);
    }
}
