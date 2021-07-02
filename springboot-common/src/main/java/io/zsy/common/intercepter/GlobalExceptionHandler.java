package io.zsy.common.intercepter;

import io.zsy.common.constant.ErrorMessage;
import io.zsy.common.dto.BaseResponse;
import io.zsy.common.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

/**
 * 拦截异常并统一处理
 * <p>
 * 异常可以有很多自定义的Exception, 异常也可以有乐观锁这些问题。
 *
 * @author zhangshuaiyin
 * @date 2021/5/31 21:58
 */
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 系统内部业务错误
     *
     * @param e 系统内部业务错误
     * @return BizResponse
     */
    @ExceptionHandler(value = BaseException.class)
    public <T> BaseResponse<T> handleException(BaseException e) {
        logger.error("已识别异常, 异常信息: {}", e.getLocalizedMessage());
        return BaseResponse.of(e);
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e 参数校验异常
     * @return BizResponse
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public <T> BaseResponse<T> handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[pathArr.length - 1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        logger.error("参数校验异常(普通传参), 异常信息:{}", message);
        return BaseResponse.of(ErrorMessage.INVALID_PARAM.getCode(), message.toString());
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return BizResponse
     */
    @ExceptionHandler(BindException.class)
    public <T> BaseResponse<T> validExceptionHandler(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        logger.info("参数校验异常(实体对象传参), 异常信息: {}", message);
        return BaseResponse.of(ErrorMessage.INVALID_PARAM.getCode(), message.toString());
    }

    /**
     * 异常错误
     *
     * @param e 异常错误
     * @return BizResponse
     */
    @ExceptionHandler(value = Exception.class)
    public <T> BaseResponse<T> handleException(Exception e) {
        logger.error("未识别异常，异常信息: " + e.getLocalizedMessage(), e);
        return BaseResponse.of(ErrorMessage.SYSTEM_ERROR);
    }
}
