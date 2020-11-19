package com.blog.service;

import com.blog.po.Type;


import java.util.List;

public interface TypeService {
    int save(Type type);

    Type getType(Long id);

    /**
     * 分页查询
     * @return
     */
    List<Type> findByPages(int pageNo,int pageSize);

    int updateType(Long id,Type type);

    Type getByName(String name);

    int deleteType(Long id);

    List<Type> listTypes();

    List<Type> listTypeTop(Integer size);
}
