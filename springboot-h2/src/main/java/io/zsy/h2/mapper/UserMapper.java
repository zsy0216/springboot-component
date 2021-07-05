package io.zsy.h2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.zsy.h2.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhangshuaiyin
 * @date 2021-07-05 09:26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
