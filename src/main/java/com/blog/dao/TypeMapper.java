package com.blog.dao;

import com.blog.po.Type;
import org.apache.ibatis.annotations.*;

import org.springframework.stereotype.Repository;

import java.util.List;


public interface TypeMapper{
    @Insert("insert into t_type(id,name) values(#{id},#{name})")
    int save(Type type);
    @Select("select * from t_type where id=#{id}")
    Type getType(Long id);

    /**
     * 分页查询
     * @return
     */
    @Select("select * from t_type")
    List<Type> listTypes();

    @Update("update t_type set id=#{id},name=#{name} where id=#{id}")
    int updateType(Type type);

    @Delete("delete from t_type where id=#{id}")
    int deleteType(Long id);

    @Select("select * from t_type where name=#{name}")
    Type getByName(String name);

    List<Type> listTypeTop();

}
