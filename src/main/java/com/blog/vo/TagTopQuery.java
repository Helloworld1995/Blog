package com.blog.vo;

public class TagTopQuery {
    private String id;
    private String name;
    private Integer count;

    public String getId() {
        return id;
    }

    public TagTopQuery setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TagTopQuery setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public TagTopQuery setCount(Integer count) {
        this.count = count;
        return this;
    }
}
