package io.zsy.shiro.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 实现shiro缓存的序列化存储
 *
 * @author: zhangshuaiyin
 * @date: 2021/8/4 21:12
 */
@Log4j2
public class SerializableUtils {

    /**
     * 反序列化
     *
     * @param str
     * @return
     */
    public static Object deserialize(String str) {
        if (ObjectUtils.isEmpty(str)) {
            return null;
        }
        Object object = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(EncodesUtil.decodeBase64(str));
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            object = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("流读取异常：", e);
        }
        return object;
    }

    /**
     * 序列化
     *
     * @param obj
     * @return
     */
    public static String serialize(Object obj) {

        if (ObjectUtils.isEmpty(obj)) {
            return null;
        }
        String base64String = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(obj);
            base64String = EncodesUtil.encodeBase64(bos.toByteArray());
        } catch (IOException e) {
            log.error("流写入异常：", e);
        }
        return base64String;
    }
}
