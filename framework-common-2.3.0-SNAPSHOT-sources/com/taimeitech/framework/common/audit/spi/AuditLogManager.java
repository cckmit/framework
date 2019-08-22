package com.taimeitech.framework.common.audit.spi;

import com.taimeitech.framework.common.audit.AuditLog;
import com.taimeitech.framework.common.query.v2.Pagination;

/**
 * 审计日志持久层接口定义，实现类可以重新这个接口，以自己的业务逻辑来保存审计日志
 *
 * @author thomason
 * @version 1.0
 * @since 2018/5/3 上午8:24
 */
public interface AuditLogManager {
	/**
	 * 保存审计日志
	 *
	 * @param auditLog 审计日志对象
	 */
	void saveAuditLog(AuditLog auditLog);

	/**
	 * 查询操作日志
	 * 查询条件包装在com.taimeitech.framework.org.mickey.framework.dbinspector.common.query.v2.Pagination#criteria中
	 * com.taimeitech.framework.org.mickey.framework.dbinspector.common.query.v2.Pagination#counting 是否需要查询总行数
	 * com.taimeitech.framework.org.mickey.framework.dbinspector.common.query.v2.Pagination#paging 是否需要分页
	 * com.taimeitech.framework.org.mickey.framework.dbinspector.common.query.v2.Pagination#pageSize 每页记录数
	 * com.taimeitech.framework.org.mickey.framework.dbinspector.common.query.v2.Pagination#pageNo 页码号
	 * com.taimeitech.framework.org.mickey.framework.dbinspector.common.query.v2.Pagination#rows 查询结果
	 * com.taimeitech.framework.org.mickey.framework.dbinspector.common.query.v2.Pagination#count 总行数
	 *
	 * @param pagination 查询对象
	 * @return 结果
	 */
	Pagination<AuditLog> findAuditLog(Pagination<AuditLog> pagination);
}
