package io.zsy.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.zsy.shiro.mapper.SysPermissionMapper;
import io.zsy.shiro.model.SysPermission;
import io.zsy.shiro.service.SysPermissionService;
import org.springframework.stereotype.Service;

/**
 * @author zhangshuaiyin
 * @date 2021-07-30 16:38
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {
}
