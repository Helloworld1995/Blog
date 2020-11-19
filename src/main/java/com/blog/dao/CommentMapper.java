package com.blog.dao;

import com.blog.po.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CommentMapper {
    @Select("SELECT * FROM t_comment WHERE blog_id=#{blogId} AND parent_comment_id is null ORDER BY create_time")
    List<Comment> listCommentByBlogId(Long blogId);
    @Insert("INSERT INTO t_comment(content,avatar,create_time,email,blog_id,nickname,parent_comment_id,admin_comment) VALUES(#{content},#{avatar},#{createTime},#{email},#{blog.id},#{nickname},#{parentComment.id},#{adminComment})")
    Integer saveComment(Comment comment);
    @Select("SELECT * FROM t_comment WHERE id=#{commentId}")
    Comment getComment(Long commentId);
    @Select("SELECT * FROM t_comment WHERE parent_comment_id=#{commentId}")
    List<Comment> listReplyComments(Long commentId);
}
