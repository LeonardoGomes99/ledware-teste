package com.sistemachamados.resources;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;

public class FileUploader {
	
	public static void main(String[] args)
		    throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		    try {
		      // Create a minioClient with the MinIO server playground, its access key and secret key.
		      MinioClient minioClient =
		          MinioClient.builder()
		              .endpoint("http://localhost:9000")
		              .credentials("minioadmin", "minioadmin")
		              .build();

		      boolean found =
		          minioClient.bucketExists(BucketExistsArgs.builder().bucket("interacao").build());
		      if (!found) {
		        minioClient.makeBucket(MakeBucketArgs.builder().bucket("interacao").build());
		        System.out.println("Bucket 'interacao' Criado");

		      }
		      
		      String image = "";
		      

		      minioClient.uploadObject(
		          UploadObjectArgs.builder()
		              .bucket("interacao")
		              .object("test.jpg")
		              .filename(image)
		              .build());
		      System.out.println(
		          "'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
		              + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");
		    } catch (MinioException e) {
		      System.out.println("Error occurred: " + e);
		      System.out.println("HTTP trace: " + e.httpTrace());
		    }
	}
}
