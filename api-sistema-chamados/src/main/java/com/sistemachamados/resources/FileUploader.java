package com.sistemachamados.resources;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import io.minio.BucketExistsArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import io.minio.*;

public class FileUploader {
	
	public static void main(String[] args) {
		
		try {
			String nomeBucket = "interacoes";
			String object = "FOLDER/download.jpg";
			String objectFolder = "56a44e49-05b6-4fb4-8f58-8a4ec459dbe7";
			String objectArchive = String.format("%s/%s","56a44e49-05b6-4fb4-8f58-8a4ec459dbe7" , "bbe9cb45-8c58-494b-bf46-9824282fb7b1");
			MinioClient minioClient =
		              MinioClient.builder()
		                  .endpoint("http://127.0.0.1:9000")
		                  .credentials("minioadmin", "minioadmin")
		                  .build();
			
			
			List<DeleteObject> objects = new LinkedList<>();
			objects.add(new DeleteObject("FOLDER"));			
			Iterable<Result<DeleteError>> results =
			    minioClient.removeObjects(
			        RemoveObjectsArgs.builder().bucket(nomeBucket).objects(objects).build());
			for (Result<DeleteError> result : results) {
			  DeleteError error = result.get();
			  System.out.println(
			      "Error in deleting object " + error.objectName() + "; " + error.message());
			}
			
			
			
			
			System.out.print("Foi");
		}catch(Exception e) {
			System.out.print("Deu Erro");
		}
		    
	}
}
