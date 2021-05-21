package io.zsy.common.service.impl;

import io.zsy.common.dao.BaseRepository;
import io.zsy.common.search.Searchable;
import io.zsy.common.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Service服务基类
 *
 * @param <M>
 * @param <ID>
 * @author zhangshuaiyin
 * @date 2021-05-21 17:25
 */
public abstract class BaseServiceImpl<M, ID extends Serializable> implements IBaseService<M, ID> {

    protected BaseRepository<M, ID> baseRepository;

    @Autowired
    public void setBaseRepository(BaseRepository<M, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    /**
     * 保存单个实体
     *
     * @param m 实体
     * @return 返回保存的实体
     */
    @Override
    public M save(M m) {
        baseRepository.insertSelective(m);
        return m;
    }

    /**
     * 更新单个实体
     *
     * @param m 实体
     */
    @Override
    public int update(M m) {
        return baseRepository.updateByPrimaryKeySelective(m);
    }

    /**
     * 根据主键删除相应实体
     *
     * @param id 主键
     */
    @Override
    public int delete(ID id) {
        return baseRepository.deleteByPrimaryKey(id);
    }

    /**
     * 删除实体
     *
     * @param searchable
     */
    @Override
    public int delete(Searchable searchable) {
        return baseRepository.delete(searchable);
    }

    /**
     * 根据主键删除相应实体
     *
     * @param ids 实体
     */
    @Override
    public int delete(ID[] ids) {
        return baseRepository.deleteByIds(ids);
    }

    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    @Override
    public M findOne(ID id) {
        return baseRepository.selectByPrimaryKey(id);
    }

    /**
     * 实体是否存在
     *
     * @param id 主键
     * @return 存在 返回true，否则false
     */
    @Override
    public boolean exists(ID id) {
        return (baseRepository.exists(id) > 0);
    }

    /**
     * 统计实体总数
     *
     * @return 实体总数
     */
    @Override
    public long selectCount(Searchable searchable) {
        return baseRepository.selectCount(searchable);
    }

    /**
     * 分页查询实体
     *
     * @return
     */
    @Override
    public List<M> selectList(Searchable searchable) {
        return baseRepository.selectList(searchable);
    }

    /**
     * 根据条件筛选唯一数据记录
     *
     * @param searchable 筛选条件
     * @return 实体记录
     */
    @Override
    public M selectBySearchable(Searchable searchable) {
        return baseRepository.selectBySearchable(searchable);
    }

    /**
     * 查询所有实体
     *
     * @return 记录列表
     */
    @Override
    public List<M> findAll() {
        return baseRepository.findAll();
    }
}
