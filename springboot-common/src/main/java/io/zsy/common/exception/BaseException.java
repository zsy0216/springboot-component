package io.zsy.common.exception;

import org.apache.commons.lang3.StringUtils;

/**
 * 公共异常类
 *
 * @author zhangshuaiyin
 * @date 2021/5/31 21:58
 */
public class BaseException extends RuntimeException {
    /**
     * 异常码
     */
    private final String exceptionCode;

    /**
     * 异常信息
     */
    private final String exceptionMessage;

    /**
     * 公共异常构造方法
     *
     * @param exceptionMessage 异常信息
     */
    public BaseException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionCode = "-1";
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * 公共异常构造方法
     *
     * @param exceptionCode    异常代码
     * @param exceptionMessage 异常信息
     */
    public BaseException(String exceptionCode, String exceptionMessage) {
        super(StringUtils.isBlank(exceptionCode) ? exceptionMessage : exceptionCode.concat(exceptionMessage));
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * 公共异常构造方法
     *
     * @param exceptionCode    异常代码
     * @param exceptionMessage 异常信息
     * @param cause            异常栈
     */
    public BaseException(String exceptionCode, String exceptionMessage, Throwable cause) {
        super(StringUtils.isBlank(exceptionCode) ? exceptionMessage : exceptionCode.concat(exceptionMessage), cause);
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * 获取异常代码
     *
     * @return 异常代码
     */
    public String getExceptionCode() {
        return exceptionCode;
    }

    /**
     * 获取异常信息
     *
     * @return 异常信息
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
