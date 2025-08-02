package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void updateCategory(CategoryDTO categoryDTO);

    List<Category>  listByType(Integer type);

    void deleteCategory(Long id);


    void startorstop(Integer status, Long id);

    void insert(CategoryDTO categoryDTO);


}
