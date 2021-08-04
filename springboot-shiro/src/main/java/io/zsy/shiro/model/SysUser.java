package io.zsy.shiro.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * sys_user
 *
 * @author zhangshuaiyin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUser extends Model<SysUser> implements Serializable {
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    private static final long serialVersionUID = 1L;
}
