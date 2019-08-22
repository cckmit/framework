package com.taimeitech.framework.util;


import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @author Thomason
 * @version 1.0
 * @since 12-9-6 上午11:01
 */

public class AESUtils {
	/**
	 * 密钥算法
	 */
	private static final String KEY_ALGORITHM = "AES";

	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	/**
	 * 初始化密钥
	 *
	 * @return byte[] 密钥
	 * @throws Exception
	 */
	public static byte[] initSecretKey() {
		//返回生成指定算法的秘密密钥的 KeyGenerator 对象
		KeyGenerator kg = null;
		try {
			kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return new byte[0];
		}
		//初始化此密钥生成器，使其具有确定的密钥大小
		//AES 要求密钥长度为 128
		kg.init(128);
		//生成一个密钥
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * 转换密钥
	 *
	 * @param key 二进制密钥
	 * @return 密钥
	 */
	public static Key toKey(byte[] key) {
		//生成密钥
		return new SecretKeySpec(key, KEY_ALGORITHM);
	}

	public static Key toKey(String hexString) throws DecoderException {
		return toKey(Hex.decodeHex(hexString.toCharArray()));
	}

	/**
	 * 加密
	 *
	 * @param data 待加密数据
	 * @param key  密钥
	 * @return byte[]   加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密
	 *
	 * @param data 待加密数据
	 * @param key  二进制密钥
	 * @return byte[]   加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	public static String encryptAsHexString(String content, Key key) throws Exception {
		return Hex.encodeHexString(encrypt(content.getBytes(), key));
	}


	/**
	 * 加密
	 *
	 * @param data            待加密数据
	 * @param key             二进制密钥
	 * @param cipherAlgorithm 加密算法/工作模式/填充方式
	 * @return byte[]   加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
		//还原密钥
		Key k = toKey(key);
		return encrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 加密
	 *
	 * @param data            待加密数据
	 * @param key             密钥
	 * @param cipherAlgorithm 加密算法/工作模式/填充方式
	 * @return byte[]   加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
		//实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		//使用密钥初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		//执行操作
		return cipher.doFinal(data);
	}


	/**
	 * 解密
	 *
	 * @param data 待解密数据
	 * @param key  二进制密钥
	 * @return byte[]   解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 解密
	 *
	 * @param data 待解密数据
	 * @param key  密钥
	 * @return byte[]   解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	public static String decryptHexString(String hexString, Key key) throws Exception {
		byte[] content = decrypt(Hex.decodeHex(hexString.toCharArray()), key);
		return new String(content, "UTF-8");

	}

	/**
	 * 解密
	 *
	 * @param data            待解密数据
	 * @param key             二进制密钥
	 * @param cipherAlgorithm 加密算法/工作模式/填充方式
	 * @return byte[]   解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws Exception {
		//还原密钥
		Key k = toKey(key);
		return decrypt(data, k, cipherAlgorithm);
	}

	/**
	 * 解密
	 *
	 * @param data            待解密数据
	 * @param key             密钥
	 * @param cipherAlgorithm 加密算法/工作模式/填充方式
	 * @return byte[]   解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
		//实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		//使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		//执行操作
		return cipher.doFinal(data);
	}

	private static String showByteArray(byte[] data) {
		if (null == data) {
			return null;
		}
		StringBuilder sb = new StringBuilder("{");
		for (byte b : data) {
			sb.append(b).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * AES解密方法
	 *
	 * @param content 密文
	 * @param key     密钥
	 * @return
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws UnsupportedEncodingException
	 */
	public static String AESDecrypt(String content, String key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance("AES");
		byte[] encryptedBytes = Base64.decodeBase64(content.getBytes());
		SecretKeySpec keySpec = new SecretKeySpec(AESGetKey(key), "AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes, "UTF-8");
	}

	/**
	 * AES加密方法
	 *
	 * @param content 原文
	 * @param key     密钥
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 */
	public static String AESEncrypt(String content, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException {
		Cipher cipher = Cipher.getInstance("AES");
		SecretKeySpec keySpec = new SecretKeySpec(AESGetKey(key), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		byte[] output = cipher.doFinal(content.getBytes("UTF-8"), 0, content.length());
		return new String(Base64.encodeBase64(output));
	}

	/**
	 * 默认Java的AES最大支持128bit的密钥，
	 * 如果使用256bit的密钥，会抛出一个异常：java.security.InvalidKeyException: Illegal key size 其实Java官网上提供了解决方案，
	 * 需要下载“Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files for JDK/JRE 8”，替换JDK/JRE里的2个jar包。
	 *
	 * @param strKey
	 * @return byte[]
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] AESGetKey(String strKey) throws UnsupportedEncodingException {
		byte[] arrBTmp = strKey.getBytes("UTF-8");
		// 创建一个空的16/32位字节数组（默认值为0）
		byte[] arrB = new byte[32];
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		return arrB;
	}

//	public static void main(String[] args) throws Exception {
//		String encrypt = AESEncrypt("paSsword123!456#$%", "ITGAQ1ZC8PNVEB7S46Y2UFD95K0OLWJHASD34");
//		System.out.println(encrypt);
//		System.out.println(AESDecrypt(encrypt, "ITGAQ1ZC8PNVEB7S46Y2UFD95K0OLWJHASD34"));
//	}
}
