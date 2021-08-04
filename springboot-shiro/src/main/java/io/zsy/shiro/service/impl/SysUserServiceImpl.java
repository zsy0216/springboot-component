package io.zsy.shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.zsy.shiro.cache.SimpleCacheService;
import io.zsy.shiro.cache.SimpleMapCache;
import io.zsy.shiro.mapper.SysPermissionMapper;
import io.zsy.shiro.mapper.SysRoleMapper;
import io.zsy.shiro.mapper.SysRolePermissionMapper;
import io.zsy.shiro.mapper.SysUserMapper;
import io.zsy.shiro.mapper.SysUserRoleMapper;
import io.zsy.shiro.model.SysPermission;
import io.zsy.shiro.model.SysRole;
import io.zsy.shiro.model.SysRolePermission;
import io.zsy.shiro.model.SysUser;
import io.zsy.shiro.model.SysUserRole;
import io.zsy.shiro.service.SysUserRoleService;
import io.zsy.shiro.service.SysUserService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Autowired
    SimpleCacheService simpleCacheService;

    @Override
    public SysUser selectByUsername(String username) {

        String key = "getUserByUsername:" + username;

        Cache<Object, Object> cache = simpleCacheService.getCache(key);
        // 缓存存在
        if (Objects.nonNull(cache)) {
            log.info("查询到缓存，从缓存获取user");
            return (SysUser) cache.get(key);
        }

        // 缓存不存在
        SysUser user = lambdaQuery().eq(SysUser::getUsername, username).one();
        if (Objects.nonNull(user)) {
            Map<Object, Object> map = new HashMap<>();
            map.put(key, user);
            simpleCacheService.createCache(key, new SimpleMapCache(key, map));
        }

        return user;
    }

    /**
     * TODO 角色、权限获取缓存
     */
    @Override
    public List<String> selectRoles(SysUser sysUser) {
        List<SysRole> roles = getRoles(sysUser);
        return roles.stream().map(SysRole::getRoleName).collect(Collectors.toList());
    }

    @Override
    public List<String> selectPermissions(SysUser sysUser) {
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
