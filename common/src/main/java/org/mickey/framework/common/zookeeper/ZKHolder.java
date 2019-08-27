package org.mickey.framework.common.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class ZKHolder {
    private static Lock lock = new ReentrantLock();
    private static Map<String, CuratorFramework> zkClientMap = new ConcurrentHashMap<>();

    public static CuratorFramework get(String address) {
        if (zkClientMap.get(address) != null) {
            return zkClientMap.get(address);
        }
        try {
            lock.lock();
            if (zkClientMap.get(address) != null) {
                return zkClientMap.get(address);
            }
            CuratorFramework zkClient = CuratorFrameworkFactory.newClient(address, new RetryNTimes(10, 5000));
            zkClient.start();
            zkClientMap.put(address, zkClient);
            return zkClient;
        } finally {
            lock.unlock();
        }
    }

    public static void close(String address) {
        if (zkClientMap.containsKey(address)) {
            CuratorFramework zkClient = zkClientMap.get(address);
            try {
                zkClient.close();
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
            zkClientMap.remove(address);
        }
    }
}
