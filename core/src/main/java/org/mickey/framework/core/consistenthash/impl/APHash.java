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
public class APHash implements HashFunction {
    @Override
    public int hash(String str) {
        long hash = 0xAAAAAAAA;
        for (int i = 0; i < str.length(); i++) {
            if ((i & 1) == 0) {
                hash ^= ((hash << 7) ^ str.charAt(i) * (hash >> 3));
            } else {
                hash ^= (~((hash << 11) + str.charAt(i) ^ (hash >> 5)));
            }
        }
        return (int) (hash % base);
    }

    @Override
    public int hash(Object key) {
        return hash(key.toString());
    }
}

