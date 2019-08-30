package org.mickey.framework.core.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.query.v2.Condition;
import org.mickey.framework.common.query.v2.Criteria;
import org.mickey.framework.common.query.v2.Operator;
import org.mickey.framework.common.util.StringUtil;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class BaseMapperUtil {
    public static Criteria createIdCriteria(String id) {
        Criteria criteria = new Criteria();
        Condition condition = new Condition();
        if (StringUtil.isBlank(id)) {
            condition.setPropertyName("id");
            condition.setOperator(Operator.isNull);
        } else {
            condition.setPropertyName("id");
            condition.setOperator(Operator.equal);
            condition.setValue(id);
        }
        criteria.addCriterion(condition);
        return criteria;
    }
}
