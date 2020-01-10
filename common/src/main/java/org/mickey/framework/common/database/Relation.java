package org.mickey.framework.common.database;

/**
 * 各表之间关联关系
 *
 * @author mickey
 * 05/07/2019
 */
public enum Relation {
    
	/**
	 * 一对一
	 */
    OneToOne, 
    /**
     * 一对多
     */
    OneToMany, 
    /**
     * 多对一
     */
    ManyToOne;
}
