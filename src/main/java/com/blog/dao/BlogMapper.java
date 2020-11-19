package com.blog.dao;

import com.blog.po.Blog;
import com.blog.vo.BlogQuery;
import com.blog.vo.BlogToTagQuery;
import org.apache.ibatis.annotations.*;
import java.util.List;


public interface BlogMapper {
    Blog getBlog(Long id);
    /**
     * 分页查询
     * @param
     * @return
     */
    List<Blog> listBlog(BlogQuery blogQuery);

    List<Blog> listAllBlogs();

    Integer saveBlog(Blog blog);

    Integer updateBlog(Blog blog);

    @Delete("delete from t_blog where id=#{id}")
    Integer deleteBlog(Long id);

    Integer saveBlogToTag(@Param("blog") Blog blog);

    @Select("select * from t_blog_tags where blogs_id=#{id} and tags_id=#{tagId}")
    List<BlogToTagQuery> checkBlogToTag(@Param("id") Long id, @Param("tagId") Long tagId);

    List<BlogToTagQuery> getBlogToTag(Long id);

    @Delete("delete from t_blog_tags where blogs_id=#{id}")
    Integer clearBlogToTags(Long id);

    @Delete("delete from t_blog_tags where blogs_id=#{id} and tags_id=#{tagId}")
    Integer deleteBlogToTag(Long id,Long tagId);

    @Select("select * from t_blog where recommend=true")
    List<Blog> listBlogTop();

    List<Blog> listBlogQuery(String query);

    @Update("update t_blog set views=views+1 where id=#{id}")
    Integer updateViews(Long id);

    List<Blog> listBlogByTypeId(Long id);

    List<Blog> listBlogByTagId(Long id);
    @Select("SELECT DISTINCT date_format(tb.update_time,'%Y') year FROM t_blog tb WHERE tb.published ORDER BY year DESC")
    List<String> findYears();

    @Select("SELECT tb.* FROM t_blog tb WHERE date_format(tb.update_time,'%Y')=#{year}")
    List<Blog> listBlogByYear(String year);
    @Select("SELECT COUNT(1) FROM t_blog WHERE published")
    Long countBlogs();
}
