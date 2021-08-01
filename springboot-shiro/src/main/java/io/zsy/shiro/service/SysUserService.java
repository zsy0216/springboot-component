package io.zsy.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.zsy.shiro.model.SysUser;

import java.util.List;

/**
 * @author: zhangshuaiyin
 * @date: 2021/5/30 20:42
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据登录用户账号查询用户信息
     *
     * @param username
     * @return
     */
    SysUser selectByUsername(String username);

    /**
     * 查询用户角色
     *
     * @param sysUser
     * @return
     */
    List<String> selectRoles(SysUser sysUser);

    /**
     * 查询用户权限
     *
     * @param sysUser
     * @return
     */
    List<String> selectPermissions(SysUser sysUser);
}
