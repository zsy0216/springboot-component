package io.zsy.shiro.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description：摘要
 */
public class DigestsUtil {

    public static final String SHA1 = "SHA-1";

    public static final Integer ITERATIONS = 512;

    /**
     * @param input 需要散列字符串
     * @param salt  盐字符串
     * @return
     * @Description sha1方法
     */
    public static String sha1(String input, String salt) {
        return new SimpleHash(SHA1, input, salt, ITERATIONS).toString();
    }

    /**
     * @return
     * @Description 随机获得salt字符串
     */
    public static String generateSalt() {
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        return randomNumberGenerator.nextBytes().toHex();
    }

    /**
     * @param
     * @return
     * @Description 生成密码字符密文和salt密文
     */
    public static Map<String, String> encryptPassword(String passwordPlain) {
        Map<String, String> map = new HashMap<>();
        String salt = generateSalt();
        String password = sha1(passwordPlain, salt);
        map.put("salt", salt);
        map.put("password", password);
        return map;
    }

    public static void main(String[] args) {
        // 生成密码123456的盐值和加密密文
        Map<String, String> stringStringMap = encryptPassword("123456");
        // password=ac9e5a212f96f614bd2f07e292a78bc4f144dd3e
        // salt=97b189201c85cd7994fbb5f04814f110
        System.out.println(stringStringMap);
    }
}
