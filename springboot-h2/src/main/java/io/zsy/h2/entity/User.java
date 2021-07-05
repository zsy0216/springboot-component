package io.zsy.h2.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangshuaiyin
 * @date 2021-07-05 09:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class User extends Model<User> {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
