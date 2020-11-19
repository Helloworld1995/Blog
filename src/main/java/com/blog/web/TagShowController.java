package com.blog.web;

import com.blog.po.Blog;
import com.blog.po.Tag;
import com.blog.service.BlogService;
import com.blog.service.TagService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/tags/{id}")
    public String tags(@RequestParam(name = "page",required = true,defaultValue = "1") int page,
                        @RequestParam(name = "size",required = true,defaultValue = "3") int size, @PathVariable Long id, Model model){
        List<Tag> tags = tagService.listTagTop(100);
        if(id==-1){
            id = tags.get(0).getId();
        }
        List<Blog> blogs = blogService.listBlogByTagId(page,size,id);
        for (Blog blog : blogs) {
            List<Tag> tagsForBlog = tagService.listTagByBlog(blog.getId());
            blog.setTags(tagsForBlog);
        }
        PageInfo<Blog> blogsPageInfo = new PageInfo<>(blogs);
        PageInfo<Tag> tagPageInfo = new PageInfo<>(tags);
        model.addAttribute("tags",tagPageInfo);
        model.addAttribute("blogs",blogsPageInfo);
        model.addAttribute("activeTagId",id);
        return "tags";

    }
}
