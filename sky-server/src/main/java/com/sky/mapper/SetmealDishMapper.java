package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {


    @Select("SELECT COUNT(*) FROM setmeal_dish WHERE dish_id IN (#{ids})")
    int countByDishId(List<Long> ids);
}
