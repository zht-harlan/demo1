package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetmealMapper {
    @Delete("DELETE FROM setmeal WHERE id = #{id}")
    static void deleteById(Long id) {
    }

    Page<SetmealVO> page(SetmealPageQueryDTO setmealDTO);

    @Select("SELECT * FROM setmeal WHERE id = #{id}")
    Setmeal selectById(Long id);
}
