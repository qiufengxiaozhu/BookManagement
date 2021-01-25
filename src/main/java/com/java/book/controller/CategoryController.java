package com.java.book.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import com.java.book.entity.Category;
import com.java.book.entity.Loginfo;
import com.java.book.service.CategoryService;
import com.java.book.util.WebUtils;
import com.java.book.vo.CategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 书籍分类表 前端控制器
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Api(tags = "图书分类管理")
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "分页查询图书分类")
    @GetMapping("getPageCategory")
    public ResultObj getPageCategory(CategoryVo categoryVo) {

        // 封装page对象
        IPage<Category> page = new Page<>(categoryVo.getPageNum(), categoryVo.getPageSize());

        // 封装查询条件
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        // 模糊匹配
        queryWrapper.like(!StringUtils.isEmpty(categoryVo.getName()), "name", categoryVo.getName());
        // 等于
        queryWrapper.eq("flag", Constant.DELETE_NOT);
        // 排序
        queryWrapper.orderByDesc("update_time");

        // 查询
        categoryService.page(page, queryWrapper);

        return ResultObj.ok().total(page.getTotal()).list( page.getRecords());
    }

    @ApiOperation( value = "批量删除图书分类" )
    @PostMapping( "batchDeleteCategory" )
    public ResultObj batchDeleteCategory(CategoryVo categoryVo) {

        try {

            // 获取所有选中的id值
            List<Integer> idList = new ArrayList<>(Arrays.asList(categoryVo.getIds()));

            categoryService.removeByIds(idList);
            return ResultObj.ok().msg(Constant.BATCH_DEL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.BATCH_DEL_FAILURE);
        }

    }

    @ApiOperation( value = "删除单个图书分类" )
    @PostMapping( "deleteCategory" )
    public ResultObj deleteCategory(CategoryVo categoryVo) {

        try {

            // 获取选中的id值
            Integer id = categoryVo.getId();
            // 逻辑删除
            categoryVo.setFlag(Constant.DELETE_YES);
            // 删除时间
            categoryVo.setUpdateTime(new Date());

            categoryService.updateById(categoryVo);
            return ResultObj.ok().msg(Constant.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.DELETE_FAILURE);
        }

    }

    @ApiOperation( value = "新增图书分类" )
    @PostMapping( "addCategory" )
    public ResultObj addCategory(CategoryVo categoryVo) {

        try {

            if ( StringUtils.isEmpty(categoryVo.getName())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            categoryVo.setCreateTime(new Date());
            categoryVo.setUpdateTime(new Date());
            categoryVo.setFlag(0);

            categoryService.save(categoryVo);
            return ResultObj.ok().msg(Constant.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.ADD_FAILURE);
        }
    }

    @ApiOperation( value = "修改图书分类" )
    @PostMapping( "updateCategory" )
    public ResultObj updateCategory(CategoryVo categoryVo) {

        try {

            if ( StringUtils.isEmpty(categoryVo.getName())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            // 修改公告时间
            categoryVo.setUpdateTime(new Date());

            categoryService.updateById(categoryVo);
            return ResultObj.ok().msg(Constant.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.UPDATE_FAILURE);
        }
    }

}

