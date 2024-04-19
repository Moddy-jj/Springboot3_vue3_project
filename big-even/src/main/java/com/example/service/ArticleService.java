package com.example.service;
import com.example.pojo.Article;
import com.example.pojo.PageBean;

public interface ArticleService {
    // 新增文章
    void add(Article article);
    // 根据id查询文章
    Article findById(Integer id);
    // 更新
    void update(Article article);
    // 删除
    void delete(Integer id);
    // 条件分页查询文章
    PageBean<Article> query(Integer pageNum, Integer pageSize,Integer categoryId,String state);
}
