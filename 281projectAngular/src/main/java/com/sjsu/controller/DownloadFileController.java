package com.sjsu.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.services.S3Services;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DownloadFileController {
 	
	@Autowired
	S3Services s3Service;
	 	
    /*
     * Download Files
     */
	@GetMapping("/s3/file/{fileName}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
		ByteArrayOutputStream downloadInputStream = s3Service.downloadObject(fileName);
	
		return ResponseEntity.ok()
					.contentType(contentType(fileName))
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + fileName + "\"")
					.body(downloadInputStream.toByteArray());	
	}
	
	/*
	 * List ALL Files
	 */
	@GetMapping("/s3/files")
	public List<String> listAllFiles(){
		return s3Service.filesList();
	}
	
	private MediaType contentType(String keyname) {
		String[] arr = keyname.split("\\.");
		String type = arr[arr.length-1];
		switch(type) {
			case "txt": return MediaType.TEXT_PLAIN;
			case "png": return MediaType.IMAGE_PNG;
			case "jpg": return MediaType.IMAGE_JPEG;
			default: return MediaType.APPLICATION_OCTET_STREAM;
		}
	}
}
