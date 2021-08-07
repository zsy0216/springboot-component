package io.zsy.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.zsy.shiro.mapper.SysPermissionMapper;
import io.zsy.shiro.mapper.SysRoleMapper;
import io.zsy.shiro.mapper.SysRolePermissionMapper;
import io.zsy.shiro.mapper.SysUserMapper;
import io.zsy.shiro.model.SysPermission;
import io.zsy.shiro.model.SysRole;
import io.zsy.shiro.model.SysRolePermission;
import io.zsy.shiro.model.SysUser;
import io.zsy.shiro.model.SysUserRole;
import io.zsy.shiro.service.SysUserRoleService;
import io.zsy.shiro.service.SysUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zhangshuaiyin
 * @date: 2021/5/30 20:43
 */
@Log4j2
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysUserRoleService userRoleService;
    @Autowired
    SysRoleMapper roleMapper;
    @Autowired
    SysRolePermissionMapper rolePermissionMapper;
    @Autowired
    SysPermissionMapper permissionMapper;

    @Override
    public SysUser selectByUsername(String username) {
        log.info("selectByUsername - 查询数据库");
        return lambdaQuery().eq(SysUser::getUsername, username).one();
    }

    @Override
    public List<String> selectRoles(SysUser sysUser) {
        log.info("selectRoles - 查询数据库");
        List<SysRole> roles = getRoles(sysUser);
        return roles.stream().map(SysRole::getRoleName).collect(Collectors.toList());
    }

    @Override
    public List<String> selectPermissions(SysUser sysUser) {
        log.info("selectPermissions - 查询数据库");
        List<SysRole> roles = getRoles(sysUser);
        List<SysRolePermission> rolePermissions = rolePermissionMapper.selectList(
                new QueryWrapper<SysRolePermission>().in("role_id", roles.stream().map(SysRole::getRoleId).collect(Collectors.toList())));
        List<Integer> idList = rolePermissions.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
        List<SysPermission> permissions = permissionMapper.selectList(
                new QueryWrapper<SysPermission>().in("permission_id", idList)
        );
        return permissions.stream().map(SysPermission::getPermissionName).collect(Collectors.toList());
    }

    private List<SysRole> getRoles(SysUser sysUser) {
        List<SysUserRole> userRoles = userRoleService.selectUserRoles(sysUser.getUserId());
        List<Integer> idList = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        return roleMapper.selectList(new QueryWrapper<SysRole>().in("role_id", idList));
    }

    @Override
    public void cacheUser(SysUser sysUser) {

    }
}
