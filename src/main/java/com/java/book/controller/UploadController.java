package com.java.book.controller;

import com.java.book.common.Constant;
import com.java.book.common.ResultObj;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传
 *
 * @author 邱高强
 */
@Api( tags = "文件上传" )
@RestController
@RequestMapping( "upload" )
public class UploadController {

    @ApiOperation( value = "layedit 富文本 图片上传  --保存到本地" )
    @PostMapping( "uploadImage" )
    public ResultObj uploadImage(@Param( "file" ) MultipartFile file) throws IOException {


        // 上传后的保存路径,即本地磁盘路径
        String filePath = Constant.UPLOAD_FILE_PATH;
        // 上传文件名，原始文件名
        String fileName = file.getOriginalFilename();
        // 上传文件后缀名
        assert fileName != null;
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 新文件名
        String newFileName = UUID.randomUUID() + suffixName;

        try {
            // 生成upload目录，真实磁盘保存路径
            File dest = new File(filePath + newFileName);
            if ( !dest.getParentFile().exists() ) {
                if ( !dest.getParentFile().mkdirs() ) {
                    return ResultObj.error().msg(Constant.UPLOAD_PATH_FAILURE);
                }
            }

            file.transferTo(dest);

            //本地目录和生成的文件名拼接，这一段存入数据库，图片路径
            newFileName = Constant.UPLOAD_FILE_URL + newFileName;
            /*
                code : 0      //0表示成功，其它失败
                msg  : ""     //提示信息,一般上传失败后返回
                data : {
                    src :     //"图片路径" ,存入数据库的URL
                    title :   //"图片名称" ,可选,这个会显示在输入框里
                }
             */
            return ResultObj.ok().code(0).msg(Constant.UPLOAD_FILE_SUCCESS).data("src", newFileName).data("title", fileName);
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResultObj.error().msg(Constant.UPLOAD_FILE_FAILURE);
        }
    }

    @ApiOperation( value = "layedit 富文本 图片上传  --本地不会留痕" )
    @PostMapping( "uploadFileDemo" )
    public Map<String, Object> uploadFile(HttpServletRequest request, @Param( "file" ) MultipartFile file) throws IOException {

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        if ( file != null ) {
            String webapp = request.getServletContext().getRealPath("/");
            try {
                String substring = file.getOriginalFilename();
                // 图片的路径+文件名称
                String fileName = "/static/upload/" + substring;
                System.out.println(fileName);
                // 图片的在服务器上面的物理路径
                File destFile = new File(webapp, fileName);
                // 生成upload目录
                File parentFile = destFile.getParentFile();
                if ( !parentFile.exists() ) {
                    parentFile.mkdirs();// 自动生成upload目录
                }
                // 把上传的临时图片，复制到当前项目的webapp路径
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(destFile));
                map = new HashMap<>();
                map2 = new HashMap<>();
                map.put("code", 0);//0表示成功，1失败
                map.put("msg", "上传成功");//提示消息
                map.put("data", map2);
                map2.put("src", fileName);//图片url
                map2.put("title", substring);//图片名称，这个会显示在输入框里
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        return map;
    }

}

