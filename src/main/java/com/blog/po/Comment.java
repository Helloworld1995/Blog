package com.blog.po;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论实体
 */
@Entity
@Table(name = "t_comment")
public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String email;
    private String content;

    private String avatar; //url地址or编码
    @Temporal(TemporalType.TIMESTAMP) //写入数据库的日期格式:日期+时分秒
    @Column(name="create_time")
    private Date createTime;
    @ManyToOne()
    private Blog blog;
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments=new ArrayList<>();//评论中包含多个回复
    @ManyToOne
    private Comment parentComment; //回复的父级别评论

    private boolean adminComment;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public Comment setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public Comment setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public List<Comment> getReplyComments() {
        return replyComments;
    }

    public Comment setReplyComments(List<Comment> replyComments) {
        this.replyComments = replyComments;
        return this;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public Comment setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Comment setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public Comment setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Comment setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Blog getBlog() {
        return blog;
    }

    public Comment setBlog(Blog blog) {
        this.blog = blog;
        return this;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public Comment setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
        return this;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
