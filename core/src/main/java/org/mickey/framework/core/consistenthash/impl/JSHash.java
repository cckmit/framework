package org.mickey.framework.core.consistenthash.impl;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.consistenthash.HashFunction;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class JSHash implements HashFunction {
    @Override
    public int hash(String str) {
        long hash = 1315423911;
        for (int i = 0; i < str.length(); i++) {
            hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
        }
        return (int) (hash % base);
    }

    @Override
    public int hash(Object key) {
        return hash(key.toString());
    }
}
