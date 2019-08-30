package org.mickey.framework.core.consistenthash;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class ConsistentHash<T extends ConsistentHashNode> {
    protected final SortedMap<Integer, T> circle = new TreeMap<>();
    protected HashFunction hashFunction;
    protected int numberOfReplicas = 1;

    public ConsistentHash() {
    }

    public ConsistentHash(HashFunction hashFunction, int numberOfReplicas,
                          Collection<T> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;

        for (T node : nodes) {
            add(node);
        }
    }

    public void add(T node) {
        for (int i = 1; i <= numberOfReplicas; i++) {
            int hasCode = replicasHashCode(node, i);
            log.info("add Node:" + node.hashString() + " with hashCode:" + hasCode);
            circle.put(hasCode, node);
        }
    }

    public void remove(ConsistentHashNode node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            int hasCode = replicasHashCode(node, i);
            circle.remove(hasCode);
        }
    }

    public boolean contains(ConsistentHashNode node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            int hasCode = replicasHashCode(node, i);
            if (circle.containsKey(hasCode)) {
                return true;
            }
        }
        return false;
    }

    public T get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
//		int hash = hashFunction.hash(DigestUtils.md5Hex(key.toString()));
        int hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    private int replicasHashCode(ConsistentHashNode node, int replaceNo) {
        return hashFunction.hash(node.hashString() + "-" + replaceNo);
    }

    public Collection<T> getAll() {
        return circle.values();
    }

    public void clear() {
        circle.clear();
    }
}
