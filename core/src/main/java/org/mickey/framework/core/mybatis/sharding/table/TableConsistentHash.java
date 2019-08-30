package org.mickey.framework.core.mybatis.sharding.table;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.consistenthash.ConsistentHash;
import org.mickey.framework.core.consistenthash.impl.MD5Unpack;

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
public class TableConsistentHash {

    public static Map<String, ConsistentHash<TableNode>> tableConsistentHashContainer = new ConcurrentHashMap<>();

    private static Lock lock = new ReentrantLock();

    public static ConsistentHash<TableNode> getOrCreate(String tableName, int maxCount) {
        if (tableConsistentHashContainer.containsKey(tableName)) {
            return tableConsistentHashContainer.get(tableName);
        }
        try {
            lock.lock();
            if (tableConsistentHashContainer.containsKey(tableName)) {
                return tableConsistentHashContainer.get(tableName);
            }
            ConsistentHash<TableNode> consistentHash = new ConsistentHash<>();
            consistentHash.setHashFunction(new MD5Unpack());
            consistentHash.setNumberOfReplicas(100);
            for (int i = 1; i <= maxCount; i++) {
                TableNode tableNode = new TableNode(tableName, i);
                consistentHash.add(tableNode);
            }
            tableConsistentHashContainer.put(tableName, consistentHash);
            return consistentHash;
        } finally {
            lock.unlock();
        }
    }
}
