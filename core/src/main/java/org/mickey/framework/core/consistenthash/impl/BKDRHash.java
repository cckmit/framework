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
public class BKDRHash implements HashFunction {
    @Override
    public int hash(String str) {
        long seed = 131; // 31 131 1313 13131 131313 etc..
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash * seed) + str.charAt(i);
        }
        return (int) (hash % base);
    }

    @Override
    public int hash(Object key) {
        return hash(key.toString());
    }
}
