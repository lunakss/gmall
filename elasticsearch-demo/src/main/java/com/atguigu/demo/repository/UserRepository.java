package com.atguigu.demo.repository;

import com.atguigu.demo.pojo.User;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author lunakss
 * @create 2020-07-25 16:37
 */
public interface UserRepository extends ElasticsearchRepository<User,Long> {
    List<User> findByAgeBetween(Integer age1,Integer age2);

    @Query("{\n" +
            "    \"range\": {\n" +
            "      \"age\": {\n" +
            "        \"gte\": \"?0\",\n" +
            "        \"lte\": \"?1\"\n" +
            "      }\n" +
            "    }\n" +
            "  }")
    List<User> fingUserByAge(Integer age1,Integer age2);
}
