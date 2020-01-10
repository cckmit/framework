package org.mickey.framework.common.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.mickey.framework.common.exception.BusinessException;
import org.mickey.framework.common.util.StringUtil;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class ZkDistributeLock {
    private final CuratorFramework zkClient;
    private final String LOCK_PATH = "/lock";

    public ZkDistributeLock(String zkAddress) {
        this.zkClient = ZkHolder.get(zkAddress);
    }

    public ZkDistributeLock(CuratorFramework zkClient) {
        this.zkClient = zkClient;
    }

    public boolean accept(String lockPath, long time, TimeUnit unit, Consumer<String> consumer) {
        InterProcessMutex lock = new InterProcessMutex(zkClient, StringUtil.formatPath(LOCK_PATH + "/" + lockPath));
        try {
            if (lock.acquire(time, unit)) {
                consumer.accept(lockPath);
                return true;
            }
            return false;
        } catch (BusinessException e) {
            throw e;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    public boolean accept(String lockPath, Consumer<String> consumer) {
        return accept(lockPath, 10, TimeUnit.SECONDS, consumer);
    }
}
