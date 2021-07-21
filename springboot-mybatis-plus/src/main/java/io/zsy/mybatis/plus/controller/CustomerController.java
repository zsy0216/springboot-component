package io.zsy.mybatis.plus.controller;

import io.zsy.mybatis.plus.common.controller.BaseController;
import io.zsy.mybatis.plus.entity.Customer;
import io.zsy.mybatis.plus.service.CustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangshuaiyin
 * @date 2021-07-21 14:12
 */
@RestController
@RequestMapping("/customers")
public class CustomerController extends BaseController<Customer, CustomerService> {

}
