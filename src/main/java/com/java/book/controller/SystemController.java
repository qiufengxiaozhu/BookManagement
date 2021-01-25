package com.java.book.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 描述: 系统控制器
 * </p>
 *
 * @author 邱高强
 * @since 2020-12-20 18:04
 */
@Api(tags = {"页面跳转"})
@Controller
@RequestMapping("sys")
public class SystemController {

    @ApiOperation(value = "跳转到后台登录页面")
    @GetMapping("toLogin")
    public String toLogin() {

        return "index/login";
    }

    @ApiOperation(value = "跳转到后台首页面")
    @GetMapping("toIndex")
    public String toIndex() {

        return "index/index";
    }

    @ApiOperation(value = "跳转到后台门户页面")
    @GetMapping("toMain")
    public String toMain() {

        return "index/main";
    }

    @ApiOperation(value = "跳转到新增图书信息页面")
    @GetMapping("toAddBook")
    public String toAddBook() {

        return "book/addbook";
    }

    @ApiOperation(value = "跳转到修改图书信息页面")
    @GetMapping("toUpdateBook")
    public String toUpdateBook() {

        return "book/updatebook";
    }

    @ApiOperation(value = "跳转到图书上架页面")
    @GetMapping("toPutawayBook")
    public String toPutAwayBook() {

        return "book/putawayBook";
    }

    @ApiOperation(value = "跳转到图书分类页面")
    @GetMapping("toCategory")
    public String toCategory() {

        return "book/category";
    }

    @ApiOperation(value = "跳转到出版社页面")
    @GetMapping("toPublisher")
    public String toPublisher() {

        return "book/publisher";
    }

    @ApiOperation(value = "跳转到日志管理页面")
    @GetMapping("toLoginfo")
    public String toLoginfo() {

        return "sys/loginfo";
    }

    @ApiOperation(value = "跳转到文件上传页面")
    @GetMapping("toUploadFile")
    public String toUploadFile() {

        return "upload/uploadFile";
    }

    @ApiOperation(value = "跳转到添加读者页面")
    @GetMapping("toAddReader")
    public String toAddReader() {

        return "reader/addreader";
    }

    @ApiOperation(value = "跳转到读者信息管理页面")
    @GetMapping("toReaderInfo")
    public String toReaderInfo() {

        return "reader/readerinfo";
    }

    @ApiOperation(value = "跳转到年级信息管理页面")
    @GetMapping("toGradeInfo")
    public String toGradeInfo() {

        return "reader/gradeinfo";
    }

    @ApiOperation(value = "跳转到年级信息管理页面")
    @GetMapping("toMajorInfo")
    public String toMajorInfo() {

        return "reader/majorinfo";
    }

    @ApiOperation(value = "跳转到在线书架管理页面")
    @GetMapping("toBookCase")
    public String toBookCase() {

        return "desk/bookcase";
    }

    @ApiOperation(value = "跳转到在线阅读管理页面")
    @GetMapping("toOnlineReading")
    public String toOnlineReading() {

        return "desk/reading";
    }

    @ApiOperation(value = "跳转到借阅记录管理页面")
    @GetMapping("toBookLend")
    public String toBookLend() {

        return "desk/lending";
    }

    @ApiOperation(value = "跳转到借阅详情管理页面")
    @GetMapping("toLendDetail")
    public String toLendDetail() {

        return "lend/detail";
    }

    @ApiOperation(value = "跳转到超时记录管理页面")
    @GetMapping("toOverdue")
    public String toOverdue() {

        return "lend/detail";
    }

    @ApiOperation(value = "跳转到罚金记录管理页面")
    @GetMapping("toTicket")
    public String toTicket() {

        return "lend/ticket";
    }

    @ApiOperation(value = "跳转到个人资料管理页面")
    @GetMapping("toUserInfo")
    public String toUserInfo() {

        return "sys/userinfo";
    }

    @ApiOperation(value = "跳转到修改密码管理页面")
    @GetMapping("toChangePwd")
    public String toChangePwd() {

        return "sys/changePwd";
    }

}
