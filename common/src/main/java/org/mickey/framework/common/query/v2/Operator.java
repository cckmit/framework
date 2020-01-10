package org.mickey.framework.common.query.v2;

import lombok.extern.slf4j.Slf4j;

/**
 * sql 条件常量
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public enum Operator {
    /**
     * like
     */
    beginWith("like"),
    /**
     * not like
     */
    notBeginWith("not like"),
    /**
     * like
     */
    contains("like"),
    /**
     * not like
     */
    notContains("not like"),
    /**
     * like
     */
    endWith("like"),
    /**
     * not like
     */
    notEndWith("not like"),
    /**
     * between
     */
    between("between"),
    /**
     * not between
     */
    notBetween("not between"),
    /**
     *  = ''
     */
    blank(" = ''"),
    /**
     *  != ''
     */
    notBlank(" != ''"),
    /**
     * =
     */
    equal("="),
    /**
     * !=
     */
    notEqual("!="),
    /**
     * >
     */
    greaterThan(">"),
    /**
     * >=
     */
    greaterEqual(">="),
    /**
     * <=
     */
    lessEqual("<="),
    /**
     * <
     */
    lessThan("<"),
    /**
     * is null
     */
    isNull("is null"),
    /**
     * is not null
     */
    isNotNull("is not null"),
    /**
     * in
     */
    in("in"),
    /**
     * not in
     */
    notIn("not in"),
    /**
     * 
     */
    custom("");


    private String value;

    Operator(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
