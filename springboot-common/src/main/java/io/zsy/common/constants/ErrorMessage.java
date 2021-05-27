package io.zsy.common.constants;

import io.zsy.common.exception.BaseException;

import java.util.Arrays;

/**
 * @author zhangshuaiyin
 * @date 2021-05-21 16:42
 */
public enum ErrorMessage {
    /**
     * 系统异常
     */
    SYSTEM_ERROR("99999", "系统异常, 请联系管理员"),
    INVALID_PARAM("00000", "报文参数校验不通过"),
    ;

    /**
     * 错误代码
     */
    private final String errorCode;
    /**
     * 错误信息
     */
    private final String errorMessage;

    /**
     * 构造方法
     *
     * @param errorCode    错误代码
     * @param errorMessage 错误描述
     */
    ErrorMessage(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    /**
     * 错误码映射枚举对象
     *
     * @param errorCode 错误代码
     * @return 枚举对象
     */
    public static ErrorMessage of(String errorCode) {
        return Arrays.stream(ErrorMessage.values())
                .filter(error -> error.equalsWith(errorCode))
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
        return this.getErrorCode().equals(errorCode);
    }

    /**
     * 获取错误代码
     *
     * @return 错误代码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 获取错误描述
     *
     * @return 错误描述
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 获取错误异常
     *
     * @return 错误异常
     */
    public BaseException getBaseException() {
        return new BaseException(this.getErrorCode(), this.getErrorMessage());
    }
}
