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
public class ELFHash implements HashFunction {
    @Override
    public int hash(String str) {
        long hash = 0;
        long x = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + str.charAt(i);
            if ((x = hash & 0xF0000000L) != 0) {
                hash ^= (x >> 24);
            }
            hash &= ~x;
        }
        return (int) (hash % base);
    }

    @Override
    public int hash(Object key) {
        return hash(key.toString());
    }
}
