package com.sky.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {


    @Delete("DELETE FROM setmeal_dish WHERE setmeal_id = #{id}")
    static void deleteBySetmealId(Long id) {
    }


    int countByDishId(List<Long> ids);
}
