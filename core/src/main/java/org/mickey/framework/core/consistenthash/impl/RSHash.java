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
public class RSHash implements HashFunction {
    @Override
    public int hash(String str) {
        int b = 378551;
        int a = 63689;
        long hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = hash * a + str.charAt(i);
            a = a * b;
        }
        return (int) (hash % base);
    }

    @Override
    public int hash(Object key) {
        return hash(key.toString());
    }
}
