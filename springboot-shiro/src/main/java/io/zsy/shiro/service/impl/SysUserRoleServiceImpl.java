package io.zsy.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.zsy.shiro.mapper.SysUserRoleMapper;
import io.zsy.shiro.model.SysUserRole;
import io.zsy.shiro.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangshuaiyin
 * @date 2021-07-30 16:38
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
    @Override
    public List<SysUserRole> selectUserRoles(int userId) {
        return lambdaQuery().eq(SysUserRole::getUserId, userId).list();
    }
}
