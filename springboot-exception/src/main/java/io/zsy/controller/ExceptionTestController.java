package io.zsy.controller;

import io.zsy.constant.BaseResult;
import io.zsy.constant.GlobalExceptionEnum;
import io.zsy.exception.BusinessException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangshuaiyin
 */
@RestController
@RequestMapping("/exception")
public class ExceptionTestController {

    @GetMapping("/test/business")
    public BaseResult testBusinessException() {
        String param = null;
        if (param == null) {
            throw new BusinessException(GlobalExceptionEnum.A_BUSINESS_ERROR);
        }
        return BaseResult.success(GlobalExceptionEnum.SUCCESS);
    }

    @GetMapping("/test/other")
    public BaseResult testOtherException(){
        int a = 3 / 0;
        return BaseResult.success(GlobalExceptionEnum.SUCCESS);
    }
}
