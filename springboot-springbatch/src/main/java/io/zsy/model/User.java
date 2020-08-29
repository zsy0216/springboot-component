package io.zsy.model;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author Ep流苏
 * @since 2020-08-29 13:59:34
 */
public class User implements Serializable {
    private static final long serialVersionUID = -43483687962598237L;
    
    private Integer id;
    
    private String username;
    
    private String sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}