package com.blog.dao;
import com.blog.po.Tag;
import com.blog.vo.TagTopQuery;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


import java.util.List;


public interface TagMapper {
    @Insert("insert into t_tag(name) values(#{name})")
    int save(Tag tag);
    @Select("select * from t_tag where id=#{id}")
    Tag getTag(Long id);

    /**
     * 分页查询
     * @return
     */
    @Select("select * from t_tag")
    List<Tag> findByPages();

    @Update("update t_tag set id=#{id},name=#{name} where id=#{id}")
    int updateTag(Tag Tag);

    @Delete("delete from t_tag where id=#{id}")
    int deleteTag(Long id);

    @Select("select * from t_tag where name=#{name}")
    Tag getByName(String name);

    @Select("select * from t_tag")
    List<Tag> listTags();

    List<Tag> getTags(List<Long> tagsIdList);

    List<Tag> listTagTop();

    List<Tag> listTagByBlog(Long blogId);
}
