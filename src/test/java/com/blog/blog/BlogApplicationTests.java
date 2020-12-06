package com.blog.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    public void contextLoads() {
        com.blog.vo.Test t=new com.blog.vo.Test();
        t.setId(1);
        t.setName("狗哥");
        t.setAddress("深圳市南山区");
        List<String> list=new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        redisTemplate.opsForValue().set("aaa",t);
        Object o = redisTemplate.opsForValue().get("aaa");
        System.out.println(o.toString());

    }
    @Test
    public void testRedis(){
        System.out.println(redisTemplate.opsForHash().entries("comment"));
    }
}
