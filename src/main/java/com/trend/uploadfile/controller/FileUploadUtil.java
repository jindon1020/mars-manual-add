package com.trend.uploadfile.controller;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.trend.uploadfile.util.FTPUtil;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件上传接口
 */
@RestController
@RequestMapping("/trend/")
public class FileUploadUtil {

    @RequestMapping("fileUpload")
    public String fileUpload(HttpServletRequest request) throws Exception{
        String path = request.getSession().getServletContext().getRealPath("/upload/");
        System.out.println(path);
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        Map<String, List<FileItem>> listMap = upload.parseParameterMap(request);
        List<FileItem> items = listMap.get("FileItem");
        for(FileItem item : items){
            if(! item.isFormField()){
                //文件项目
                String fileName = item.getName();
                String uuid = UUID.randomUUID().toString().replace("-","");
                fileName = uuid + "_" + fileName;
                File newFile = new File(path);
                InputStream inputStream = new FileInputStream(newFile);
                boolean uploadFile = FTPUtil.uploadFile("host", 80, "root", "root", "trend/", "localPath", "filename", inputStream);
                if(uploadFile){
                    System.out.println("success");
                }
                //item.write(new File(path, fileName));
                item.delete();System.out.println("success upload");
            }
        }
        return "success";
    }

}
