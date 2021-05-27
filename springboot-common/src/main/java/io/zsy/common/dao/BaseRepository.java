package io.zsy.common.dao;

import io.zsy.common.search.Searchable;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * 抽象DAO层基类 提供一些简便方法
 * 泛型： M 表示实体类型; ID 表示主键类型
 *
 * @author zhangshuaiyin
 * @date 2021-05-21 17:03
 */
@Repository
public interface BaseRepository<M, ID extends Serializable> {
    /**
     * 保存实体数据 - 不检查字段是否为空
     *
     * @param m 实体数据
     * @return 操作行数
     */
    int insert(M m);

    /**
     * 保存实体不为空的数据
     *
     * @param m 实体数据
     * @return 操作行数
     */
    int insertSelective(M m);

    /**
     * 根据主键更新实体数据 - 不检查字段是否为空
     *
     * @param m 实体数据
     * @return 操作行数
     */
    int updateByPrimaryKey(M m);

    /**
     * 根据主键更新实体数据 - 检查字段是否为空, 不为空则更新
     *
     * @param m 实体数据
     * @return 操作行数
     */
    int updateByPrimaryKeySelective(M m);

    /**
     * 根据主键删除记录
     *
     * @param id 主键
     * @return 操作行数
     */
    int deleteByPrimaryKey(ID id);

    /**
     * 根据searchable条件删除记录
     *
     * @param searchable 筛选条件
     * @return 操作行数
     */
    int delete(Searchable searchable);

    /**
     * 根据主键数组删除记录
     *
     * @param ids 主键数组
     * @return 操作行数
     */
    int deleteByIds(ID[] ids);

    /**
     * 根据主键查询记录
     *
     * @param id 主键
     * @return 记录实体
     */
    M selectByPrimaryKey(ID id);

    /**
     * 根据searchable条件统计数量
     *
     * @param searchable 筛选条件
     * @return 统计数量
     */
    long selectCount(Searchable searchable);

    /**
     * 根据searchable条件查询记录 - 返回列表
     *
     * @param searchable 筛选条件
     * @return 记录列表
     */
    List<M> selectList(Searchable searchable);

    /**
     * 根据searchable条件查询记录
     *
     * @param searchable 筛选条件
     * @return 记录实体
     */
    M selectBySearchable(Searchable searchable);

    /**
     * 根据主键统计数据, 是否存在记录
     *
     * @param id 主键
     * @return 是否存在记录
     */
    long exists(ID id);

    /**
     * 获取所有记录
     *
     * @return 记录列表
     */
    List<M> findAll();
}
