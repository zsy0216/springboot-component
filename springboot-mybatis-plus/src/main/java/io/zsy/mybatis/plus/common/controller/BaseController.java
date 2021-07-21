package io.zsy.mybatis.plus.common.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangshuaiyin
 * @date 2021-07-21 14:07
 */
@Slf4j
public class BaseController<M extends Serializable, S extends IService<M>> {

    @Autowired
    protected S baseService;

    @GetMapping()
    public Map<String, Object> findList() {
        log.info("get list...");
        List<M> list = baseService.list();
        Map<String, Object> map = new HashMap<>();
        map.put("result", list);
        return map;
    }

    @GetMapping("/{id}")
    public Map<String, Object> findById(@PathVariable("id") Serializable id) {
        log.info("get ID : {}", id);
        M model = baseService.getById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("result", model);
        return map;
    }

    // TODO 分页查询

    @PostMapping()
    public boolean save(@RequestBody M entity) {
        log.info("save record: {}", entity);
        return baseService.save(entity);
    }

    @PutMapping()
    public boolean update(@RequestBody M entity) {
        log.info("update record : {}", entity);
        return baseService.updateById(entity);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Serializable id) {
        log.info("remove ID : {}", id);
        baseService.removeById(id);
    }
}
