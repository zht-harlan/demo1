package com.sky.controller.admin;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类管理接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询分类列表
     *
     * @param categoryPageQueryDTO 分页查询条件
     * @return 分页结果
     */
    @ApiOperation(value = "分页查询分类列表")
    @GetMapping("/page")
    public Result pageguery(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询分类列表: {}", categoryPageQueryDTO);
        PageResult pageResult =categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PutMapping
    @ApiOperation(value = "更新分类信息")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("更新分类信息: {}", categoryDTO);
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }

    /**
     * 根据类型查询所有分类列表
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "根据类型查询所有分类列表")
    public Result list(@RequestParam("type") Integer type) {
        log.info("根据类型查询分类列表: type={}", type);
        return Result.success(categoryService.listByType(type));
    }


    @DeleteMapping
    @ApiOperation(value = "删除分类")
    public Result deleteCategory(@RequestParam("id") Long id) {
        log.info("删除分类: id={}", id);
        // 这里可以添加删除逻辑
        categoryService.deleteCategory(id);
        return Result.success();
    }

    /**
     * 修改分类状态
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改分类状态")
    public Result updateStatus(@PathVariable("status") Integer status, @RequestParam("id") Long id) {
        log.info("修改分类状态: id={}, status={}", id, status);
        categoryService.startorstop(status, id);
        return Result.success();
    }

    @PostMapping
    @ApiOperation(value = "新增分类")
    public Result saveCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类: {}", categoryDTO);
        categoryService.insert(categoryDTO);
        return Result.success();
    }

}
