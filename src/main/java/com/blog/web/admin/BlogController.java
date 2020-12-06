package com.blog.web.admin;


import com.blog.po.Blog;
import com.blog.po.User;
import com.blog.service.BlogService;
import com.blog.service.TagService;
import com.blog.service.TypeService;
import com.blog.vo.BlogQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@PropertySource("classpath:i18n/message.properties")
@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;
    @Value("${admin.index.blogCount}")
    private Integer blogCount;
    @GetMapping("/blogs")
    public String listBlog(@RequestParam(name = "page",required = true,defaultValue = "1") int page,
                       @RequestParam(name = "size",required = true,defaultValue = "3") int size,BlogQuery blogQuery, Model model){

        List<Blog> blogs = blogService.listBlog(page, blogCount, blogQuery);
        PageInfo<Blog> blogsByPages=new PageInfo<>(blogs);
        model.addAttribute("blogs",blogsByPages);
        model.addAttribute("types",typeService.listTypes());
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(@RequestParam(name = "page",required = true,defaultValue = "1") int page,
                         @RequestParam(name = "size",required = true,defaultValue = "5") int size, BlogQuery blogQuery, Model model){
        List<Blog> blogs = blogService.listBlog(page, size, blogQuery);
        PageInfo<Blog> blogsByPages=new PageInfo<>(blogs);
        model.addAttribute("blogs",blogsByPages);
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        setTypeAndTag(model);
        return "admin/blogs-input";
    }


    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        setTypeAndTag(model);
        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/blogs-input";
    }

    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listTypes());
        model.addAttribute("tags",tagService.listTags());
    }
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        if(!blog.getTagIdList().isEmpty()) {
            blog.setTags(tagService.getTags(blog.getTagIdList()));
        }
        int res=0;
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            res = blogService.saveBlog(blog);
        }else{
            blog.setUpdateTime(new Date());
            res = blogService.updateBlog(blog);
        }
        if(res>0){
            attributes.addFlashAttribute("message","操作成功");
        }else{
            attributes.addFlashAttribute("message","操作失败");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable Long id,RedirectAttributes attributes){
        Integer res=blogService.deleteBlog(id);
        if(res!=null||res!=0){
            attributes.addFlashAttribute("message","操作成功");
        }else{
            attributes.addFlashAttribute("message","操作失败");
        }
        return "redirect:/admin/blogs";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model, HttpServletRequest request) {
        String url=request.getRequestURI();
        model.addAttribute("blog", blogService.getBlogAndConvert(id,url));
        return "admin/blog";
    }
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listBlogTop(3));
        return "admin/_fragments :: newblogList";
    }
}
