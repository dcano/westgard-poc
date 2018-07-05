package com.roche.modules.re;

import java.util.List;

class WestgardRuleContext {

    private final String tenantId;
    private final double mean;
    private final double sd;
    private final QcResult controlUnderEvaluation;
    private final List<QcResult> contextControls;

    WestgardRuleContext(String tenantId, double mean, double sd, QcResult controlUnderEvaluation, List<QcResult> contextControls) {
        this.tenantId = tenantId;
        this.mean = mean;
        this.sd = sd;
        this.controlUnderEvaluation = controlUnderEvaluation;
        this.contextControls = contextControls;
    }

    public String getTenantId() {
        return tenantId;
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

    public List<QcResult> getContextControls() {
        return contextControls;
    }
}
