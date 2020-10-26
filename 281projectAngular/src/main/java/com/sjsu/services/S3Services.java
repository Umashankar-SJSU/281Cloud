package com.sjsu.services;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface S3Services {
	public ByteArrayOutputStream downloadObject(String fileName);
	public void uploadObject(String fileName, MultipartFile file);
	public List<String> filesList();
	public void deleteFileFromS3Bucket(String fileName);
}