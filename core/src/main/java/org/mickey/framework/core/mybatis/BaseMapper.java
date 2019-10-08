package org.mickey.framework.core.mybatis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.*;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.database.Table;
import org.mickey.framework.common.dto.PageRequest;
import org.mickey.framework.common.dto.PageResponse;
import org.mickey.framework.common.po.BasePo;
import org.mickey.framework.common.po.BaseProjectPo;
import org.mickey.framework.common.po.CommonPo;
import org.mickey.framework.common.query.v2.Condition;
import org.mickey.framework.common.query.v2.Criteria;
import org.mickey.framework.common.query.v2.Operator;
import org.mickey.framework.common.util.ReflectionUtils;
import org.mickey.framework.common.util.StringUtil;
import org.mickey.framework.core.mybatis.listener.MybatisListenerContainer;
import org.mickey.framework.core.mybatis.listener.spi.*;
import org.mickey.framework.dbinspector.common.ORMapping;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@SuppressWarnings({"unchecked", "Duplicates"})
public interface BaseMapper<T extends CommonPo> {

    @InsertProvider(type = MybatisSqlBuilder.class, method = "insert")
    int _insert(T po);

    default int insert(T po) {
        //pre insert listener 处理
        List<PreInsertListener> preInsertListeners = MybatisListenerContainer.getPreInsertListeners();
        if (CollectionUtils.isNotEmpty(preInsertListeners)) {
            for (PreInsertListener preInsertListener : preInsertListeners) {
                boolean execute = preInsertListener.preInsert(po);
                if (!execute) {
                    return 0;
                }
            }
        }
        int rows = _insert(po);
        //post insert listener 处理
        List<PostInsertListener> postInsertListeners = MybatisListenerContainer.getPostInsertListeners();
        if (CollectionUtils.isNotEmpty(postInsertListeners)) {
            postInsertListeners.forEach(postInsertListener -> postInsertListener.postInsert(po));
        }
        return rows;
    }

    /*******************************批量插入方法********************************/
    @InsertProvider(type = MybatisSqlBuilder.class, method = "batchInsert")
    int _batchInsert(@Param("list") List<T> poList, @Param("map") Map<String, Object> map);

    default int batchInsert(@Param("list") List<T> poList) {
        if (poList == null || poList.size() == 0) {
            return 0;
        }
        List<PreInsertListener> preInsertListeners = MybatisListenerContainer.getPreInsertListeners();
        //pre insert listener 处理
        if (CollectionUtils.isNotEmpty(preInsertListeners)) {
            for (PreInsertListener preInsertListener : preInsertListeners) {
                boolean result = true;
                for (T t : poList) {
                    boolean execute = preInsertListener.preInsert(t);
                    result = execute && result;
                }
                if (!result) {
                    return 0;
                }
            }
        }
        int rows = _batchInsert(poList, new HashMap<>());
        //post insert listener 处理
        List<PostInsertListener> postInsertListeners = MybatisListenerContainer.getPostInsertListeners();
        if (CollectionUtils.isNotEmpty(postInsertListeners)) {
            postInsertListeners.forEach(postInsertListener -> poList.forEach(postInsertListener::postInsert));
        }
        return rows;
    }

    /*******************************全部更新方法********************************/
    @UpdateProvider(type = MybatisSqlBuilder.class, method = "update")
    int _update(@Param("po") T po, @Param("columns") Set<String> columns);

    default int update(T po, String... columns) {
        List<PreUpdateListener> preUpdateListeners = MybatisListenerContainer.getPreUpdateListeners();
        //pre update listener 处理
        if (CollectionUtils.isNotEmpty(preUpdateListeners)) {
            for (PreUpdateListener preInsertListener : preUpdateListeners) {
                boolean execute = preInsertListener.preUpdate(po);
                if (!execute) {
                    return 0;
                }
            }
        }
        Set<String> includeColumns = null;
        if (columns != null && columns.length > 0) {
            includeColumns = new HashSet<>(Arrays.asList(columns));
            includeColumns.add("updateTime");
            includeColumns.add("updateBy");
        }
        int rows = _update(po, includeColumns);

        //post update listener 处理
        List<PostUpdateListener> postUpdateListeners = MybatisListenerContainer.getPostUpdateListeners();
        if (CollectionUtils.isNotEmpty(postUpdateListeners)) {
            postUpdateListeners.forEach(postUpdateListener -> postUpdateListener.postUpdate(po));
        }
        return rows;
    }

    /*******************************选择更新方法********************************/
    @UpdateProvider(type = MybatisSqlBuilder.class, method = "updateSelective")
    int _updateSelective(@Param("po") T po, @Param("versionable") boolean versionable, @Param("columns") Set<String> includeColumns);

    default int updateSelective(T po, String... columns) {
        List<PreUpdateListener> preUpdateListeners = MybatisListenerContainer.getPreUpdateListeners();
        //pre update listener 处理
        if (CollectionUtils.isNotEmpty(preUpdateListeners)) {
            for (PreUpdateListener preInsertListener : preUpdateListeners) {
                boolean execute = preInsertListener.preUpdate(po);
                if (!execute) {
                    return 0;
                }
            }
        }
        Set<String> includeColumns = null;
        if (columns != null && columns.length > 0) {
            includeColumns = new HashSet<>(Arrays.asList(columns));
        }
        int rows = _updateSelective(po, false, includeColumns);

        //post update listener 处理
        List<PostUpdateListener> postUpdateListeners = MybatisListenerContainer.getPostUpdateListeners();
        if (CollectionUtils.isNotEmpty(postUpdateListeners)) {
            postUpdateListeners.forEach(postUpdateListener -> postUpdateListener.postUpdate(po));
        }
        return rows;
    }

    default int updateVersionable(T po, String... columns) {
        List<PreUpdateListener> preUpdateListeners = MybatisListenerContainer.getPreUpdateListeners();
        //pre update listener 处理
        if (CollectionUtils.isNotEmpty(preUpdateListeners)) {
            for (PreUpdateListener preInsertListener : preUpdateListeners) {
                boolean execute = preInsertListener.preUpdate(po);
                if (!execute) {
                    return 0;
                }
            }
        }
        Set<String> includeColumns = null;
        if (columns != null && columns.length > 0) {
            includeColumns = new HashSet<>(Arrays.asList(columns));
        }
        int rows = _updateSelective(po, true, includeColumns);

        //post update listener 处理
        List<PostUpdateListener> postUpdateListeners = MybatisListenerContainer.getPostUpdateListeners();
        if (CollectionUtils.isNotEmpty(postUpdateListeners)) {
            postUpdateListeners.forEach(postUpdateListener -> postUpdateListener.postUpdate(po));
        }
        return rows;
    }

    /*******************************批量选择更新方法********************************/
    @UpdateProvider(type = MybatisSqlBuilder.class, method = "batchUpdateSelective")
    int _batchUpdateSelective(@Param("clazz") Class<T> clazz, @Param("list") List<T> poList, @Param("includeColumns") Set<String> includeColumns);

    default int batchUpdateSelective(List<T> poList, String... includeColumns) {
        if (poList == null || poList.size() == 0) {
            return 0;
        }
        List<PreUpdateListener> preUpdateListeners = MybatisListenerContainer.getPreUpdateListeners();
        //pre update listener 处理
        if (CollectionUtils.isNotEmpty(preUpdateListeners)) {
            for (PreUpdateListener preInsertListener : preUpdateListeners) {
                boolean result = true;
                for (T t : poList) {
                    boolean execute = preInsertListener.preUpdate(t);
                    result = result && execute;
                }
                if (!result) {
                    return 0;
                }
            }
        }

        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getInterfaces()[0].getGenericInterfaces()[0]).getActualTypeArguments()[0];
        Set<String> includeColumnSet = new HashSet<>();
        if (includeColumns != null) {
            includeColumnSet.addAll(Arrays.asList(includeColumns));
        }
        int rows = _batchUpdateSelective(entityClass, poList, includeColumnSet);

        //post insert listener 处理
        List<PostUpdateListener> postUpdateListeners = MybatisListenerContainer.getPostUpdateListeners();
        if (CollectionUtils.isNotEmpty(postUpdateListeners)) {
            postUpdateListeners.forEach(postUpdateListener -> poList.forEach(postUpdateListener::postUpdate));
        }
        return rows;
    }


    @UpdateProvider(type = MybatisSqlBuilder.class, method = "batchUpdate")
    int _batchUpdate(@Param("clazz") Class<T> clazz, @Param("list") List<T> poList, @Param("includeColumns") Set<String> includeColumns);

    default int batchUpdate(List<T> poList, String... columns) {
        if (poList == null || poList.size() == 0) {
            return 0;
        }
        List<PreUpdateListener> preUpdateListeners = MybatisListenerContainer.getPreUpdateListeners();
        //pre update listener 处理
        if (CollectionUtils.isNotEmpty(preUpdateListeners)) {
            for (PreUpdateListener preInsertListener : preUpdateListeners) {
                boolean result = true;
                for (T t : poList) {
                    boolean execute = preInsertListener.preUpdate(t);
                    result = result && execute;
                }
                if (!result) {
                    return 0;
                }
            }
        }

        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getInterfaces()[0].getGenericInterfaces()[0]).getActualTypeArguments()[0];
        Set<String> includeColumns = new HashSet<>();
        if (columns != null && columns.length > 0) {
            includeColumns = new HashSet<>(Arrays.asList(columns));
            includeColumns.add("updateTime");
            includeColumns.add("updateBy");
        }
        int rows = _batchUpdate(entityClass, poList, includeColumns);

        //post insert listener 处理
        List<PostUpdateListener> postUpdateListeners = MybatisListenerContainer.getPostUpdateListeners();
        if (CollectionUtils.isNotEmpty(postUpdateListeners)) {
            postUpdateListeners.forEach(postUpdateListener -> poList.forEach(postUpdateListener::postUpdate));
        }
        return rows;
    }

    /*******************************删除方法********************************/
    @DeleteProvider(type = MybatisSqlBuilder.class, method = "delete")
    int _delete(@Param("map") Map<String, Object> map, @Param("clazz") Class<T> clazz);

    default int delete(String id) {
        T po = null;
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getInterfaces()[0].getGenericInterfaces()[0]).getActualTypeArguments()[0];
        //pre postDelete listener 处理
        List<PreDeleteListener> preDeleteListeners = MybatisListenerContainer.getPreDeleteListeners();
        if (CollectionUtils.isNotEmpty(preDeleteListeners)) {
            po = get(id);
            for (PreDeleteListener preInsertListener : preDeleteListeners) {
                boolean execute = preInsertListener.preDelete(po);
                if (!execute) {
                    return 0;
                }
            }
        }
        Criteria idCriteria = BaseMapperUtil.createIdCriteria(id);
        int rows = deleteByCondition(idCriteria);

        //post postDelete listener 处理
        List<PostDeleteListener> postDeleteListeners = MybatisListenerContainer.getPostDeleteListeners();
        if (CollectionUtils.isNotEmpty(postDeleteListeners)) {
            if (po == null) {
                po = getById(id);
            }
            for (PostDeleteListener postDeleteListener : postDeleteListeners) {
                postDeleteListener.postDelete(po);
            }
        }
        return rows;
    }

    default int deleteByCriteria(Criteria criteria) {
        int rows = deleteByCondition(criteria);
        return rows;
    }

    /*******************************按照查询条件删除********************************/
    @DeleteProvider(type = MybatisSqlBuilder.class, method = "deleteByCriteria")
    int _deleteByCondition(@Param("clazz") Class<T> clazz, @Param("criteria") Criteria criteria, @Param("map") Map<String, Object> map);

    default int deleteByCondition(Criteria criteria) {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getInterfaces()[0].getGenericInterfaces()[0]).getActualTypeArguments()[0];
        if (ReflectionUtils.isSubClass(entityClass, BasePo.class)) {
            criteria.and("tenantId", Operator.equal, SystemContext.getTenantId());
        }
        if (ReflectionUtils.isSubClass(entityClass, BaseProjectPo.class)) {
            criteria.and("projectId", Operator.equal, SystemContext.getProjectId());
        }
        criteria.and("isDeleted", Operator.equal, "0");
        Map<String, Object> map = new HashMap<>();
        map.put("updateTime", new Date());
        map.put("updateBy", SystemContext.getUserId());
        int rows = _deleteByCondition(entityClass, criteria, map);
        return rows;
    }

    /*******************************按照主键查询********************************/
    @SelectProvider(type = MybatisSqlBuilder.class, method = "get")
    T _get(@Param("id") String id, @Param("clazz") Class<T> clazz);

    default T get(String id) {
        Criteria criteria = BaseMapperUtil.createIdCriteria(id);
        List<T> ts = findByCriteria(criteria);
        if (ts != null && ts.size() > 0) {
            Assert.isTrue(ts.size() == 1, "按id查出多个记录");
            return ts.get(0);
        }
        return null;
    }

    // all
    default T getById(String id) {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getInterfaces()[0].getGenericInterfaces()[0]).getActualTypeArguments()[0];
        Criteria criteria = BaseMapperUtil.createIdCriteria(id);
        List<T> ts = _findByCriteria(entityClass, criteria, new HashMap<>());
        if (ts.size() > 0) {
            Assert.isTrue(ts.size() == 1, "按id查出多个记录");
            return ts.get(0);
        }
        return null;
    }

    @SelectProvider(type = MybatisSqlBuilder.class, method = "shardingGetList")
    List<T> _shardingGetList(@Param("idList") List<String> idList, @Param("clazz") Class<T> clazz);

    //isdelted == 0
    default List<T> getList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return new ArrayList<>();
        }
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getInterfaces()[0].getGenericInterfaces()[0]).getActualTypeArguments()[0];
        Table table = ORMapping.get(entityClass);
        if (table.isSharding()) {
            return _shardingGetList(idList, entityClass);
        }
        Criteria criteria = new Criteria();
        Condition condition = new Condition();
        condition.setPropertyName("id");
        condition.setOperator(Operator.in);
        condition.setValue(String.join(",", idList));
        criteria.addCriterion(condition);
        List<T> list = findByCriteria(criteria);
        return list;
    }

    @SelectProvider(type = MybatisSqlBuilder.class, method = "shardingGetByIdList")
    List<T> _shardingByIdList(@Param("idList") List<String> idList, @Param("clazz") Class<T> clazz);


    default List<T> getByIdList(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return new ArrayList<>();
        }
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getInterfaces()[0].getGenericInterfaces()[0]).getActualTypeArguments()[0];
        Table table = ORMapping.get(entityClass);
        if (table.isSharding()) {
            return _shardingByIdList(idList, entityClass);
        }
        Criteria criteria = new Criteria();
        Condition condition = new Condition();
        condition.setPropertyName("id");
        condition.setOperator(Operator.in);
        condition.setValue(String.join(",", idList));
        criteria.addCriterion(condition);
        return _findByCriteria(entityClass, criteria, new HashMap<>());
    }


    /*******************************按照某个属性查询********************************/
//	@SelectProvider(type = MybatisSqlBuilder.class, method = "findByProperty")
//	List<T> _findByProperty(@Param("clazz") Class<T> clazz, @Param("propName") String propName, @Param("propValue") Object propValue);

    @SelectProvider(type = MybatisSqlBuilder.class, method = "findByCriteria")
    List<T> _findByCriteria(@Param("clazz") Class<T> clazz, @Param("criteria") Criteria criteria, @Param("map") Map<String, Object> map);

    default List<T> findByCriteria(Criteria criteria) {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getInterfaces()[0].getGenericInterfaces()[0]).getActualTypeArguments()[0];
        if (ReflectionUtils.isSubClass(entityClass, BasePo.class)) {
            criteria.and("tenantId", Operator.equal, SystemContext.getTenantId());
        }
        if (ReflectionUtils.isSubClass(entityClass, BaseProjectPo.class)) {
            criteria.and("projectId", Operator.equal, SystemContext.getProjectId());
        }
        criteria.and("isDeleted", Operator.equal, "0");
        return _findByCriteria(entityClass, criteria, new HashMap<>());
    }

    /**
     * 根据查询条件查询单个记录
     *
     * @param criteria 查询条件封装类
     * @return 记录
     */
    default T findOne(Criteria criteria) {
        List<T> list = findByCriteria(criteria);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 分页查询对象
     *
     * @param request 查询条件
     * @return 分页查询结果
     */
    default PageInfo<T> findPage(PageRequest request) {
        if (request.isNeedPaging()) {
            PageHelper.startPage(request.getPageNo(), request.getPageSize(), request.isNeedCount());
        }
        List<T> list = this.findByCriteria(request.getCriteria());
        PageInfo<T> pageInfo = new PageInfo<>(list);
        PageInfo<T> response = new PageInfo<>();
        response.setTotal(pageInfo.getTotal());
        return response;
    }

    default List<T> findByProperty(String propName, Object propValue) {
        Criteria criteria = new Criteria();
        criteria.and(propName, Operator.equal, String.valueOf(propValue));
        return findByCriteria(criteria);
    }

    default T findOne(String propName, Object propValue) {
        List<T> list = findByProperty(propName, propValue);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    default List<T> findAll() {
        Criteria criteria = new Criteria();
        return findByCriteria(criteria);
    }

    default List<T> findByCondition(Criteria criteria) {
        return findByCriteria(criteria);
    }

    default Long countByCondition(Criteria criteria) {
        return countByCriteria(criteria);
    }

    default List<T> findByConditionBase(Criteria criteria) {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getInterfaces()[0].getGenericInterfaces()[0]).getActualTypeArguments()[0];
        return _findByCriteria(entityClass, criteria, new HashMap<>());
    }

    @SelectProvider(type = MybatisSqlBuilder.class, method = "countByCondition")
    Long _countByCriteria(@Param("clazz") Class<T> clazz, @Param("criteria") Criteria criteria, @Param("map") Map<String, Object> map);

    default Long countByCriteria(Criteria criteria) {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getInterfaces()[0].getGenericInterfaces()[0]).getActualTypeArguments()[0];
        if (ReflectionUtils.isSubClass(entityClass, BasePo.class)) {
            criteria.and("tenantId", Operator.equal, SystemContext.getTenantId());
        }
        if (ReflectionUtils.isSubClass(entityClass, BaseProjectPo.class)) {
            criteria.and("projectId", Operator.equal, SystemContext.getProjectId());
        }
        criteria.and("isDeleted", Operator.equal, "0");
        return _countByCriteria(entityClass, criteria, new HashMap<>());
    }

//	/********************************检查单个属性是否重复****************************/
//	@SelectProvider(type = MybatisSqlBuilder.class, method = "checkRepeat")
//	boolean _checkRepeat(@Param("clazz") Class<T> clazz, @Param("id") String id, @Param("propName") String propName, @Param("propValue") Object propValue);

    default boolean checkRepeat(String id, String propName, Object propValue) {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getInterfaces()[0].getGenericInterfaces()[0]).getActualTypeArguments()[0];
        Criteria criteria = new Criteria();
        if (StringUtil.isNotBlank(id)) {
            criteria.and("id", Operator.notEqual, id);
        }
        criteria.and(propName, Operator.equal, String.valueOf(propValue));
        Long aLong = countByCriteria(criteria);
        return aLong > 0;
    }

}
