package org.mickey.framework.common.query.v2;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public enum Operator {
    beginWith("like"),    // Like
    notBeginWith("not like"),// not like
    contains("like"),
    notContains("not like"),
    endWith("like"),
    notEndWith("not like"),
    between("between"),
    notBetween("not between"),
    blank(" = ''"),
    notBlank(" != ''"),
    equal("="),
    notEqual("!="),
    greaterThan(">"),
    greaterEqual(">="),
    lessEqual("<="),
    lessThan("<"),
    isNull("is null"),
    isNotNull("is not null"),
    in("in"),
    notIn("not in"),
    custom("");


    private String value;

    Operator(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
