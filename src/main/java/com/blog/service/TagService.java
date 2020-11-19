package com.blog.service;

import com.blog.po.Tag;
import com.blog.vo.TagTopQuery;

import java.util.List;

public interface TagService {

    int save(Tag tag);

    Tag getTag(Long id);

    /**
     * 分页查询
     * @return
     */

    List<Tag> findByPages(int pageNo,int pageSize);


    int updateTag(Long id,Tag Tag);

    int deleteTag(Long id);

    Tag getByName(String name);

    List<Tag> listTags();

    List<Tag> getTags(List<Long> tagIds);

    List<Tag> listTagTop(Integer size);

    List<Tag> listTagByBlog(Long blogId);

}
