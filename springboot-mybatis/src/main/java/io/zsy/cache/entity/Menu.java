package io.zsy.cache.entity;

import lombok.Data;

/**
 * 动态菜单信息表
 *
 * @author zhangshuaiyin
 * @since 2021-06-21
 */
@Data
public class Menu {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer parentId;

    private String path;

    private String redirect;

    private String component;

    private String name;

    private String title;

    private Integer sort;

    private String icon;

    private Boolean hidden;
}
