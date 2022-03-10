package com.xuan.vueblog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuan.vueblog.common.lang.Result;
import com.xuan.vueblog.entity.Blog;
import com.xuan.vueblog.service.BlogService;
import com.xuan.vueblog.util.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

/**
 * @author yuan
 * @date 3/8/22
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @CrossOrigin
    @GetMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){

        Page page = new Page(currentPage,5);
        Page pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.success(pageData);
    }

    @CrossOrigin
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id){

        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客为空");

        return Result.success(blog);
    }

    @CrossOrigin
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){

        Blog temp = null;
        //blog没有id就是添加，否则是修改
        if (blog.getId() != null){

            temp = blogService.getById(blog.getId());
            //只能编辑自己的文章
            Assert.isTrue(temp.getUserId() .equals(ShiroUtils.getProfile().getId()),"没有权限编辑");
            BeanUtils.copyProperties(blog,temp,"id","userId","Created","status");
            blogService.saveOrUpdate(temp);
        }else {

            temp = new Blog();
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setCreated(Calendar.getInstance().getTime());
            temp.setStatus(0);

            BeanUtils.copyProperties(blog,temp,"id","userId","created","status");
            blogService.saveOrUpdate(temp);
        }

        return Result.success("添加或修改博客成功!");
    }

    @CrossOrigin
    @RequiresAuthentication
    @DeleteMapping("/blog/{blogId}")
    public Result delete(@PathVariable("blogId")Long blogId){

        Long blogUserId = blogService.getById(blogId).getUserId();
        Assert.isTrue(blogUserId.equals(ShiroUtils.getProfile().getId()),"没有权限删除");
        blogService.remove(new QueryWrapper<Blog>().lambda()
                .eq(Blog::getId,blogId));
        return Result.success("删除博客成功！");
    }
}
