package org.mickey.framework.common.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * UUID工具类
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class UUIDUtils {
    private static final int IP;
    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);
    private static short counter = (short) 0;

    static {
        int ipadd;
        try {
            ipadd = toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }

    /**
     * 优化
     *
     * @return
     */
    public static String getUuid() {
        String part1 = format(getIp());
        String part2 = format(getJvm());
        String part3 = format(getHiTime());
        String part4 = format(getLoTime());
        String part5 = format(getCount());
        char[] dist = new char[32];
        part1.getChars(0, 8, dist, 0);
        part2.getChars(0, 8, dist, 8);
        part3.getChars(0, 4, dist, 16);
        part4.getChars(0, 8, dist, 20);
        part5.getChars(0, 4, dist, 28);
        return new String(dist, 0, 32).toUpperCase();
    }

    protected static String format(int intValue) {
        String formatted = Integer.toHexString(intValue);
        char[] init = "00000000".toCharArray();
        int length = formatted.length();
        formatted.getChars(0, length, init, 8 - length);
        return new String(init, 0, 8);
    }

    protected static String format(short shortValue) {
        String formatted = Integer.toHexString(shortValue);
        char[] init = "0000".toCharArray();
        int length = formatted.length();
        formatted.getChars(0, length, init, 4 - length);
        return new String(init, 0, 4);
    }

    /**
     * Unique across JVMs on this machine (unless they load this class
     * in the same quater second - very unlikely)
     */
    protected static int getJvm() {
        return JVM;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there
     * are > Short.MAX_VALUE instances created in a millisecond)
     */
    protected static short getCount() {
        synchronized (UUIDUtils.class) {
            if (counter < 0) {
                counter = 0;
            }
            return counter++;
        }
    }

    /**
     * Unique in a local network
     */
    protected static int getIp() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    protected static short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    protected static int getLoTime() {
        return (int) System.currentTimeMillis();
    }

    protected static int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }
}
