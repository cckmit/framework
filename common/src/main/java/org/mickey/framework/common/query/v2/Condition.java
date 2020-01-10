package org.mickey.framework.common.query.v2;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.query.Joint;

import javax.persistence.criteria.JoinType;
import java.io.Serializable;
import java.util.List;

/**
 * 条件
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class Condition implements Serializable {
    /**
     * 条件连接字符串 and or
     */
    private Joint joint = Joint.AND;
    /**
     * 查询主实体名称
     */
    private String entityName;
    /**
     * 实体对应的属性名称
     */
    private String propertyName;
    /**
     * 多个属性之间的连接方式
     */
    private JoinType joinType = JoinType.INNER;
    /**
     * 查询关系符号
     */
    private Operator operator = Operator.equal;
    /**
     * 条件的值
     */
    private String value;
    /**
     * 子条件
     */
    private List<Condition> subConditions;

    public Condition() {
    }

    public Condition(String propertyName, String value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    public Condition(String propertyName, Operator operator, String value) {
        this.propertyName = propertyName;
        this.operator = operator;
        this.value = value;
    }

    public boolean hasSubCondition() {
        return this.subConditions != null && this.subConditions.size() > 0;
    }
}
