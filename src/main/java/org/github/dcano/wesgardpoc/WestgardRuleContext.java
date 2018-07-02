package org.github.dcano.wesgardpoc;

import java.util.List;

class WestgardRuleContext {

    private final double mean;
    private final double sd;
    private final QcResult controlUnderEvaluation;
    private final List<QcResult> contextControls;

    WestgardRuleContext(double mean, double sd, QcResult controlUnderEvaluation, List<QcResult> contextControls) {
        this.mean = mean;
        this.sd = sd;
        this.controlUnderEvaluation = controlUnderEvaluation;
        this.contextControls = contextControls;
    }

    double getMean() {
        return mean;
    }

    double getSd() {
        return sd;
    }

    QcResult getControlUnderEvaluation() {
        return controlUnderEvaluation;
    }

    List<QcResult> getContextControls() {
        return contextControls;
    }
}
