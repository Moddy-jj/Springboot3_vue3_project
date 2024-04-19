package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pojo.Article;
import com.example.service.ArticleService;
import com.example.pojo.Result;
import com.example.pojo.PageBean;
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @PostMapping()
    public Result<String> add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }
    @GetMapping()
    public Result<PageBean<Article>> query(Integer pageNum, Integer pageSize,
                                            @RequestParam(required = false) Integer categoryId,
                                            @RequestParam(required = false) String state) {
        PageBean<Article> pb= articleService.query(pageNum, pageSize, categoryId, state);
        return Result.success(pb);
    }
    @GetMapping("/detail")
    public Result<Article> detail(Integer id) {
        Article a = articleService.findById(id);
        return Result.success(a);
    }
    @PutMapping()
    public Result update(@RequestBody @Validated(Article.Update.class) Article article) {
        articleService.update(article);
        return Result.success();
    }
    @DeleteMapping()
    public Result delect(Integer id) {
        articleService.delete(id);
        return Result.success();
    }
}
