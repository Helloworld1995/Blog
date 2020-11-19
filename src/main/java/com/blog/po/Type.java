package com.blog.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 类型实体
 */
@Entity
@Table(name = "t_type")
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;
    @OneToMany(mappedBy = "type")
    private List<Blog> bloglist=new ArrayList<>();
    @Transient
    private Integer count;
    public Type() {
    }

    public Long getId() {
        return id;
    }

    public Type setId(Long id) {
        this.id = id;
        return this;
    }


    public String getName() {
        return name;
    }

    public Type setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public Type setCount(Integer count) {
        this.count = count;
        return this;
    }

    public List<Blog> getBloglist() {
        return bloglist;
    }

    public Type setBloglist(List<Blog> bloglist) {
        this.bloglist = bloglist;
        return this;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bloglist=" + bloglist +
                '}';
    }
}
