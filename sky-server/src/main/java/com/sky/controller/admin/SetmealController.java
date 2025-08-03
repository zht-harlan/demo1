package com.sky.controller.admin;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Api(tags= "套餐管理")
public class SetmealController {

    @Autowired
    private DishService dishservice;
    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    @ApiOperation("分页查询套餐列表")
    public Result page(SetmealPageQueryDTO setmealDTO) {

        PageResult page = setmealService.page(setmealDTO);
        return Result.success(page);
    }

    @CacheEvict(cacheNames = "setmeal",allEntries = true)
    @DeleteMapping
    @ApiOperation("删除套餐")
    public Result delete(@RequestParam List<Long> ids) {
        setmealService.delete(ids);
        return Result.success();
    }
    @CacheEvict(cacheNames = "setmeal",allEntries = true)
    @PostMapping("/status/{status}")
    @ApiOperation("起售/停售套餐")
    public Result status(@PathVariable Integer status, @RequestParam Long id) {
        setmealService.status(status, id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据ID查询套餐")
    public Result getById(@PathVariable Long id) {
        SetmealVO setmealVO = setmealService.getById(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("修改套餐")
    @CacheEvict(cacheNames = "setmeal",allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO) {
        setmealService.update(setmealDTO);
        return Result.success();
    }

    @CacheEvict(cacheNames = "setmeal", key="#setmealDTO.categoryId")
    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        setmealService.save(setmealDTO);
        return Result.success();
    }

}
