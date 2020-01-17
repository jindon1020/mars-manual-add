package com.trend.uploadfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
//@ComponentScan({"com.trend.uploadfile.controller"})

@SpringBootApplication
public class UploadFileApplication {

	public static void main(String[] args) {
		SpringApplication.run(UploadFileApplication.class, args);
	}

}
