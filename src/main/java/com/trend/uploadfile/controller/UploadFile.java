package com.trend.uploadfile.controller;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.trend.uploadfile.util.FTPUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/app/")
public class UploadFile {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    private String host;

    private int port;

    private String userName;

    private String password;

    private String basePath;

    private String filePath;

    private String fileName;

    @PostMapping("upload")
    public String upload(MultipartFile multipartFile, HttpServletRequest request){
        String realPath = request.getSession().getServletContext().getRealPath("/uploadFile/");
        String format = simpleDateFormat.format(new Date());
        File file = new File(realPath+format);
        if(! file.isDirectory()){
            file.mkdirs();
        }
        String oldName = multipartFile.getOriginalFilename();
        //String newName = UUID.rando mUUID().toString() + oldName.substring(oldName.indexOf("."), oldName.length());
        try{
            //uploadFile存在tomcat下
            File uploadFile = new File(file, oldName);
            InputStream inputStream = new FileInputStream(uploadFile);
            boolean result = FTPUtil.uploadFile(host, port, userName, password, basePath, filePath, fileName, inputStream);
            //multipartFile.transferTo(new File(file, newName));
            //String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + format + newName;
            if(result) {
                return "success upload";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "上传失败";
    }


}
