# 为什么要统一异常处理

目前在项目开发中，往往采用前后端分离的方式，后端传递 json 数据给前端，前端解析 json 数据在界面展示。然而这只是一般情况，在业务处理中，往往希望在出现异常的信息时，也通过响应对象传递给前端，由前端根据不同的情况分开展示。

无论是编码中的系统异常，还是我们在业务处理中的自定义异常，都应该进行统一的处理，按照规范进行前后端交互。例如定义前后端认可的统一响应实体，针对不同种类的异常定义的不同的响应码和响应信息。

# 怎么来统一异常处理

## 1. 定义枚举

定义统一的响应信息是第一步，我们可以根据不同的业务来定义响应规范。

例如，响应码设计为 4 位，A 业务下的响应码第一位是 1，B业务下的响应码第一位是 2，公共响应码第一位是 0 等等，可以根据具体的业务需求定义合适的异常枚举。

```java
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
```



## 2. 自定义业务异常

在业务处理中遇到异常处理的场景时，我们可能需要手动自定义异常并抛出来提示操作中可能出现的错误。

```java
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
```



## 3. 统一异常拦截

通过 @ControllerAdvice 和 @ExceptionHandler 注解可以来拦截指定异常类型的异常，进而统一返回异常枚举定义的信息完成统一异常处理

```java
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
```

