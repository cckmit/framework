package com.taimeitech.framework.common.query;

import com.taimeitech.framework.common.dto.PropertySelector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Thomason
 * @version 1.0
 * @since 2017/6/15 17:26
 */
@Deprecated
public class Criteria {
	//对象名称
	private String entityName;
	//筛选条件
	private List<Condition> conditions = new ArrayList<>();
	//排序属性列
	private List<SortProperty> sortProperties;
	//属性选择器
	private PropertySelector selector;

	public Criteria addCriterion(Condition... condition) {
		if (condition != null) {
			conditions.addAll(Arrays.asList(condition));
		}
		return this;
	}

	public Criteria addSortProperty(SortProperty... sortProperty) {
		if (sortProperty == null || sortProperty.length == 0) {
			return this;
		}
		if (this.sortProperties == null) {
			this.sortProperties = new ArrayList<>();
		}
		sortProperties.addAll(Arrays.asList(sortProperty));
		return this;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public List<SortProperty> getSortProperties() {
		return sortProperties;
	}

	public PropertySelector getSelector() {
		return selector;
	}

	public void setSelector(PropertySelector selector) {
		this.selector = selector;
	}
}
