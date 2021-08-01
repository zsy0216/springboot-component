package io.zsy.shiro.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;

/**
 * @Description 封装base64和16进制编码解码工具类
 */
public class EncodesUtil {

    /**
     * @param input 输入数组
     * @return String
     * @Description HEX-byte[]--String转换
     */
    public static String encodeHex(byte[] input) {
        return Hex.encodeToString(input);
    }

    /**
     * @param input 输入字符串
     * @return byte数组
     * @Description HEX-String--byte[]转换
     */
    public static byte[] decodeHex(String input) {
        return Hex.decode(input);
    }

    /**
     * @param input 输入数组
     * @return String
     * @Description Base64-byte[]--String转换
     */
    public static String encodeBase64(byte[] input) {
        return Base64.encodeToString(input);
    }

    /**
     * @param input 输入字符串
     * @return byte数组
     * @Description Base64-String--byte[]转换
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decode(input);
    }

}
