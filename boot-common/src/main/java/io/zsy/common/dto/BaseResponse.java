package io.zsy.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.zsy.common.constants.ErrorMessage;

/**
 * 公共响应类
 *
 * @author zhangshuaiyin
 * @date 2021-05-21 16:01
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

    /**
     * 创建响应对象
     *
     * @param retCode 响应码
     * @param retMsg  响应描述
     * @return 响应对象
     */
    public static BaseResponse<?> of(String retCode, String retMsg) {
        return new BaseResponse<>(retCode, retMsg);
    }

    /**
     * 创建响应对象
     *
     * @param message 错误代码
     * @return 响应对象
     */
    public static BaseResponse<?> of(ErrorMessage message) {
        return new BaseResponse<>(message.getErrorCode(), message.getErrorMessage());
    }

}
