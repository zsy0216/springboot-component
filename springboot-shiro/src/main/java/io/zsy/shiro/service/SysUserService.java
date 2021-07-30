package io.zsy.shiro.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.zsy.shiro.model.SysUser;

/**
 * @author: zhangshuaiyin
 * @date: 2021/5/30 20:42
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * @param username
     * @return
     */
    SysUser selectByUsername(String username);

}
