package com.blog.service;

import com.blog.dao.UserMapper;
import com.blog.po.User;
import com.blog.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Override
    public User checkUser(String username, String password) {
        User user= userMapper.findByUsernameAndPassword(username, MD5Util.code(password)); //用MD5加密是为了避免密码明码方式在网络上传输
        return user;
    }
}
