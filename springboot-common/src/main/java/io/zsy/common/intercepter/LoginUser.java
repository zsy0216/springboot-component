package io.zsy.common.intercepter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangshuaiyin
 * @date 2021-06-30 15:40
 */
@Data
@ApiModel("登陆用户")
public class LoginUser {
    private static final ThreadLocal<LoginUser> LOGIN_USER_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 放入登陆对象 绑定线程
     */
    public static void put(LoginUser loginUser) {
        LOGIN_USER_THREAD_LOCAL.set(loginUser);
    }

    /**
     * 获取登陆对象
     */
    public static LoginUser get() {
        return LOGIN_USER_THREAD_LOCAL.get();
    }

    /**
     * 移除登陆对象
     */
    public static void remove() {
        LOGIN_USER_THREAD_LOCAL.remove();
    }

    /**
     * 获取用户id
     */
    public static String userId() {
        LoginUser loginUser = get();
        return loginUser == null ? null : loginUser.getUserId();
    }

    /**
     * 获取用户登录token
     */
    public static String token() {
        LoginUser loginUser = get();
        return loginUser == null ? null : loginUser.getAccessToken();
    }

    @ApiModelProperty("userId")
    private String userId;

    @ApiModelProperty("渠道标识")
    private String channel;

    @ApiModelProperty("登录令牌")
    private String accessToken;
}
