package com.taimeitech.framework.common.datasource.readwriteisolate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thomason
 * @version 1.0
 * @since 2017/4/22 22:58
 */
public class ReadWriteContext {
	private static final ThreadLocal<DatasourceStack> rw = new ThreadLocal<DatasourceStack>();
	private static Logger logger = LoggerFactory.getLogger(ReadWriteContext.class);

	/**
	 * 标记当前线程使用读数据源
	 */
	public static void markAsRead(DatasourcePropagation propagation) {
		DatasourceStack datasourceStack = rw.get();
		if (datasourceStack == null) {
			datasourceStack = new DatasourceStack(ReadWriteStrategy.READ);
		}
		if (DatasourcePropagation.REQUIRED.equals(propagation)) {
			datasourceStack.setReadWriteStrategy(datasourceStack.getReadWriteStrategy());
		} else if (DatasourcePropagation.REQUIRES_NEW.equals(propagation)) {
			datasourceStack.setReadWriteStrategy(ReadWriteStrategy.READ);
		}
		datasourceStack.push(ReadWriteStrategy.READ);
		if (logger.isDebugEnabled()) {
			logger.debug(datasourceStack.toString());
		}
		rw.set(datasourceStack);
	}

	/**
	 * 标记当前线程使用写数据源
	 */
	public static void markAsWrite(DatasourcePropagation propagation) {
		DatasourceStack datasourceStack = rw.get();
		if (datasourceStack == null) {
			datasourceStack = new DatasourceStack(ReadWriteStrategy.WRITE);
		}
		if (DatasourcePropagation.REQUIRED.equals(propagation)) {
			datasourceStack.setReadWriteStrategy(datasourceStack.getReadWriteStrategy());
		} else if (DatasourcePropagation.REQUIRES_NEW.equals(propagation)) {
			datasourceStack.setReadWriteStrategy(ReadWriteStrategy.WRITE);
		}
		datasourceStack.push(ReadWriteStrategy.WRITE);
		if (logger.isDebugEnabled()) {
			logger.debug(datasourceStack.toString());
		}
		rw.set(datasourceStack);
	}


	/**
	 * 获取当前的数据源类型
	 *
	 * @return 数据源类型
	 */
	public static ReadWriteStrategy getRwType() {
		DatasourceStack datasourceStack = rw.get();
		if (datasourceStack != null) {
			return datasourceStack.getReadWriteStrategy();
		}
		return null;
	}

	public static void clean(DatasourcePropagation propagation) {
		DatasourceStack datasourceStack = rw.get();
		if (datasourceStack == null) {
			return;
		}
		if (datasourceStack.isEmpty()) {
			rw.remove();
			return;
		}
		datasourceStack.pop();
		if (datasourceStack.isEmpty()) {
			rw.remove();
		} else {
			ReadWriteStrategy peek = datasourceStack.peek();
			if (DatasourcePropagation.REQUIRED.equals(propagation)) {
				datasourceStack.setReadWriteStrategy(datasourceStack.getInitialReadWriteStrategy());
			} else if (DatasourcePropagation.REQUIRES_NEW.equals(propagation)) {
				datasourceStack.setReadWriteStrategy(peek);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(datasourceStack.toString());
			}
			rw.set(datasourceStack);
		}
	}
}
