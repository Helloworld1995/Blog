package com.blog.service;

import com.blog.NotFoundException;
import com.blog.dao.TypeMapper;
import com.blog.po.Type;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{
    @Autowired(required = false)
    private TypeMapper typeMapper;
    @Transactional
    @Override
    public int save(Type type) {
        return typeMapper.save(type);
    }

    @Override
    public Type getType(Long id) {
        return typeMapper.getType(id);
    }

    @Override
    public List<Type> findByPages(int pageNo,int pageSize) {
        String orderBy="id"+" desc";
        PageHelper.startPage(pageNo,pageSize,orderBy);
        List<Type> types = typeMapper.listTypes();
        if(types==null){
            throw new NotFoundException();
        }
        return types;
    }

    @Transactional
    @Override
    public int updateType(Long id, Type type) {
        Type t = typeMapper.getType(id);
        if(t==null){
            throw new NotFoundException();
        }
        return typeMapper.updateType(type);
    }

    @Override
    public Type getByName(String name) {
        return typeMapper.getByName(name);
    }

    @Transactional
    @Override
    public int deleteType(Long id) {
        return typeMapper.deleteType(id);
    }

    @Override
    public List<Type> listTypes() {
        return typeMapper.listTypes();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
       PageHelper.startPage(0,size);
       List<Type> types = typeMapper.listTypeTop();
        Collections.sort(types, new Comparator<Type>() {
            @Override
            public int compare(Type o1, Type o2) {
                return o2.getBloglist().size()-o1.getBloglist().size();
            }
        });
       return types;
    }
}
