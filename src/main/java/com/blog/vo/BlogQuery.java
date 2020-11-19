package com.blog.vo;

public class BlogQuery {
    private String title;
    private Long typeId;
    private Boolean recommend;

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public BlogQuery setTitle(String title) {
        this.title = title;
        return this;
    }

    public Long getTypeId() {
        return typeId;
    }

    public BlogQuery setTypeId(Long typeId) {
        this.typeId = typeId;
        return this;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public BlogQuery setRecommend(Boolean recommend) {
        this.recommend = recommend;
        return this;
    }
}
