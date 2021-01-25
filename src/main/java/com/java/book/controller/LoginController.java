package com.java.book.controller;

import com.java.book.common.ActiveUser;
import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import com.java.book.entity.Loginfo;
import com.java.book.service.LoginfoService;
import com.java.book.util.WebUtils;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * <p>
 * 描述: 登录控制器
 * </p>
 *
 * @author 邱高强
 * @since 2020-12-20 21:17
 */
@Api( tags = "登录管理" )
@RestController
@RequestMapping( "login" )
public class LoginController {

    @Autowired
    private LoginfoService loginfoService;

    @ApiOperation( value = "生成验证码" )
    @GetMapping( "generate" )
    public void generate(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // 默认
        CaptchaUtil.out(request, response);
    }

    @ApiOperation( value = "验证账号密码" )
    @PostMapping( "login" )
    public ResultObj login(String username, String password, String code) {

        // 是否启用登录时验证码
        if ( Constant.CODE_ACTIVE_USE ) {

            System.out.println("后端的验证码的值为：" + WebUtils.getSession().getAttribute("captcha").toString());
            System.out.println("页面上的验证码值为：" + code);
            // 如果验证码等于空
            if ( StringUtils.isEmpty(code) ) {

                return ResultObj.error().msg(Constant.CODE_IS_NULL);
            }

            String captchaCode = WebUtils.getSession().getAttribute("captcha").toString();
            // 如果验证码输入错误
            if ( !code.toLowerCase().equals(captchaCode) ) {

                return ResultObj.error().msg(Constant.CODE_IS_ERROR);
            }
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            WebUtils.getSession().setAttribute("user", activeUser.getUser());

            // 新增一条登录日志
            Loginfo loginfo = new Loginfo();
            loginfo.setLoginname(activeUser.getUser().getName() + "-" + activeUser.getUser().getLoginname());
            loginfo.setLoginip(WebUtils.getRequest().getRemoteAddr());
            loginfo.setLogintime(new Date());
            loginfoService.save(loginfo);

            return ResultObj.ok().msg(Constant.LOGIN_SUCCESS);
        } catch ( AuthenticationException e ) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.USER_PWD_ERROR);
        }
    }
}
