package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    private  SetmealMapper setmealMapper;

    public SetmealServiceImpl(SetmealMapper setmealMapper) {
        this.setmealMapper = setmealMapper;
    }

    @Override
    public PageResult page(SetmealPageQueryDTO setmealDTO) {
        PageHelper.startPage(setmealDTO.getPage(), setmealDTO.getPageSize());
        Page<SetmealVO> page= setmealMapper.page(setmealDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Transactional
    public void delete(List<Long> ids) {
        ids.forEach(id-> {
            Setmeal setmeal = setmealMapper.selectById(id);
            if(setmeal.getStatus() == 1) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });

        ids.forEach(id->{
            SetmealMapper.deleteById(id);
            SetmealDishMapper.deleteBySetmealId(id);
        });
        return;
    }
}
