package io.zsy.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.zsy.shiro.mapper.SysUserMapper;
import io.zsy.shiro.model.SysUser;
import io.zsy.shiro.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @author: zhangshuaiyin
 * @date: 2021/5/30 20:43
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Override
    public SysUser selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
    }
}
