package io.zsy.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应实体
 *
 * @author zhangshuaiyin
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseResult<T> {
    private String retCode;
    private String retMsg;
    private T data;

    public BaseResult(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public static BaseResult of(String retCode, String retMsg) {
        return new BaseResult(retCode, retMsg);
    }

    public static BaseResult error(GlobalExceptionEnum e) {
        return of(e.getCode(), e.getMessage());
    }


    /**
     * 这里模拟异常，其他成功响应等省略
     */
    public static BaseResult success(GlobalExceptionEnum e) {
        return of(e.getCode(), e.getMessage());
    }
}
