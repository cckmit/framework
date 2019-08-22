package com.taimeitech.framework.common.datasource;

import com.taimeitech.framework.common.SystemConstant;
import com.taimeitech.framework.common.SystemContext;
import com.taimeitech.framework.common.datasource.meta.DataSourceMetaInfo;
import com.taimeitech.framework.common.datasource.meta.DbMetaInfoProvider;
import com.taimeitech.framework.common.datasource.readwriteisolate.ReadWriteContext;
import com.taimeitech.framework.common.datasource.readwriteisolate.ReadWriteStrategy;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

import java.util.stream.Collectors;

public abstract class AbstractRoutingDataSource implements RoutingDataSource, DisposableBean {
	/**
	 * 内置的数据源Map
	 */
	private final Map<String, DataSource> dataSourceMap;
	private Logger logger = LoggerFactory.getLogger(AbstractRoutingDataSource.class);
	/**
	 * 数据源配置元数据提供者
	 */
	private DbMetaInfoProvider metaInfoProvider;
	/**
	 * 路由的属性
	 */
	private String routingKey = SystemConstant.HEADER_TENANT_ID;
	/**
	 * 是否读写分离
	 */
	private boolean readWriteIsolate = true;
	private ReentrantLock lock = new ReentrantLock();

	public AbstractRoutingDataSource() {
		this.dataSourceMap = new ConcurrentHashMap<>();
	}

	/**
	 * <p>Attempts to establish a connection with the data source that
	 * this <code>DataSource</code> object represents.
	 *
	 * @return a connection to the data source
	 * @throws SQLException if a org.mickey.framework.org.mickey.framework.dbinspector.common.database access error occurs
	 */
	@Override
	public Connection getConnection() throws SQLException {
		DataSource dataSource = getDataSource();
		return dataSource.getConnection();
	}

	/**
	 * <p>Attempts to establish a connection with the data source that
	 * this <code>DataSource</code> object represents.
	 *
	 * @param username the org.mickey.framework.org.mickey.framework.dbinspector.common.database user on whose behalf the connection is
	 *                 being made
	 * @param password the user's password
	 * @return a connection to the data source
	 * @throws SQLException if a org.mickey.framework.org.mickey.framework.dbinspector.common.database access error occurs
	 * @since 1.4
	 */
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		DataSource dataSource = getDataSource();
		return dataSource.getConnection(username, password);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		DataSource dataSource = getDataSource();
		return dataSource.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		DataSource dataSource = getDataSource();
		dataSource.setLogWriter(out);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		DataSource dataSource = getDataSource();
		return dataSource.getLoginTimeout();
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		DataSource dataSource = getDataSource();
		dataSource.setLoginTimeout(seconds);
	}

	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		DataSource dataSource = getDataSource();
		return dataSource.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		DataSource dataSource = getDataSource();
		return dataSource.isWrapperFor(iface);
	}

	/**
	 * 取得数据源
	 *
	 * @return 当前线程变量中公司对应的数据源
	 * @throws SQLException 创建数据源异常或者无法找到对应公司的数据源时抛出的异常
	 */
	private DataSource getDataSource() throws SQLException {
		DataSourceMetaInfo databaseMeta = null;
		String routingKey = getRoutingValue();
		if (readWriteIsolate) {
			//获取读写上下文
			ReadWriteStrategy readWriteStrategy = getReadWriteStrategy();
			databaseMeta = metaInfoProvider.getDatabaseMeta(routingKey, readWriteStrategy);
		} else {
			databaseMeta = metaInfoProvider.getDatabaseMeta(routingKey, ReadWriteStrategy.WRITE);
		}
		if (databaseMeta == null) {
			throw new SQLException("Can't find data source");
		}
		return getDataSource(databaseMeta);
	}

	@Override
	public DataSource getDataSource(DataSourceMetaInfo databaseMeta) {
		String databaseKey = databaseMeta.getDatabaseKey();
		if (dataSourceMap.containsKey(databaseKey)) {
			return dataSourceMap.get(databaseKey);
		}
		return dataSourceMap.computeIfAbsent(databaseKey, (v) -> {
			try {
				return createTargetDatasource(databaseMeta);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		});
	}

	@Override
	public List<DataSource> getAllDataSource() {
		List<DataSourceMetaInfo> metaInfos = metaInfoProvider.getAllDataSourceMetaInfo();
		if (metaInfos != null) {
			List<DataSource> dataSourceList = new ArrayList<>();
			metaInfos.stream().collect(Collectors.toMap(DataSourceMetaInfo::getDatabaseKey, Function.identity(), (o, n) -> o)).forEach(
					(k, v) -> {
						try {
							dataSourceList.add(getDataSource(v));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			);
			return dataSourceList;
		}
		return null;
	}

	public ReadWriteStrategy getReadWriteStrategy() {
		ReadWriteStrategy readWriteStrategy = ReadWriteContext.getRwType();
		if (readWriteStrategy == null) {
			readWriteStrategy = ReadWriteStrategy.WRITE;
		}
		return readWriteStrategy;
	}

	public void setMetaInfoProvider(DbMetaInfoProvider metaInfoProvider) {
		this.metaInfoProvider = metaInfoProvider;
	}

	protected abstract DataSource createTargetDatasource(DataSourceMetaInfo databaseMeta) throws Exception;

	@Override
	public void destroy() throws Exception {
		Collection<DataSource> values = dataSourceMap.values();
		if (CollectionUtils.isNotEmpty(values)) {
			for (DataSource value : values) {
				closeDatasourceQuietly(value);
			}
		}
	}

	private void closeDatasourceQuietly(DataSource dataSource) {
		if (dataSource != null) {
			try {
//				if (dataSource instanceof ComboPooledDataSource) {
//					((ComboPooledDataSource) dataSource).close();
//				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

	/**
	 * 获取路由的值
	 *
	 * @return
	 */
	@Override
	public String getRoutingValue() {
		String routingValue = SystemContext.get(routingKey);
		if (routingValue == null) {
			throw new RuntimeException("routing value is null,can't get routing datasource");
		}
		return routingValue;
	}

	public boolean isReadWriteIsolate() {
		return readWriteIsolate;
	}

	public void setReadWriteIsolate(boolean readWriteIsolate) {
		this.readWriteIsolate = readWriteIsolate;
	}
}

