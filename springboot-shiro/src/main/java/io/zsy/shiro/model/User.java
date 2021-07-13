package io.zsy.shiro.model;

import java.io.Serializable;

import lombok.Data;

/**
 * shiro_user
 *
 * @author zhangshuaiyin
 */
@Data
public class User implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * 授权
     */
    private String perms;

    private static final long serialVersionUID = 1L;
}
