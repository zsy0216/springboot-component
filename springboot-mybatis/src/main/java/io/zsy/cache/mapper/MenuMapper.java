package io.zsy.cache.mapper;

import io.zsy.cache.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhangshuaiyin
 * @date 2021-06-24 13:51
 */
@Mapper
public interface MenuMapper {

    /**
     * 查询菜单列表
     *
     * @return List
     */
    List<Menu> selectList();
}
