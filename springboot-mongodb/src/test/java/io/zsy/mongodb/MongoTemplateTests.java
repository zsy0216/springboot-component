package io.zsy.mongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import io.zsy.mongodb.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author zhangshuaiyin
 * @date 2021-07-20 14:41
 */
@SpringBootTest
public class MongoTemplateTests {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加
     */
    @Test
    public void createUser() {
        User user = new User();
        user.setAge(20);
        user.setName("test");
        user.setEmail("4932200@qq.com");
        User user1 = mongoTemplate.insert(user);
        System.out.println(user1);
    }

    /**
     * 查询所有
     */
    @Test
    public void findUser() {
        List<User> userList = mongoTemplate.findAll(User.class);
        System.out.println(userList);
    }

    /**
     * 根据id查询
     */
    @Test
    public void getById() {
        User user = mongoTemplate.findById("5ffbfa2ac290f356edf9b5aa", User.class);
        System.out.println(user);
    }

    /**
     * 条件查询
     */
    @Test
    public void findUserList() {
        Query query = new Query(Criteria.where("name").is("test").and("age").is(20));

        List<User> userList = mongoTemplate.find(query, User.class);
        System.out.println(userList);
    }

    /**
     * 模糊查询
     */
    @Test
    public void findUsersLikeName() {
        String name = "est";
        String regex = String.format("%s%s%s", "^.*", name, ".*$");

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<User> userList = mongoTemplate.find(query, User.class);
        System.out.println(userList);
    }

    /**
     * 分页查询
     */
    @Test
    public void findUsersPage() {
        String name = "est";
        int pageNo = 2;
        int pageSize = 10;

        Query query = new Query();
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("name").regex(pattern));
        int totalCount = (int) mongoTemplate.count(query, User.class);
        List<User> userList = mongoTemplate.find(query.skip((pageNo - 1) * pageSize).limit(pageSize), User.class);

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("list", userList);
        pageMap.put("totalCount", totalCount);
        System.out.println(pageMap);
    }

    /**
     * 修改
     */
    @Test
    public void updateUser() {
        User user = mongoTemplate.findById("5ffbfa2ac290f356edf9b5aa", User.class);
        Assertions.assertNotNull(user);
        user.setName("test_1");
        user.setAge(25);
        user.setEmail("493220990@qq.com");

        Query query = new Query(Criteria.where("_id").is(user.getId()));
        Update update = new Update();
        update.set("name", user.getName());
        update.set("age", user.getAge());
        update.set("email", user.getEmail());
        UpdateResult result = mongoTemplate.upsert(query, update, User.class);
        long count = result.getModifiedCount();
        System.out.println(count);
    }

    /**
     * 删除操作
     */
    @Test
    public void delete() {
        Query query = new Query(Criteria.where("_id").is("5ffbfa2ac290f356edf9b5aa"));
        DeleteResult result = mongoTemplate.remove(query, User.class);
        long count = result.getDeletedCount();
        System.out.println(count);
    }
}
