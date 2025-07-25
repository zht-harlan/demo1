package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void update(Category category);

    @Select("SELECT * FROM category WHERE type = #{type}")
    List<Category> listByType(Integer type);

    @Select("sELECT COUNT(*) FROM category WHERE id = #{id}")
    long countById(Long id);

    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    int countmealById(Long id);

    @Delete("DELETE FROM category WHERE id = #{id}")
    void delete(Category category);
}
