package io.zsy.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.zsy.mybatis.plus.entity.Customer;
import io.zsy.mybatis.plus.entity.User;
import io.zsy.mybatis.plus.mapper.CustomerMapper;
import io.zsy.mybatis.plus.mapper.UserMapper;
import io.zsy.mybatis.plus.service.CustomerService;
import io.zsy.mybatis.plus.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author zhangshuaiyin
 * @date 2021-07-13 10:42
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
}
