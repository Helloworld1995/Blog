package com.blog.service;

import com.blog.NotFoundException;
import com.blog.dao.BlogMapper;
import com.blog.po.Blog;
import com.blog.utils.MarkdownUtils;
import com.blog.vo.BlogQuery;
import com.blog.vo.BlogToTagQuery;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired(required = false)
    private BlogMapper blogMapper;
    @Override
    public Blog getBlog(Long id) {
        return blogMapper.getBlog(id);
    }

    @Override
    public List<Blog> listBlog(int pageNo, int pageSize, BlogQuery blogQuery) {
        pageInfo(pageNo,pageSize);
        List<Blog> blogs = blogMapper.listBlog(blogQuery);
        if(blogs==null){
            throw new NotFoundException();
        }
        return blogs;
    }
    private void pageInfo(int pageNo, int pageSize){
        String orderBy="update_time"+" desc";
        PageHelper.startPage(pageNo, pageSize, orderBy);
    }
    @Override
    public List<Blog> listAllBlogs(int pageNo, int pageSize) {
        String orderBy="tb.update_time"+" desc";
        PageHelper.startPage(pageNo, pageSize, orderBy);
        List<Blog> blogs = blogMapper.listAllBlogs();
        if(blogs==null){
            throw new NotFoundException();
        }
        return blogs;
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        Date date=new Date();
        blog.setCreateTime(date);
        blog.setUpdateTime(date);
        blog.setViews(0);
        int res=blogMapper.saveBlog(blog);
        insertBlogToTag(blog);
        return res;
    }
    @Override
    public void insertBlogToTag(Blog blog){
        this.clearBlogToTags(blog.getId());
        if(!blog.getTagIdList().isEmpty()){
            this.saveBlogToTag(blog);
        }
    }
    @Override
    public Integer clearBlogToTags(Long id) {
        return blogMapper.clearBlogToTags(id);
    }

    @Override
    public Integer deleteBlogToTag(Long id,Long tagId) {
        return blogMapper.deleteBlogToTag(id,tagId);
    }

    @Override
    public List<Blog> listBlogTop(Integer size) {
        pageInfo(0,size);
        List<Blog> blogs=blogMapper.listBlogTop();
        Collections.sort(blogs, new Comparator<Blog>() {
            @Override
            public int compare(Blog o1, Blog o2) {

                return o2.getViews()-o1.getViews();
            }
        });
        return blogs;
    }

    @Override
    public List<Blog> listBlogQuery(String query,int pageNo, int pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        return blogMapper.listBlogQuery(query);
    }
    @Transactional
    @Override
    public Blog getBlogAndConvert(Long id,String url) {
        Blog blog = blogMapper.getBlog(id);
        if(blog==null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content=b.getContent();
        String convertedContent = MarkdownUtils.markdownToHtmlExtensions(content);
        b.setContent(convertedContent);
        if(!url.contains("admin")) {
            blogMapper.updateViews(id);
        }
        return b;
    }
    @Override
    public List<Blog> listBlogByTypeId(Integer page,Integer size,Long id) {
        pageInfo(page,size);
        return blogMapper.listBlogByTypeId(id);
    }

    @Override
    public List<Blog> listBlogByTagId(Integer page, Integer size, Long id) {
        pageInfo(page,size);
        return blogMapper.listBlogByTagId(id);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogMapper.findYears();
        Map<String,List<Blog>> map=new LinkedHashMap<>();
        for (String year : years) {
            List<Blog> blogs = blogMapper.listBlogByYear(year);
            map.put(year,blogs);
        }
        return map;
    }

    @Override
    public List<Blog> listBlogByYear(String year) {
        return blogMapper.listBlogByYear(year);
    }

    @Override
    public Long countBlogs() {
        return blogMapper.countBlogs();
    }

    @Transactional
    @Override
    public int updateBlog(Blog blog) {
        Blog b=blogMapper.getBlog(blog.getId());
        if(b==null){
            throw new NotFoundException("该博客不存在");
        }
        insertBlogToTag(blog);
        return blogMapper.updateBlog(blog);
    }
    @Transactional
    @Override
    public Integer deleteBlog(Long id) {
        blogMapper.clearBlogToTags(id);
        return blogMapper.deleteBlog(id);
    }

    @Override
    public Integer saveBlogToTag(Blog blog) {
        return blogMapper.saveBlogToTag(blog);
    }

    @Override
    public List<BlogToTagQuery> checkBlogToTag(Long id, Long tagId) {
        return blogMapper.checkBlogToTag(id,tagId);
    }

    @Override
    public List<BlogToTagQuery> getBlogToTag(Long id) {
        return blogMapper.getBlogToTag(id);
    }
}
