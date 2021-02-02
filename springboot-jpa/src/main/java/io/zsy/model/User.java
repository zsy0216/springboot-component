package io.zsy.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: zhangshuaiyin
 * @date: 2021/2/2 15:26
 */
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "jpa_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25)
    private String name;

    @Column(length = 99)
    private String email;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Date createTime;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updateTime;
}
