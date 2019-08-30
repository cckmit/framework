package org.mickey.framework.core.consistenthash;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface HashFunction {
    long base = Integer.MAX_VALUE;

    int hash(String str);

    int hash(Object key);
}
