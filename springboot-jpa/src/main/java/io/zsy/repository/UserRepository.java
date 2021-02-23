package io.zsy.repository;

import io.zsy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: zhangshuaiyin
 * @date: 2021/2/2 15:38
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByNameAndEmail(String name, String email);
}
