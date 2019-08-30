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
public class DEKHash implements HashFunction {
    @Override
    public int hash(String str) {
        long hash = str.length();
        for (int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) ^ (hash >> 27)) ^ str.charAt(i);
        }
        return (int) (hash % base);
    }

    @Override
    public int hash(Object key) {
        return hash(key.toString());
    }
}
