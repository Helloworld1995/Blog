package com.blog.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签实体
 */
@Entity
@Table(name = "t_tag")
public class Tag implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "标签名称不能为空")
    private String name;
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs=new ArrayList<>();
    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public Tag setId(Long id) {
        this.id = id;
        return this;
    }



    public String getName() {
        return name;
    }

    public Tag setName(String name) {
        this.name = name;
        return this;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public Tag setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
        return this;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
