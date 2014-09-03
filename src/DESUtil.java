import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

/**
 * DES 对称加密.<br>
 * <p/>
 * Copyright: Copyright (c) 2014/9/3 16:26
 * <p/>
 * Company: 苏州宽连十方电子商务有限公司
 * <p/>
 *
 * @author TONY(wanghq@c-platform.com)
 * @version 1.0.0
 */
public class DESUtil {
	private final static String DES = "DES";

	/**
	 *
	 * @param src 数据源
	 * @param key 密钥，长度必须是8的倍数
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		// 正式执行解密操作
		return cipher.doFinal(src);
	}

	public final static String decrypt(String data, String key) {
		try {
			return new String(decrypt(String2byte(data.getBytes()), key.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] String2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static byte[] encrypt(byte[] src, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		// 正式执行加密操作
		return cipher.doFinal(src);
	}

	/**
	 *
	 * @param password 密码
	 * @param key 加密字符串
	 * @return
	 */
	public final static String encrypt(String password, String key) {
		try {
			return byte2String(encrypt(password.getBytes(), key.getBytes()));
		} catch (Exception e) {
		}
		return null;
	}

	public static String byte2String(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static void main(String[] args) {
		String msg = "hello DES";
		// key length >= 8
		String key = "12345678";

		// encrypt
		String encryptStr = encrypt(msg, key);
		System.out.println(encryptStr);
		// descrypt
		String desencryptString = decrypt(encryptStr, key);
		System.out.println(desencryptString);
	}
}
