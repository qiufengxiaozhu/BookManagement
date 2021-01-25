package com.java.book.controller;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.book.common.ActiveUser;
import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import com.java.book.entity.User;
import com.java.book.realm.UserRealm;
import com.java.book.service.UserService;
import com.java.book.util.PinyinUtils;
import com.java.book.util.WebUtils;
import com.java.book.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author qiufeng
 * @since 2021-01-02
 */
@Api( tags = "用户管理" )
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "分页查询读者信息")
    @GetMapping("getPageUser")
    public ResultObj getPageUser(UserVo userVo) {

        // 封装page对象
        IPage<User> page = new Page<>(userVo.getPageNum(), userVo.getPageSize());

        // 封装查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 模糊匹配
        queryWrapper.like(!StringUtils.isEmpty(userVo.getName()), "name", userVo.getName());
        queryWrapper.like(!StringUtils.isEmpty(userVo.getLoginname()), "loginname", userVo.getLoginname());
        queryWrapper.like(!StringUtils.isEmpty(userVo.getTel()), "tel", userVo.getTel());
        // 等于
        queryWrapper.eq(!StringUtils.isEmpty(userVo.getFkMajorid()), "fk_majorId", userVo.getFkMajorid());
        queryWrapper.eq(!StringUtils.isEmpty(userVo.getFkGradeid()), "fk_gradeId", userVo.getFkGradeid());
        queryWrapper.eq("flag", Constant.DELETE_NOT);
        // 排序
        queryWrapper.orderByDesc("update_time");

        // 查询
        userService.page(page, queryWrapper);

        return ResultObj.ok().total(page.getTotal()).pages(page.getPages()).list( page.getRecords());
    }

    @ApiOperation( value = "删除单个读者信息" )
    @PostMapping( "deleteUser" )
    public ResultObj deleteUser(UserVo userVo) {

        try {

            // 获取选中的id值
            Integer id = userVo.getId();
            // 逻辑删除
            userVo.setFlag(Constant.DELETE_YES);
            // 删除时间
            userVo.setUpdateTime(new Date());

            userService.updateById(userVo);
            return ResultObj.ok().msg(Constant.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.DELETE_FAILURE);
        }

    }

    @ApiOperation( value = "新增学生信息" )
    @PostMapping( "addUser" )
    public ResultObj addUser(UserVo userVo) {

        try {

            if ( StringUtils.isEmpty(userVo.getName())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }
            // 上传一个默认头像
            if ( StringUtils.isEmpty(userVo.getSrc())) {
                if ( Constant.USER_SEX_MAN.equals(userVo.getSex()) ) {
                    userVo.setSrc(Constant.USER_HEADPHOTO_MAN);
                } else {
                    userVo.setSrc(Constant.USER_HEADPHOTO_WOMAN);
                }
            }

            //设置盐
            String salt= IdUtil.simpleUUID().toUpperCase();
            userVo.setSalt(salt);
            //设置密码
            userVo.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD, salt, 2).toString());

            userVo.setCreateTime(new Date());
            userVo.setUpdateTime(new Date());
            userVo.setFlag(0);

            userService.save(userVo);
            return ResultObj.ok().msg(Constant.ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.ADD_FAILURE);
        }
    }

    @ApiOperation( value = "根据用户密码认证用户身份" )
    @GetMapping( "checkUser" )
    public ResultObj checkUser(UserVo userVo) {

        try {

            // 从 session 中获取用户信息
            User user = (User) WebUtils.getSession().getAttribute("user");

            // 将用户传过来的密码结合盐值进行加密
            String newPwd = new Md5Hash(userVo.getPwd(), user.getSalt(), 2).toString();

            System.out.println("session密码：" + user.getPwd());
            System.out.println("前端传递密码：" + newPwd);

            // 认证用户身份
            if ( user.getPwd().equals(newPwd) ) {
                return ResultObj.ok().msg(Constant.AUTHENTICATION_SUCCESS);
            } else {
                return ResultObj.error().msg(Constant.AUTHENTICATION_FAILURE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.AUTHENTICATION_FAILURE);
        }
    }

    @ApiOperation( value = "修改学生信息" )
    @PostMapping( "updateUser" )
    public ResultObj updateUser(UserVo userVo) {

        try {

            if ( StringUtils.isEmpty(userVo.getName())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            // 修改学生时间
            userVo.setUpdateTime(new Date());
            boolean update = userService.updateById(userVo);

            // 从 session 中获取用户信息
            User user = (User) WebUtils.getSession().getAttribute("user");
            // 判断是否需要更新session
            if ( user.getLoginname().equals(userVo.getLoginname()) ) {
                WebUtils.getSession().setAttribute("user", userService.getById(userVo));
            }

            return ResultObj.ok().msg(Constant.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.UPDATE_FAILURE);
        }
    }

    @ApiOperation(value = "查询读者手机号是否已被注册")
    @GetMapping("checkTel")
    public ResultObj checkTel(UserVo userVo) {

        System.out.println("tel:" + userVo.getTel());

        // 封装查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 等于
        queryWrapper.eq(!StringUtils.isEmpty(userVo.getTel()), "tel", userVo.getTel());
        // 不等于自己的id
        queryWrapper.ne(!StringUtils.isEmpty(userVo.getId()), "id", userVo.getId());
        // 查询
        List<User> userList = userService.list(queryWrapper);

        if ( userList.size() > 0 ) {
            System.out.println(userList);
            return ResultObj.error().msg(Constant.PHONE_FAILURE);
        } else {

            return ResultObj.ok().msg(Constant.PHONE_SUCCESS);
        }
    }

    @ApiOperation(value = "查询读者登录名是否已被注册")
    @GetMapping("checkLoginame")
    public ResultObj checkLoginame(UserVo userVo) {

        System.out.println("loginame:" + userVo.getLoginname());
        System.out.println("ID:" + userVo.getId());

        // 封装查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 等于
        queryWrapper.eq(!StringUtils.isEmpty(userVo.getLoginname()), "loginname", userVo.getLoginname());
        // 不等于自己的id
        queryWrapper.ne(!StringUtils.isEmpty(userVo.getId()), "id", userVo.getId());
        // 查询
        List<User> userList = userService.list(queryWrapper);

        if ( userList.size() > 0 ) {
            System.out.println(userList);
            return ResultObj.error().msg(Constant.LOGINNAME_FAILURE);
        } else {

            return ResultObj.ok().msg(Constant.LOGINNAME_SUCCESS);
        }
    }

    @ApiOperation(value = "重置用户密码")
    @PostMapping("resetPwd")
    public ResultObj resetPwd(Integer id) {
        try {
            User user=new User();
            user.setId(id);
            String salt=IdUtil.simpleUUID().toUpperCase();
            //设置盐
            user.setSalt(salt);
            //设置密码
            user.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD, salt, 2).toString());
            this.userService.updateById(user);
            return ResultObj.ok().msg(Constant.RESET_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.RESET_FAILURE);
        }
    }

    @ApiOperation(value = "修改用户密码")
    @PostMapping("updatePwd")
    public ResultObj updatePwd(UserVo userVo) {
        try {

            if ( StringUtils.isEmpty(userVo.getPwd())) {
                return ResultObj.error().msg(Constant.EXCEPTION);
            }

            // 从 session 中获取用户信息
            User user = (User) WebUtils.getSession().getAttribute("user");

            String salt=IdUtil.simpleUUID().toUpperCase();
            //设置盐
            user.setSalt(salt);
            //设置密码
            user.setPwd(new Md5Hash(userVo.getPwd(), salt, 2).toString());
            this.userService.updateById(user);
            return ResultObj.ok().msg(Constant.RESET_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.RESET_FAILURE);
        }
    }

    @ApiOperation(value = "把用户名转成拼音")
    @GetMapping("changeChineseToPinyin")
    public ResultObj changeChineseToPinyin(String loginname){

        if(!StringUtils.isEmpty(loginname)) {
            return ResultObj.ok().data("loginname", PinyinUtils.getPingYin(loginname));
        }else {
            return ResultObj.ok().data("loginname", "");
        }
    }
}

