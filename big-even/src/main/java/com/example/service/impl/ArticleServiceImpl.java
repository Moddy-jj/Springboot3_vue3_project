package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.example.mapper.ArticleMapper;
import com.example.pojo.Article;
import com.example.pojo.PageBean;
import com.example.service.ArticleService;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        //补充属性
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }
    @Override
    public PageBean<Article> query(Integer pageNum, Integer pageSize,Integer categoryId,String state) {
        // 创建分页对象
        PageBean<Article> pb = new PageBean<>();
        //开启分页PageHelper
        PageHelper.startPage(pageNum, pageSize);
        //调用Mapper
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.query(userId,categoryId,state);
        //Page中提供了方法，封装了数据和分页信息
        Page<Article> page = (Page<Article>) as;
        //封装到PageBean中
        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());
        return pb;
    }
    @Override
    public Article findById(Integer id) {
        //补充属性
        return articleMapper.findById(id);    
    }
    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);    
    }
    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);    
    }

}
