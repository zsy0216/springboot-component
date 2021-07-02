package io.zsy.common.constant;

import io.zsy.common.exception.BaseException;

import java.util.Arrays;

/**
 * @author zhangshuaiyin
 * @date 2021/5/31 21:58
 */
public enum ErrorMessage {
    /**
     * 成功
     */
    SUCCESS("200", "success"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR("999999", "系统异常, 请联系管理员"),
    INVALID_PARAM("A00001", "报文参数校验不通过"),
    ;

    /**
     * 错误代码
     */
    private final String code;
    /**
     * 错误信息
     */
    private final String message;

    /**
     * 构造方法
     *
     * @param code    错误代码
     * @param message 错误描述
     */
    ErrorMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 错误码映射枚举对象
     *
     * @param errorCode 错误代码
     * @return 枚举对象
     */
    public static ErrorMessage of(String errorCode) {
        return Arrays.stream(ErrorMessage.values())
                .filter(code -> code.equalsWith(errorCode))
                .findFirst()
                .orElse(null);
    }

    /**
     * 错误代码
     *
     * @param errorCode 错误代码
     * @return 是否存在错误代码
     */
    public boolean equalsWith(String errorCode) {
        return this.getCode().equals(errorCode);
    }

    /**
     * 获取错误代码
     *
     * @return 错误代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取错误描述
     *
     * @return 错误描述
     */
    public String getMessage() {
        return message;
    }

    /**
     * 获取错误异常
     *
     * @return 错误异常
     */
    public BaseException getBaseException() {
        return new BaseException(this.getCode(), this.getMessage());
    }
}
