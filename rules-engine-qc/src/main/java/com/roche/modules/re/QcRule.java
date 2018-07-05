package com.roche.modules.re;

abstract class QcRule {

    abstract void accept(RuleVisitor visitor);

}
