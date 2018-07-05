package com.roche.modules.re;

import java.time.ZonedDateTime;
import java.util.Objects;

class QcResult {

    private final String id;
    private final double result;
    private final ZonedDateTime zonedDateTime;

    QcResult(String id, double result, ZonedDateTime zonedDateTime) {
        this.id = id;
        this.result = result;
        this.zonedDateTime = zonedDateTime;
    }

    String getId() {
        return id;
    }

    public double getResult() {
        return result;
    }

    ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QcResult qcResult = (QcResult) o;
        return Double.compare(qcResult.result, result) == 0 &&
                Objects.equals(id, qcResult.id) &&
                Objects.equals(zonedDateTime, qcResult.zonedDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, result, zonedDateTime);
    }
}
