package com.blog.service;

import com.blog.NotFoundException;
import com.blog.dao.TagMapper;
import com.blog.po.Tag;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public int save(Tag Tag) {
        return tagMapper.save(Tag);
    }

    @Override
    public Tag getTag(Long id) {
        return tagMapper.getTag(id);
    }

    @Override
    @SuppressWarnings("all")
    public List<Tag> findByPages(int pageNo,int pageSize) {
        String orderBy="id"+" desc";
        PageHelper.startPage(pageNo,pageSize,orderBy);
        List<Tag> Tags = tagMapper.findByPages();
        if(Tags==null){
            throw new NotFoundException();
        }
        return Tags;
    }
    @Transactional
    @Override
    public int updateTag(Long id, Tag tag) {
        Tag t = tagMapper.getTag(id);
        if(t==null){
            throw new NotFoundException();
        }
        return tagMapper.updateTag(tag);
    }

    @Override
    public Tag getByName(String name) {
        return tagMapper.getByName(name);
    }

    @Override
    public List<Tag> listTags() {
        return tagMapper.listTags();
    }

    @Override
    public List<Tag> getTags(List<Long> tagIds) {
        return tagMapper.getTags(tagIds);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        String order="blogsCount"+" desc";
        PageHelper.startPage(0,size,order);
        List<Tag> tags = tagMapper.listTagTop();
        return tags;
    }

    @Override
    public List<Tag> listTagByBlog(Long blogId) {
        return tagMapper.listTagByBlog(blogId);
    }

    @Transactional
    @Override
    public int deleteTag(Long id) {
        return tagMapper.deleteTag(id);
    }
}
