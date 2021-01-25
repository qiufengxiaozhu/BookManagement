package com.java.book.common;

import java.math.BigDecimal;

/**
 * 常量池
 *
 * @author 邱高强
 */
public interface Constant {

    /**
     * 返回json状态码
     */
    Integer OK = 200;
    Integer ERROR = -1;

    /**
     * 分页时，每页的默认大小
     */
    Integer PAGE_SIZE = 10;

    /**
     * 菜单权限类型对应数据库字段名
     */
    String TYPE_MENU = "menu";
    String TYPE_PERMISSION = "permission";

    /**
     * 菜单权限 0-管理员 1-普通用户
     */
    Integer MENU_PREMISSION_SUPER = 0;
    Integer MENU_PREMISSION_NOMAL = 1;

    /**
     * 菜单可用状态
     */
    Integer MENU_TRUE = 0;
    Integer MENU_FALSE = 1;

    /**
     * 用户类型
     */
    Integer USER_TYPE_SUPER = 0;
    Integer USER_TYPE_NORMAL = 1;

    /**
     * 菜单是否展开展开类型
     */
    Integer MENU_OPEN_TRUE = 1;
    Integer MENU_OPEN_FALSE = 0;

    /**
     * 一级菜单节点
     */
    Integer MENU_ROOT_NODE = 1;

    /**
     * 系统公告发布状态
     */
    Integer PUBLISH_NOT = 0;
    Integer PUBLISH_YES = 1;
    Integer PUBLISH_CANCEL = 2;

    /**
     * 已删除,未删除
     */
    Integer DELETE_NOT = 0;
    Integer DELETE_YES = 1;

    /**
     * 单天逾期金额
     */
    BigDecimal OVER_DATE_FEE = new BigDecimal("0.2");

    /**
     * 是否激活启用验证码
     */
    Boolean CODE_ACTIVE_USE = false;

    /**
     * 是否启用定时任务
     */
    Boolean START_TIME_TASK_ONE = false;
    Boolean START_TIME_TASK_TWO = false;

    /**
     * 用户默认密码
     */
    String USER_DEFAULT_PWD = "123456";

    /**
     * 罚单记录是否已归档
     */
    String TICKET_IS_FINISH = "该罚单流程已归档了，不能再修改了哦";

    /**
     * 用户默认头像
     */
    String USER_HEADPHOTO_MAN = "/upload/0a00_headPhoto_man.jpg";
    String USER_HEADPHOTO_WOMAN = "/upload/0a01_headPhoto_woman.jpg";

    /**
     * 性别编码
     */
    Integer USER_SEX_MAN = 0;
    Integer USER_SEX_WOMAN = 1;

    /**
     * 是否逾期
     */
    Integer BORROW_IS_OVERDUE = 1;
    Integer BORROW_IS_NOT_OVERDUE = 0;

    /**
     * 是否归还
     */
    Integer BORROW_IS_FINISH = 1;
    Integer BORROW_IS_NOT_FINISH = 0;

    /**
     * 成功与失败
     */
    String SUCCESS = "成功!";
    String FAILURE = "失败!";
    String EXCEPTION = "异常!";

    /**
     * 验证码值为空
     */
    String CODE_IS_NULL = "登录失败,验证码值为空！";
    String CODE_IS_ERROR = "登录失败,验证码输入错误！";

    /**
     * 用户登录
     */
    String USER_PWD_ERROR = "登录失败,用户名或密码输入错误！";
    String LOGIN_SUCCESS = "登录成功！";

    /**
     * 增删改,重置,分配，撤销，发布
     */
    String SELECT_SUCCESS = "查询成功!";
    String SELECT_FAILURE = "查询失败!";

    String ADD_SUCCESS = "新增成功!";
    String ADD_FAILURE = "新增失败!";

    String DELETE_SUCCESS = "删除成功!";
    String DELETE_FAILURE = "删除失败!";

    String BATCH_DEL_SUCCESS = "批量删除成功!";
    String BATCH_DEL_FAILURE = "批量删除失败!";

    String UPDATE_SUCCESS = "修改成功!";
    String UPDATE_FAILURE = "修改失败!";

    String RESET_SUCCESS = "重置成功!";
    String RESET_FAILURE = "重置失败!";

    String DISPATCH_SUCCESS = "分配成功!";
    String DISPATCH_FAILURE = "分配失败!";

    String CANCEL_SUCCESS = "撤销成功!";
    String CANCEL_FAILURE = "撤销失败!";

    String PUBLISH_SUCCESS = "发布成功!";
    String PUBLISH_FAILURE = "发布失败!";

    String PHONE_SUCCESS = "该手机号可用!";
    String PHONE_FAILURE = "该手机号已被注册，请更换!";

    String LOGINNAME_SUCCESS = "该登录名可用!";
    String LOGINNAME_FAILURE = "该登录名已被注册，请更换!";

    Integer BORROW_MAX_NUMBER = 5;
    String BORROW_RECORDS = "此图书已被您借阅，请勿重复借阅！";
    String BORROW_NO_RECORDS = "您没有借阅记录！";
    String BORROW_NUMBER = "每人最多同时借阅" + BORROW_MAX_NUMBER + "本书哦，需要再次借阅请将借阅的书籍归还！";

    String BOOK_RETURN_SUCCESS = "书籍归还成功！";
    String BOOK_RETURN_FAILURE = "书籍归还失败！";

    String BOOK_FINISH_SUCCESS = "书籍撤销归还成功！";
    String BOOK_FINISH_FAILURE = "书籍撤销归还失败！";

    String BORROW_SUCCESS = "借阅成功！";
    String BORROW_FAILURE = "借阅失败！";

    String AUTHENTICATION_SUCCESS = "身份认证成功！";
    String AUTHENTICATION_FAILURE = "身份认证失败！";

    String TICKET_CAN_NOT_PAY =  "该记录尚未进行归还，不支持支付罚金操作！";

    /**
     * 文件本地磁盘路径
     */
    String UPLOAD_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/static/upload/";
    /**
     * 上传到数据库的图片URL前缀
     */
    String UPLOAD_FILE_URL = "/upload/";
    /**
     * 文件上传返回信息
     */
    String UPLOAD_PATH_SUCCESS = "上传目录生成成功！";
    String UPLOAD_PATH_FAILURE = "上传目录生成失败！";
    String UPLOAD_FILE_SUCCESS = "文件上传成功！";
    String UPLOAD_FILE_FAILURE = "文件上传失败！";
}
