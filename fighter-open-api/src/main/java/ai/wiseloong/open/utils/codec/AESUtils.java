package ai.wiseloong.open.utils.codec;


import jodd.net.URLCoder;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(AESUtils.class);

	private final static String CIPHER_KEY = "AES";

	public static Cipher initEncrypt(String password) {
		Cipher cipher = null;
		try {
			SecretKeySpec key = new SecretKeySpec(
					Base64.decodeBase64(password), CIPHER_KEY);
			cipher = Cipher.getInstance(CIPHER_KEY);
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
		}
		return cipher;
	}

	public static Cipher initDecrypt(String password) {
		Cipher cipher = null;
		try {
			SecretKeySpec key = new SecretKeySpec(
					Base64.decodeBase64(password), CIPHER_KEY);
			cipher = Cipher.getInstance(CIPHER_KEY);
			cipher.init(Cipher.DECRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			logger.error(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
		}
		return cipher;
	}

	public static String encrypt(String content, String password) {
		Cipher initEncrypt = initEncrypt(password);
		try {
			byte[] byteContent = content.getBytes();
			byte[] result = initEncrypt.doFinal(byteContent);
			return parseByte2HexStr(result);
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage(), e);
		} catch (BadPaddingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static byte[] decrypt(String hexStr, String password) {
		byte[] result = null;
		Cipher initDecrypt = initDecrypt(password);
		try {
			result = initDecrypt.doFinal(parseHexStr2Byte(hexStr));
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage(), e);
		} catch (BadPaddingException e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public static String decrypt2str(String hexStr, String password) {
		byte[] result = null;
		Cipher initDecrypt = initDecrypt(password);
		try {
			result = initDecrypt.doFinal(parseHexStr2Byte(hexStr));
		} catch (IllegalBlockSizeException e) {
			logger.error(e.getMessage(), e);
		} catch (BadPaddingException e) {
			logger.error(e.getMessage(), e);
		}
		return result == null ? null : new String(result);
	}

	public static String parseByte2HexStr(byte[] buf) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = ((byte) (high * 16 + low));
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		SecretKey generateKey = KeyGenerator.getInstance("AES").generateKey();
		String string = Base64.encodeBase64String(generateKey.getEncoded());
		System.out.println(string);
		String content = "这是一段中文test!@#$%^&*()_";
		String password = string;
		System.out.println("加密前:" + content);
		String encryptResult = encrypt(content, password);
		System.out.println("加密：" + encryptResult);

		String decrypt2str = decrypt2str(encryptResult, password);
		System.out.println("解密后：" + decrypt2str);

		String de = URLCoder.encodeUri("http://oos-cn.ctyunapi.cn/sqjyypt/zf_jie-wuli/粤教版3-1 第一章电场整章课件、教案同步练习及复习与测验试题/电场单元复习资源/静电场习题(麦).doc","UTF-8");
		System.out.println(de);
	}
}