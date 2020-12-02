package com.blog.web;

import com.blog.po.Blog;
import com.blog.po.Tag;
import com.blog.po.Type;
import com.blog.service.BlogService;
import com.blog.service.TagService;
import com.blog.service.TypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
@PropertySource("classpath:i18n/message.properties")
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Value("${index.blogCount}")
    private Integer blogCount;
    @Value("${index.typeCount}")
    private Integer typeCount;
    @Value("${index.tagCount}")
    private Integer tagCount;
    @Value("${index.recommendBlogCount}")
    private Integer recommendBlogCount;
    @GetMapping("/")
    public String index(@RequestParam(name = "page", required = true, defaultValue = "1") int page,
                        @RequestParam(name = "size", required = true, defaultValue = "5")  int size, Model model) {
        PageInfo<Blog> blogs = new PageInfo<>(blogService.listAllBlogs(page, blogCount));
        PageInfo<Type> types = new PageInfo<>(typeService.listTypeTop(typeCount));
        PageInfo<Tag> tags = new PageInfo<>(tagService.listTagTop(tagCount));
        PageInfo<Blog> recommendBlogs = new PageInfo<>(blogService.listBlogTop(recommendBlogCount));
        model.addAttribute("blogs", blogs);
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        model.addAttribute("recommendBlogs", recommendBlogs);
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model, HttpServletRequest request) {
        model.addAttribute("blog", blogService.getBlogAndConvert(id,request.getRequestURI()));
        return "blog";
    }

    @PostMapping("/search")
    public String search(@RequestParam(name = "page", required = true, defaultValue = "1") int page,
                         @RequestParam(name = "size", required = true, defaultValue = "5") int size,
                         @RequestParam String query, Model model) {

        PageInfo<Blog> blogs = new PageInfo<>(blogService.listBlogQuery("%" + query + "%", page, size));
        model.addAttribute("blogs", blogs);
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listBlogTop(3));
        return "_fragments :: newblogList";
    }
}




