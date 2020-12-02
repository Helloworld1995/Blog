package com.blog.web.admin;

import com.blog.po.Tag;
import com.blog.service.TagService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by limi on 2017/10/16.
 */
@PropertySource("classpath:i18n/message.properties")
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;
    @Value("${admin.index.tagCount}")
    private Integer tagCount;
    @GetMapping("/tags")
    public String tags(@RequestParam(name = "page",required = true,defaultValue = "1") int page,
                       @RequestParam(name = "size",required = true,defaultValue = "3") int size, Model model) {
        size=tagCount;
        List<Tag> tags = tagService.findByPages(page, size);
        PageInfo<Tag> tagPageInfo = new PageInfo<>(tags);
        model.addAttribute("tags",tagPageInfo);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }


    @PostMapping("/tags")
    public String post(@Valid Tag tag,BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagService.getByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Integer res = tagService.save(tag);
        if (res ==null || res >0) {
            attributes.addFlashAttribute("message", "新增成功");
        } else if(res==null||res<=0){
            attributes.addFlashAttribute("message", "新增失败");
        }
        return "redirect:/admin/tags";
    }


    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        Tag tag1 = tagService.getByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Integer res= tagService.updateTag(id,tag);
        if (res==null||res<=0) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }


}
