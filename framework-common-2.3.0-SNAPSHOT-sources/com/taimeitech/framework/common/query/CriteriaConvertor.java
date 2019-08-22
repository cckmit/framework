package com.taimeitech.framework.common.query;

import com.taimeitech.framework.common.query.v2.Criteria;
import com.taimeitech.framework.util.SerializeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author thomason
 * @version 1.0
 * @since 2018/5/23 下午4:39
 */
public class CriteriaConvertor {
	private static Logger logger = LoggerFactory.getLogger(CriteriaConvertor.class);

	public static Criteria convertV1ToV2(com.taimeitech.framework.common.query.Criteria v1) {
		if (v1 == null) {
			return null;
		}
		Criteria v2 = new Criteria();
		v2.setSelector(v1.getSelector());
		v2.setEntityName(v1.getEntityName());
		List<SortProperty> sortProperties = v1.getSortProperties();
		if (CollectionUtils.isNotEmpty(sortProperties)) {
			sortProperties.forEach(v2::addSortProperty);
		}
		List<Condition> conditions = v1.getConditions();
		if (CollectionUtils.isNotEmpty(conditions)) {
			conditions.forEach(v1Condition -> {
				com.taimeitech.framework.common.query.v2.Condition v2Condition = convertCondition(v1Condition);
				v2.addCriterion(v2Condition);
			});
		}
		if (logger.isDebugEnabled()) {
			logger.debug("converting v1 Criteria:");
			logger.debug(SerializeUtils.toJson(v1));
			logger.debug("to v2 Criteria:");
			logger.debug(SerializeUtils.toJson(v2));
		}
		return v2;
	}

	private static com.taimeitech.framework.common.query.v2.Condition convertCondition(Condition v1Condition) {
		com.taimeitech.framework.common.query.v2.Condition v2Condition = new com.taimeitech.framework.common.query.v2.Condition();
		v2Condition.setPropertyName(v1Condition.getPropertyName());
		v2Condition.setJoint(v1Condition.getJoint());
		Operator operator = v1Condition.getOperator();
		switch (operator) {
			case LIKE:
				switch (v1Condition.getMatchMode()) {
					case ANYWHERE:
						v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.contains);
						v2Condition.setValue(v1Condition.getValue());
						break;
					case BEFORE:
						v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.beginWith);
						v2Condition.setValue(v1Condition.getValue());
						break;
					case AFTER:
						v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.endWith);
						v2Condition.setValue(v1Condition.getValue());
						break;
					case EXACT:
						//fix like exact bug
						if (StringUtils.isNotBlank(v1Condition.getValue())) {
							v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.custom);
							v2Condition.setValue(" like '" + StringEscapeUtils.escapeSql(v1Condition.getValue()) + "'");
							break;
						}
				}
				break;
			case NOT_LIKE:
				switch (v1Condition.getMatchMode()) {
					case ANYWHERE:
						v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.notContains);
						break;
					case BEFORE:
						v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.notBeginWith);
						break;
					case AFTER:
						v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.notEndWith);
						break;
					case EXACT:
						v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.notEqual);
						break;
				}
				v2Condition.setValue(v1Condition.getValue());
				break;
			case LT:
				v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.lessThan);
				v2Condition.setValue(v1Condition.getValue());
				break;
			case GT:
				v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.greaterThan);
				v2Condition.setValue(v1Condition.getValue());
				break;
			case LE:
				v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.lessEqual);
				v2Condition.setValue(v1Condition.getValue());
				break;
			case GE:
				v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.greaterEqual);
				v2Condition.setValue(v1Condition.getValue());
				break;
			case NE:
				v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.notEqual);
				v2Condition.setValue(v1Condition.getValue());
				break;
			case IN:
				v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.in);
				v2Condition.setValue(StringUtils.join(v1Condition.getValues(), ","));
				break;
			case BETWEEN:
				v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.between);
				if (v1Condition.getValue() != null && v1Condition.getValue2() != null) {
					v2Condition.setValue(v1Condition.getValue() + "," + v1Condition.getValue2());
				} else if (v1Condition.getValue() != null && v1Condition.getValue2() == null) {
					v2Condition.setValue(v1Condition.getValue());
				} else if (v1Condition.getValue() == null && v1Condition.getValue2() != null) {
					v2Condition.setValue("," + v1Condition.getValue2());
				}
				break;
			case EQ:
				v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.equal);
				v2Condition.setValue(v1Condition.getValue());
				break;
			case IS_NULL:
				v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.isNull);
				break;
			case IS_NOT_NULL:
				v2Condition.setOperator(com.taimeitech.framework.common.query.v2.Operator.isNotNull);
				break;
		}
		if (CollectionUtils.isNotEmpty(v1Condition.getSubConditions())) {
			v2Condition.setSubConditions(v1Condition.getSubConditions().stream().map(CriteriaConvertor::convertCondition).collect(Collectors.toList()));
		}
		return v2Condition;
	}
}
