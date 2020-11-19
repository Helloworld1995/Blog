package com.blog.vo;

public class BlogToTagQuery {
    private Long blogs_id;
    private Long tags_id;

    public Long getBlogs_id() {
        return blogs_id;
    }

    public BlogToTagQuery setBlogs_id(Long blogs_id) {
        this.blogs_id = blogs_id;
        return this;
    }

    public Long getTags_id() {
        return tags_id;
    }

    public BlogToTagQuery setTags_id(Long tags_id) {
        this.tags_id = tags_id;
        return this;
    }
}
