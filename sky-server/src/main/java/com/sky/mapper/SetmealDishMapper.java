package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {


    @Delete("DELETE FROM setmeal_dish WHERE setmeal_id = #{id}")
    void deleteBySetmealId(Long id);

    @Select("SELECT * FROM setmeal_dish WHERE setmeal_id = #{id}")
    List<SetmealDish> selectBySetmealId(Long id);


    int countByDishId(List<Long> ids);

    void insertBatch(List<SetmealDish> setmealDishes);


}
