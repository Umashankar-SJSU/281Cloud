package com.sjsu.services.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.sjsu.entity.Users;
import com.sjsu.repository.UsersRepository;
import com.sjsu.services.S3Services;

@Service
public class S3ServicesImpl implements S3Services {

	private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);

	@Autowired
	private AmazonS3 s3;

	@Autowired
	private UsersRepository repo;

	@Value("${s3Bucket.bucket}")
	private String bucket;

	@Override
	public ByteArrayOutputStream downloadObject(String fileName) {
		try {
			S3Object object = s3.getObject(new GetObjectRequest(bucket, fileName));

			InputStream inStream = object.getObjectContent();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			int length;
			byte[] b = new byte[8192];
			while ((length = inStream.read(b, 0, b.length)) != -1) {
				outputStream.write(b, 0, length);
			}

			return outputStream;
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
		}

		return null;
	}

	@Override
	public void uploadObject(String fileName, MultipartFile file) {
		try {
			ObjectMetadata data = new ObjectMetadata();
			data.setContentLength(file.getSize());
			PutObjectResult res = s3.putObject(bucket, fileName, file.getInputStream(), data);
			System.out.println("uploaded object is -> " + res.toString());

			repo.save(new Users("George", "kirsten", new Date(), new Date(), "Test File"));
		} catch (Exception e) {
			logger.error("Exception: " + e.getMessage());
		}
	}

	public List<String> filesList() {

		ListObjectsRequest request = new ListObjectsRequest().withBucketName(bucket);
		List<String> allFiles = new ArrayList<>();
		ObjectListing objects = s3.listObjects(request);

		while (true) {
			List<S3ObjectSummary> list = objects.getObjectSummaries();
			if (list.size() < 1) {
				break;
			}

			for (S3ObjectSummary item : list) {
				if (!item.getKey().endsWith("/"))
					allFiles.add(item.getKey());
			}

			objects = s3.listNextBatchOfObjects(objects);
		}

		return allFiles;
	}

	@Override
	public void deleteFileFromS3Bucket(String fileName) {
		try {
			s3.deleteObject(new DeleteObjectRequest(bucket, fileName));
		} catch (AmazonServiceException ex) {
			logger.error("error [" + ex.getMessage() + "] occurred while removing [" + fileName + "] ");

		}
	}
}
