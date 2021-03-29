package io.zsy.constant;

/**
 * 统一异常处理响应枚举
 *
 * @author zhangshuaiyin
 */
public enum GlobalExceptionEnum {
    /**
     * 1 - A 业务异常
     * 2 - B 业务异常
     * 0 - 系统公共异常
     */
    A_BUSINESS_ERROR("1001", "A业务异常"),
    B_BUSINESS_ERROR("2001", "B业务异常"),
    SUCCESS("0000", "交易成功"),
    SYSTEM_ERROR("0999", "系统异常");

    private final String code;
    private final String message;

    GlobalExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
