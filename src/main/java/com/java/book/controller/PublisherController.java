package com.java.book.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import com.java.book.entity.Category;
import com.java.book.entity.Publisher;
import com.java.book.service.PublisherService;
import com.java.book.vo.CategoryVo;
import com.java.book.vo.PublisherVo;
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
 * 出版社信息表 前端控制器
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Api(tags = "出版社管理")
@RestController
@RequestMapping("publisher")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @ApiOperation(value = "分页查询出版社")
    @GetMapping("getPagePublisher")
    public ResultObj getPagePublisher(PublisherVo publisherVo) {

        // 封装page对象
        IPage<Publisher> page = new Page<>(publisherVo.getPageNum(), publisherVo.getPageSize());

        // 封装查询条件
        QueryWrapper<Publisher> queryWrapper = new QueryWrapper<>();
        // 模糊匹配
        queryWrapper.like(!StringUtils.isEmpty(publisherVo.getName()), "name", publisherVo.getName());
        // 等于
        queryWrapper.eq("flag", Constant.DELETE_NOT);
        // 排序
        queryWrapper.orderByDesc("update_time");

        // 查询
        publisherService.page(page, queryWrapper);

        return ResultObj.ok().total(page.getTotal()).list( page.getRecords());
    }

    @ApiOperation( value = "批量删除出版社" )
    @PostMapping( "batchDeletePublisher" )
    public ResultObj batchDeletePublisher(PublisherVo publisherVo) {

        try {

            // 获取所有选中的id值
            List<Integer> idList = new ArrayList<>(Arrays.asList(publisherVo.getIds()));

            publisherService.removeByIds(idList);
            return ResultObj.ok().msg(Constant.BATCH_DEL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.BATCH_DEL_FAILURE);
        }

    }

    @ApiOperation( value = "删除单个出版社" )
    @PostMapping( "deletePublisher" )
    public ResultObj deletePublisher(PublisherVo publisherVo) {

        try {

            // 获取选中的id值
            Integer id = publisherVo.getId();
            // 逻辑删除
            publisherVo.setFlag(Constant.DELETE_YES);
            // 删除时间
            publisherVo.setUpdateTime(new Date());

            publisherService.updateById(publisherVo);
            return ResultObj.ok().msg(Constant.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.DELETE_FAILURE);
        }

    }

    @ApiOperation( value = "新增出版社" )
    @PostMapping( "addPublisher" )
    public ResultObj addPublisher(PublisherVo publisherVo) {

        try {

            if ( StringUtils.isEmpty(publisherVo.getName())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            publisherVo.setCreateTime(new Date());
            publisherVo.setUpdateTime(new Date());
            publisherVo.setFlag(0);

            publisherService.save(publisherVo);
            return ResultObj.ok().msg(Constant.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.ADD_FAILURE);
        }
    }

    @ApiOperation( value = "修改出版社" )
    @PostMapping( "updatePublisher" )
    public ResultObj updatePublisher(PublisherVo publisherVo) {

        try {

            if ( StringUtils.isEmpty(publisherVo.getName())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            // 修改公告时间
            publisherVo.setUpdateTime(new Date());

            publisherService.updateById(publisherVo);
            return ResultObj.ok().msg(Constant.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.UPDATE_FAILURE);
        }
    }
}

