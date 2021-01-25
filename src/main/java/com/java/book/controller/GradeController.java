package com.java.book.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import com.java.book.entity.Category;
import com.java.book.entity.Grade;
import com.java.book.service.CategoryService;
import com.java.book.service.GradeService;
import com.java.book.vo.CategoryVo;
import com.java.book.vo.GradeVo;
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
 * 年级表 前端控制器
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-05
 */
@Api(tags = "年级管理")
@RestController
@RequestMapping("grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @ApiOperation(value = "分页查询年级信息")
    @GetMapping("getPageGrade")
    public ResultObj getPageGrade(GradeVo gradeVo) {

        // 封装page对象
        IPage<Grade> page = new Page<>(gradeVo.getPageNum(), gradeVo.getPageSize());

        // 封装查询条件
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        // 模糊匹配
        queryWrapper.like(!StringUtils.isEmpty(gradeVo.getGrade()), "grade", gradeVo.getGrade());
        // 等于
        queryWrapper.eq("flag", Constant.DELETE_NOT);
        // 排序
        queryWrapper.orderByDesc("update_time");

        // 查询
        gradeService.page(page, queryWrapper);

        return ResultObj.ok().total(page.getTotal()).list( page.getRecords());
    }

    @ApiOperation( value = "批量删除年级信息" )
    @PostMapping( "batchDeleteGrade" )
    public ResultObj batchDeleteGrade(GradeVo gradeVo) {

        try {

            // 获取所有选中的id值
            List<Integer> idList = new ArrayList<>(Arrays.asList(gradeVo.getIds()));

            gradeService.removeByIds(idList);
            return ResultObj.ok().msg(Constant.BATCH_DEL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.BATCH_DEL_FAILURE);
        }

    }

    @ApiOperation( value = "删除单个年级信息" )
    @PostMapping( "deleteGrade" )
    public ResultObj deleteGrade(GradeVo gradeVo) {

        try {

            // 获取选中的id值
            Integer id = gradeVo.getId();
            // 逻辑删除
            gradeVo.setFlag(Constant.DELETE_YES);
            // 删除时间
            gradeVo.setUpdateTime(new Date());

            gradeService.updateById(gradeVo);
            return ResultObj.ok().msg(Constant.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.DELETE_FAILURE);
        }

    }

    @ApiOperation( value = "新增年级信息" )
    @PostMapping( "addGrade" )
    public ResultObj addGrade(GradeVo gradeVo) {

        try {

            if ( StringUtils.isEmpty(gradeVo.getGrade())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            gradeVo.setCreateTime(new Date());
            gradeVo.setUpdateTime(new Date());
            gradeVo.setFlag(0);

            gradeService.save(gradeVo);
            return ResultObj.ok().msg(Constant.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.ADD_FAILURE);
        }
    }

    @ApiOperation( value = "修改年级信息" )
    @PostMapping( "updateGrade" )
    public ResultObj updateGrade(GradeVo gradeVo) {

        try {

            if ( StringUtils.isEmpty(gradeVo.getGrade())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            // 修改公告时间
            gradeVo.setUpdateTime(new Date());

            gradeService.updateById(gradeVo);
            return ResultObj.ok().msg(Constant.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.UPDATE_FAILURE);
        }
    }
}

