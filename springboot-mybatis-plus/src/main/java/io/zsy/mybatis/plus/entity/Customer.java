package io.zsy.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhangshuaiyin
 * @date 2021-07-13 09:59
 */
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class Customer extends Model<Customer> {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String name;
    private Integer age;
    private String email;

    /**
     * ActiveRecord Model 模式下必须重写该方法，否则 xxById 方法可能失效
     * 且必须定义 *Mapper DAO 层接口
     *
     * @return 主键
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
