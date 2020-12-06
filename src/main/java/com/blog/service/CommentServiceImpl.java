package com.blog.service;


import com.blog.dao.CommentMapper;
import com.blog.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    RedisTemplate<String,Map<Long,List<Comment>>> redisTemplate;
    @Resource
    CommentMapper commentMapper;
    private List<Comment> tempReplys;

    /**
     * 首次查询数据库然后存储到redis中，以后直接获取
     * @param blogId
     * @return
     */
    @Override
    public List<Comment> listCommentByBlogId(Long blogId){
        Map map = redisTemplate.opsForHash().entries("comment");
        if(map.get(blogId)!=null){
            System.out.println("here!!!!!!!!!!!");
            long t1=System.currentTimeMillis();
            List<Comment> res=(List<Comment>)map.get(blogId);
            long t2=System.currentTimeMillis();
            System.out.println(t2 - t1);
            return res;
        }else{
            return deepSearchComment(blogId);
        }
    }
    /**
     * 得到评论结构(二层)
     * @param blogId
     */
    public List<Comment> deepSearchComment(Long blogId){
        List<Comment> comments= commentMapper.listCommentByBlogId(blogId);
        tempReplys=new ArrayList<>();
        List<Comment> commentPool=new ArrayList<>();
        for (Comment comment : comments) {
            if(comment.getParentComment()==null) {
                commentPool.add(comment);
                search(comment, comments);
                comment.setReplyComments(tempReplys);
                tempReplys = new ArrayList<>();
            }
        }
        Map<Long,List<Comment>> map=new HashMap<>();
        map.put(blogId,commentPool);
        redisTemplate.opsForHash().putAll("comment",map);
        return commentPool;
    }
    /**
     * 深度优先遍历获取评论结构(2层)
     * @param comment
     * @param comments
     */
    private void search(Comment comment,List<Comment> comments){
        Comment c=null;
        for (Comment cs : comments) {
            c=cs.getParentComment();
            if(c==null){
                continue;
            }
            if(c.getId().longValue()==comment.getId().longValue() ){
                tempReplys.add(cs);
                search(cs,comments);
            }
        }
    }
    /**
     *  这个方法大量逐条查询数据库，IO消耗过大
     */
//    @Override
//    @Transactional
   /* public List<Comment> listCommentByBlogId1(Long blogId) {
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
    }*/
    @Override
    @Transactional
    public Integer saveComment(Comment comment) {
        Long parentCommentId=comment.getParentComment().getId();
        if(parentCommentId !=-1){
            comment.setParentComment(commentMapper.getComment(parentCommentId));
        }else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        Integer res=commentMapper.saveComment(comment);
        Comment resComment = commentMapper.getLatestComment();
        checkCommentPool(commentMapper.getLatestComment());
        if(resComment!=null&&res!=null){
            return 1;
        }
        return 0;
    }

    private synchronized void checkCommentPool(Comment comment) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries("comment");
        List<Comment> list = (List<Comment>) map.get(comment.getBlog().getId());
        if (comment.getParentComment() == null) {
            list.add(comment);
        } else {
            loop:
            for (Comment c : list) {
                if (c.getId().longValue() == comment.getParentComment().getId().longValue()) {
                    c.getReplyComments().add(comment);
                    break;
                } else {
                    for (Comment replyComment : c.getReplyComments()) {
                        if (replyComment.getId().longValue() == comment.getParentComment().getId().longValue()) {
                            c.getReplyComments().add(comment);
                            break loop;
                        }
                    }
                }
            }
        }
        redisTemplate.opsForHash().put("comment", comment.getBlog().getId(), list);
    }
    @Override
    public Comment getComment(Long commentId) {
        return commentMapper.getComment(commentId);
    }
}
