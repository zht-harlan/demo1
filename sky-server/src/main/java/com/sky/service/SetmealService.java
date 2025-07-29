package com.sky.service;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface SetmealService {
    PageResult page(SetmealPageQueryDTO setmealDTO);

    void delete(List<Long> ids);
}
