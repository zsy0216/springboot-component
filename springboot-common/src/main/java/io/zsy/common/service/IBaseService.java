package io.zsy.common.service;

import io.zsy.common.search.Searchable;

import java.util.List;

/**
 * 公共服务接口
 *
 * @author zhangshuaiyin
 * @date 2021-05-21 17:25
 */
public interface IBaseService<M, ID> {
    /**
     * 保存单个实体
     *
     * @param m 实体
     * @return 返回保存的实体
     */
    M save(M m);

    /**
     * 更新单个实体
     *
     * @param m 实体
     */
    int update(M m);

    /**
     * 根据主键删除相应实体
     *
     * @param id 主键
     */
    int delete(ID id);

    /**
     * 删除实体
     *
     * @param searchable
     */
    int delete(Searchable searchable);

    /**
     * 根据主键删除相应实体
     *
     * @param ids 实体
     */
    int delete(ID[] ids);

    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    M findOne(ID id);

    /**
     * 实体是否存在
     *
     * @param id 主键
     * @return 存在 返回true，否则false
     */
    boolean exists(ID id);

    /**
     * 统计实体总数
     *
     * @return 实体总数
     */
    long selectCount(Searchable searchable);

    /**
     * 分页查询实体
     *
     * @return
     */
    List<M> selectList(Searchable searchable);

    /**
     * 根据条件筛选唯一数据记录
     *
     * @param searchable 筛选条件
     * @return 实体记录
     */
    M selectBySearchable(Searchable searchable);

    /**
     * 查询所有实体
     *
     * @return 记录列表
     */
    List<M> findAll();
}
