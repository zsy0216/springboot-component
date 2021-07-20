package io.zsy.mongodb.repository;

import io.zsy.mongodb.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangshuaiyin
 * @date 2021-07-20 14:48
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
