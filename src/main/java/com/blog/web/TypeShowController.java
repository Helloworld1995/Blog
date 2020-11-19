package com.blog.web;

import com.blog.po.Blog;
import com.blog.po.Type;
import com.blog.service.BlogService;
import com.blog.service.TypeService;
import com.blog.vo.BlogQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @GetMapping("/types/{id}")
    public String types(@RequestParam(name = "page",required = true,defaultValue = "1") int page,
                        @RequestParam(name = "size",required = true,defaultValue = "3") int size, @PathVariable Long id, Model model){
        List<Type> types = typeService.listTypeTop(100);
        if(id==-1){
            id = types.get(0).getId();
        }
        BlogQuery query = new BlogQuery();
        query.setTypeId(id);
        List<Blog> blogs = blogService.listBlogByTypeId(page,size,id);
        PageInfo<Blog> blogsPageInfo = new PageInfo<>(blogs);
        PageInfo<Type> typePageInfo = new PageInfo<>(types);
        model.addAttribute("types",typePageInfo);
        model.addAttribute("blogs",blogsPageInfo);
        model.addAttribute("activeTypeId",id);
        return "types";

    }
}
