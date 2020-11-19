package com.blog.service;

import com.blog.dao.CommentMapper;
import com.blog.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    private List<Comment> tempReplys=new ArrayList<>();

    @Override
    @Transactional
    public List<Comment> listCommentByBlogId(Long blogId) {
        List<Comment> comments = commentMapper.listCommentByBlogId(blogId);
        for (Comment comment : comments) {
            searchRelyComments(comment);
            comment.setReplyComments(tempReplys);
            tempReplys=new ArrayList<>();
        }
        return comments;
    }
    private void searchRelyComments(Comment comment){
        List<Comment> replyComments = commentMapper.listReplyComments(comment.getId());
        if(replyComments!=null&&replyComments.size()>0){
            for (Comment replyComment : replyComments) {
                replyComment.setParentComment(comment);
                tempReplys.add(replyComment);
                searchRelyComments(replyComment);
            }
        }
    }
    @Transactional
    @Override
    public Integer saveComment(Comment comment) {
        Long parentCommentId=comment.getParentComment().getId();
        if(parentCommentId !=-1){
            comment.setParentComment(commentMapper.getComment(parentCommentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentMapper.saveComment(comment);
    }

    @Override
    public Comment getComment(Long commentId) {
        return commentMapper.getComment(commentId);
    }
}
