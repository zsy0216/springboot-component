package io.zsy.okhttps;

import com.alibaba.fastjson.JSONObject;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.HttpUtils;
import com.ejlchina.okhttps.OkHttps;

/**
 * 参考：https://okhttps.ejlchina.com/v3/foundation.html
 *
 * @author: zhangshuaiyin
 * @date: 2021/6/14 19:15
 */
public class OkHttpsUtils {

    public static void main(String[] args) {
        // sync 同步，async 异步
        JSONObject object = OkHttps.sync("https://www.yuque.com/api/users/12568777/interperson?offset=0&type=follower")
                .get()
                .getBody()
                .toBean(JSONObject.class);
        System.out.println(object);

        HttpResult httpResult1 = HttpUtils.sync("https://www.yuque.com/api/users/12568777/interperson?offset=0&type=follower").get();


    }
}
