package com.blog.service;

import com.blog.po.Blog;

import com.blog.vo.BlogQuery;
import com.blog.vo.BlogToTagQuery;


import java.util.List;
import java.util.Map;


public interface BlogService {
    Blog getBlog(Long id);

    /**
     * 分页查询
     * @param
     * @return
     */
    List<Blog> listBlog(int pageNo, int pageSize, BlogQuery blogQuery);

    List<Blog> listAllBlogs(int pageNo,int pageSize);

    int saveBlog(Blog blog);

    int updateBlog(Blog blog);

    Integer deleteBlog(Long id);


    Integer saveBlogToTag(Blog blog);


    List<BlogToTagQuery> checkBlogToTag(Long id,Long tagId);


    List<BlogToTagQuery> getBlogToTag(Long id);

    void insertBlogToTag(Blog blog);

    Integer clearBlogToTags(Long id);

    Integer deleteBlogToTag(Long id,Long tagId);

    List<Blog> listBlogTop(Integer size);

    List<Blog> listBlogQuery(String query,int pageNo, int pageSize);

    Blog getBlogAndConvert(Long id,String url);

    List<Blog> listBlogByTypeId(Integer page,Integer size,Long id);

    List<Blog> listBlogByTagId(Integer page,Integer size,Long id);

    Map<String,List<Blog>> archiveBlog();

    List<Blog> listBlogByYear(String year);

    Long countBlogs();




}
