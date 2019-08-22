package com.taimeitech.framework.common.query;

/**
 * 关系操作符号
 *
 * @author Thomason
 * @version 1.0
 */
@Deprecated
public enum Operator {

	LIKE("like"),    // Like

	NOT_LIKE("not like"),// not like

	LT("<"),     // 小于

	GT(">"),    // 大于

	LE("<="), // 小于等于

	GE(">="),  // 大于等于

	NE("<>"),   // 不等于

	IN("in"),    // IN

	BETWEEN("between"), //between

	EQ("="),       //等于

	IS_NULL("is null"), //为空

	IS_NOT_NULL(" is not null"); //非空
	private String value;

	Operator(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
