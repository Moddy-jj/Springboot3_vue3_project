package com.example.service;

import com.example.pojo.Category;
import java.util.List;

public interface CategoryService {
    // 新增分类
    void add(Category category);
    // 分类查询
    List<Category> list();
    // id详情查询
    Category findById(Integer id);

    // 修改
    void update(Category category);
    // 删除
    void delete(Integer id);
}
