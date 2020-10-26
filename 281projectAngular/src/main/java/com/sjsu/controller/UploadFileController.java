package com.sjsu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sjsu.services.S3Services;
 
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UploadFileController {
	
	@Autowired
	S3Services s3Service;
	
    @PostMapping("/s3/file/upload")
    public String uploadMultipartFile(@RequestParam("file") MultipartFile file) {
    	String keyName = file.getOriginalFilename();
    	s3Service.uploadObject(keyName, file);
		return "Upload Successfully -> KeyName = " + keyName;
    }    
}