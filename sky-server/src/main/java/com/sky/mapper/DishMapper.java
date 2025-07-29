package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {
    @AutoFill(value =OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> getDish(DishPageQueryDTO dishPageQueryDTO);

    @Select("SELECT * FROM dish WHERE id = #{id}")
    Dish getById(Long id);

    @Delete("DELETE FROM dish WHERE id = #{id}")
    void deleteByDishId(Long id);


    @AutoFill(value =OperationType.UPDATE)
    void update(Dish dish);
}
