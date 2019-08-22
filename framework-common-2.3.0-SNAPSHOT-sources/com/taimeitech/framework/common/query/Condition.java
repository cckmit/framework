package com.taimeitech.framework.common.query;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomason
 * @version 1.0
 * @since 2017/6/15 17:35
 */
@Deprecated
public class Condition {
	//实体对应的属性名称
	private String propertyName;
	//条件连接符
	private Joint joint = Joint.AND;
	//查询关系符号
	private Operator operator = Operator.EQ;
	//like查询时候的匹配模式
	private MatchMode matchMode = MatchMode.ANYWHERE;
	//条件的值
	private String value;
	//输入的第二个值
	private String value2;
	//多个值
	private String[] values;
	//子条件
	private List<Condition> subConditions;

	public Condition() {
	}

	public Condition(String propertyName, Operator operator, String value) {
		this.propertyName = propertyName;
		this.operator = operator;
		this.value = value;
	}

	/**
	 * in 的构造方法
	 *
	 * @param propertyName 属性名
	 * @param values       属性值
	 */
	public Condition(String propertyName, String[] values) {
		this.propertyName = propertyName;
		this.operator = Operator.IN;
		this.values = values;
	}

	/**
	 * between的构造方法
	 *
	 * @param propertyName 属性名
	 * @param value        第一个值
	 * @param value2       第二个值
	 */
	public Condition(String propertyName, String value, String value2) {
		this.propertyName = propertyName;
		this.operator = Operator.BETWEEN;
		this.value = value;
		this.value2 = value2;
	}

	public Condition addSubCondition(Condition condition) {
		if (this.subConditions == null) {
			this.subConditions = new ArrayList<>();
		}
		this.subConditions.add(condition);
		return this;
	}

	public boolean hasSubCondition() {
		return this.subConditions != null && this.subConditions.size() > 0;
	}

	public Joint getJoint() {
		return joint;
	}

	public void setJoint(Joint joint) {
		this.joint = joint;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public MatchMode getMatchMode() {
		return matchMode;
	}

	public void setMatchMode(MatchMode matchMode) {
		this.matchMode = matchMode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public List<Condition> getSubConditions() {
		return subConditions;
	}

	public void setSubConditions(List<Condition> subConditions) {
		this.subConditions = subConditions;
	}
}
