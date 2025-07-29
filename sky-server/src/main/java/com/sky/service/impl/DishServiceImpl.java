package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.apache.poi.ss.formula.constant.ErrorConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {


    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Transactional
    @Override
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        // 保存菜品基本信息
        dishMapper.insert(dish);
        long dishId = dish.getId(); // 获取菜品ID

        // 获取菜品ID
        List<DishFlavor> flavors = dishDTO.getFlavors();
        // 保存菜品口味信息
        if (dishDTO.getFlavors() != null && !dishDTO.getFlavors().isEmpty()) {
            flavors.forEach(flavor -> {
                flavor.setDishId(dishId);
            });

            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public PageResult getDish(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.getDish(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 删除菜品
     *
     * @param ids
     */
    @Override
    public void delete(List<Long> ids) {
        // 删除菜品口味
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            // 删除菜品口味
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // 检查菜品是否关联套餐
        if (setmealDishMapper.countByDishId(ids) > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

    // 删除菜品
        ids.forEach(id ->

    {
        // 删除菜品口味
        dishMapper.deleteByDishId(id);
        dishFlavorMapper.deleteByDishId(id);
    });
    }

    @Override
    public DishVO getById(Long id) {
        Dish dish=dishMapper.getById(id);
        DishVO dishVO = new DishVO();
        List<DishFlavor> dishFlavors =dishFlavorMapper.getByDishId(id);
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;

    }

    @Transactional
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        dishFlavorMapper.deleteByDishId(dish.getId());
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null&&flavors.size()>0){
            flavors.forEach(flavor->{
                flavor.setDishId(dish.getId());
            });
        }
        dishFlavorMapper.insertBatch(flavors);
    }

    @Override
    public List<Dish> list(Long id) {
        Dish dish = Dish.builder()
                .status(StatusConstant.ENABLE)
                .categoryId(id)
                .build();
        List<Dish> dishes=dishMapper.list(dish);
        return dishes;
    }
}

