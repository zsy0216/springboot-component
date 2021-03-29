package io.zsy.exception;

import io.zsy.constant.GlobalExceptionEnum;

/**
 * @author zhangshuaiyin
 */
public class BusinessException extends RuntimeException{
    private String code;
    private String message;

    public BusinessException() {
    }

    public BusinessException(GlobalExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
