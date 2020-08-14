package com.qiniu.controller;

import com.qiniu.util.Auth;
import com.qiniu.service.UploadImageService;
import com.qiniu.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @program: SpringBoot Qiniu
 * @description:
 * @author: CodeDuck
 * @create: 2020-07-30 16:12
 **/
@Slf4j
@RestController
@RequestMapping("/qiniu")
public class UploadController {
    //密钥配置 用于下载
    Auth auth = Auth.create("************************", "************************");

    @Resource
    UploadImageService uploadImageService;

    @PostMapping(value = "/image")
    private String upLoadImage(@RequestParam("file") MultipartFile file) throws IOException {

        // 获取文件的名称
        String fileName = file.getOriginalFilename();

        // 使用工具类根据上传文件生成唯一文件名称
        String imgName = StringUtil.getRandomImgName(fileName);

        if (!file.isEmpty()) {

            FileInputStream inputStream = (FileInputStream) file.getInputStream();

            String path = uploadImageService.uploadQNImg(inputStream, imgName);
            System.out.print("七牛云返回的文件链接:" + path);
            return path;
        }
        return "上传失败";
    }



    @GetMapping(value = "/download")
    public String download(){
        //此地址需要在数据库中获得  下面只用于测试
        String targetUrl = "http://qeze7275g.hn-bkt.clouddn.com/XXXXXXXXXXXXXXXXXXxxx";
        String downloadUrl = auth.privateDownloadUrl(targetUrl);
        System.out.println(downloadUrl);
        return downloadUrl;
    }







}