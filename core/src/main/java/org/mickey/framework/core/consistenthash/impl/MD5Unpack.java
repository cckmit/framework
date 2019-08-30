package org.mickey.framework.core.consistenthash.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.mickey.framework.core.consistenthash.HashFunction;


import java.nio.ByteBuffer;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class MD5Unpack implements HashFunction {
    @Override
    public int hash(String str) {
        byte[] bytes = DigestUtils.md5(str);
        int anInt = ByteBuffer.wrap(bytes).getInt(0);
        return anInt;
    }

    @Override
    public int hash(Object key) {
        return hash(key.toString());
    }
}
