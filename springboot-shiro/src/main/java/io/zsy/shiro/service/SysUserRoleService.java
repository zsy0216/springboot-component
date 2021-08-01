package io.zsy.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.zsy.shiro.model.SysUserRole;

import java.util.List;

/**
 * @author zhangshuaiyin
 * @date 2021-07-30 16:36
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据 user id 查询用户角色列表
     *
     * @param userId user id
     * @return 用户角色列表
     */
    List<SysUserRole> selectUserRoles(int userId);
}
