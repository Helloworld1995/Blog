package com.blog.vo;

public class TypeTopQuery {
    private String id;
    private String name;
    private Integer count;

    public String getId() {
        return id;
    }

    public TypeTopQuery setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TypeTopQuery setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public TypeTopQuery setCount(Integer count) {
        this.count = count;
        return this;
    }
}
