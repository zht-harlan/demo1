package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    Object listByType(Integer type);

    void deleteCategory(Long id);


    void startorstop(Integer status, Long id);

    void insert(CategoryDTO categoryDTO);
}
