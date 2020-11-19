package com.blog.dao;

import com.blog.po.User;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


public interface UserMapper {
    @Select("select * from t_user where username=#{username} and password=#{password}")
    User findByUsernameAndPassword(String username, String password);
}
