package io.zsy.interceptor;

import io.zsy.constant.BaseResult;
import io.zsy.constant.GlobalExceptionEnum;
import io.zsy.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhangshuaiyin
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResult businessException(BusinessException e) {
        log.error("业务处理异常，错误码：{}, 错误信息为：{}", e.getCode(), e.getMessage());
        return BaseResult.error(GlobalExceptionEnum.A_BUSINESS_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public BaseResult otherException(Exception e) {
        log.error("业务处理异常，错误信息为：{}", e.getMessage());
        return BaseResult.error(GlobalExceptionEnum.SYSTEM_ERROR);
    }
}
