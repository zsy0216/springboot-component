package io.zsy.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.zsy.common.constant.ErrorMessage;
import io.zsy.common.exception.BaseException;
import io.swagger.annotations.ApiModelProperty;

/**
 * 公共响应类
 *
 * @author zhangshuaiyin
 * @date 2021/5/31 21:58
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    /**
     * 响应码
     */
    @ApiModelProperty(value = "响应码", required = true)
    private String retCode;

    /**
     * 响应描述
     */
    @ApiModelProperty(value = "响应描述", required = true)
    private String retMessage;

    /**
     * 响应数据体
     */
    @ApiModelProperty(value = "响应数据")
    private T data;

    /**
     * 获取响应码
     *
     * @return 响应码
     */
    public String getRetCode() {
        return retCode;
    }

    /**
     * 设置响应码
     *
     * @param retCode 响应码
     */
    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    /**
     * 获取响应描述
     *
     * @return 响应描述
     */
    public String getRetMessage() {
        return retMessage;
    }

    /**
     * 设置响应描述
     *
     * @param retMessage 响应描述
     */
    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    /**
     * 获取响应数据
     *
     * @return 响应数据
     */
    public T getData() {
        return data;
    }

    /**
     * 设置setData
     *
     * @param data 响应数据
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 无参构造方法
     */
    public BaseResponse() {
    }

    /**
     * 构造方法
     *
     * @param retCode 响应码
     * @param retMsg  响应描述
     */
    public BaseResponse(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMessage = retMsg;
    }

    public BaseResponse(String retCode, String retMessage, T data) {
        this(retCode, retMessage);
        this.data = data;
    }

    /**
     * 构造方法
     *
     * @param e 异常对象
     */
    public BaseResponse(BaseException e) {
        this(e.getExceptionCode(), e.getExceptionMessage());
    }

    /**
     * 创建响应对象
     *
     * @param retCode 响应码
     * @param retMsg  响应描述
     * @return 响应对象
     */
    public static <T> BaseResponse<T> of(String retCode, String retMsg) {
        return new BaseResponse<>(retCode, retMsg);
    }

    /**
     * 创建响应对象
     *
     * @param retCode 响应码
     * @param retMsg  响应描述
     * @param data    响应数据
     * @return 响应对象
     */
    public static <T> BaseResponse<T> of(String retCode, String retMsg, T data) {
        return new BaseResponse<>(retCode, retMsg, data);
    }

    /**
     * 创建响应对象
     *
     * @param e 异常对象
     * @return 响应对象
     */
    public static <T> BaseResponse<T> of(BaseException e) {
        return new BaseResponse<>(e);
    }

    /**
     * 创建响应对象
     *
     * @param message 响应信息
     * @return 响应对象
     */
    public static <T> BaseResponse<T> of(ErrorMessage message) {
        return of(message.getCode(), message.getMessage());
    }

    /**
     * 创建响应对象
     *
     * @param message 响应信息
     * @param data    响应对象
     * @return 响应对象
     */
    public static <T> BaseResponse<T> of(ErrorMessage message, T data) {
        return of(message.getCode(), message.getMessage(), data);
    }
}
