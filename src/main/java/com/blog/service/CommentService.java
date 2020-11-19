package com.blog.service;

import com.blog.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Integer saveComment(Comment comment);

    Comment getComment(Long commentId);
}
