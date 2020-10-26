package com.sjsu.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.services.S3Services;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DeleteController {
	@Autowired
	S3Services s3Service;

	@GetMapping("/s3/file/delete/{fileName}")
	public Map<String, String> deleteFile(@PathVariable String fileName) {
		this.s3Service.deleteFileFromS3Bucket(fileName);

		Map<String,String> response = new HashMap<>();
		response.put("message", "file [" + fileName + "] removing request submitted successfully.");
		return response;
	}

}
