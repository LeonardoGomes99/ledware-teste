package com.sistemachamados.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.net.*;  

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sistemachamados.dtos.MinIODto;
import com.sistemachamados.models.MinIOModel;
import com.sistemachamados.models.UsuarioModel;
import com.sistemachamados.repositories.MinIORepository;

import io.minio.*;
import io.minio.http.Method;

@Service
public class MinIOService {
	
	@Autowired
	MinIORepository minioRepository;
	String LocalDoArquivo;
	
	public MinIOService(MinIORepository minioRepository) {
		this.minioRepository = minioRepository;
	}
	
	public static String convertBase64ToArchive(MinIODto minioDto) {			
		
		String arquivo = minioDto.getArquivo();
		String nomeArquivo = minioDto.getNomeArquivo();
		String arquivoTipo = minioDto.getTipoArquivo();
		UUID interacaoId = minioDto.getInteracaoId();
		String urlLocal = "src\\main\\resources\\static";
		
		String ArquivoLocal = String.format("%s\\%s\\%s.%s", urlLocal,interacaoId,nomeArquivo,arquivoTipo);
		new File(String.format("src\\main\\resources\\static\\%s", interacaoId)).mkdirs();
		try (OutputStream stream = new FileOutputStream(ArquivoLocal)) {
			byte[] data = Base64.getDecoder().decode(arquivo);
		    stream.write(data);
			return ArquivoLocal;
		}catch(Exception e){
			return "Error";
		}
	}
	
	@Transactional
	public MinIOModel save(MinIOModel minioModel) {
        return minioRepository.save(minioModel);
    }	
	
	public static void removeLocalFolder(UUID folderLocation) {
        String arquivoDeleteStatic = String.format("src\\main\\resources\\static\\%s", folderLocation.toString());
        File index = new File(arquivoDeleteStatic);
        String[]entries = index.list();
        for(String s: entries){
            File currentFile = new File(index.getPath(),s);
            currentFile.delete();
        }
        index.delete();
	}
	
	public boolean UploadToMinio(MinIODto minioDto) {
	  String nomeBucket = "interacoes";
	  try {
          MinioClient minioClient =
              MinioClient.builder()
                  .endpoint("http://127.0.0.1:9000")
                  .credentials("minioadmin", "minioadmin")
                  .build();

          boolean found =
              minioClient.bucketExists(BucketExistsArgs.builder().bucket(nomeBucket).build());
          if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(nomeBucket).build());
            System.out.println("Bucket 'interacoes' Criado");

          }
          String urlArquivo = minioDto.getUrlArquivo();
          String folderMinio = String.format("%s/%s/%s/", minioDto.getUsuarioId().toString(), minioDto.getChamadoId().toString(), minioDto.getInteracaoId().toString() );
          String arquivoToMinio = String.format("%s.%s", minioDto.getNomeArquivo().toString(), minioDto.getTipoArquivo().toString());
                     
          minioClient.uploadObject(
        		  UploadObjectArgs.builder()
				          .bucket(nomeBucket)
				          .object(folderMinio+arquivoToMinio)
				          .filename(urlArquivo)
				          .build());
          
          minioDto.setUrlArquivo(folderMinio+arquivoToMinio);          
          System.out.println("Upload Realizado com Sucesso");
  		  return true;
        } catch (Exception e) {
          System.out.println("Upload de arquivo : " + e);
          return false;
        }
	}
	
	public Optional<MinIOModel> findById(UUID id) {
        return minioRepository.findById(id);

    }
	
	public List<MinIOModel> findByChamadoId(UUID id) {
        return minioRepository.findByChamadoId(id);
    }
	

	public String[] getAllUrl(UUID id) {
		String nomeBucket = "interacoes";

		Optional<MinIOModel> minioRequest = minioRepository.findById(id);
		var minioModel = new MinIOModel();
    	BeanUtils.copyProperties(minioRequest.get(), minioModel);
				
        String [] ArchiveChamadoWithUrl = new String [8];
        
        ArchiveChamadoWithUrl[0] = minioModel.getId().toString();
        ArchiveChamadoWithUrl[1] = minioModel.getNomeArquivo();
        ArchiveChamadoWithUrl[2] = minioModel.getTipoArquivo();
        ArchiveChamadoWithUrl[3] = minioModel.getUrlArquivo();
        ArchiveChamadoWithUrl[4] = minioModel.getInteracaoId().toString();
        ArchiveChamadoWithUrl[5] = minioModel.getChamadoId().toString();
        ArchiveChamadoWithUrl[6] = minioModel.getUsuarioId().toString(); 
        
        try {
	        MinioClient minioClient =
		              MinioClient.builder()
		                  .endpoint("http://127.0.0.1:9000")
		                  .credentials("minioadmin", "minioadmin")
		                  .build();
	        
	        ArchiveChamadoWithUrl[7] = minioClient.getPresignedObjectUrl(
	                GetPresignedObjectUrlArgs.builder()
	                .method(Method.GET)
	                .bucket(nomeBucket)
	                .object(minioModel.getUrlArquivo().toString())
	                .expiry(2, TimeUnit.HOURS)             
	                .build());
	        
	        return ArchiveChamadoWithUrl;

        }catch(Exception e) {
        	ArchiveChamadoWithUrl[7] = "Not Found";
	        return ArchiveChamadoWithUrl;
        }
        


    }			
       
	
	@Transactional
    public boolean deleteArchive(MinIOModel minioModel) {	
		String nomeBucket = "interacoes";
		try {
			MinioClient minioClient =
		              MinioClient.builder()
		                  .endpoint("http://127.0.0.1:9000")
		                  .credentials("minioadmin", "minioadmin")
		                  .build();
			minioClient.removeObject(RemoveObjectArgs.builder().bucket(nomeBucket).object(minioModel.getUrlArquivo().toString()).build());	
			minioRepository.delete(minioModel);
			return true;
		}catch(Exception e) {
			return false;
		}		
    }	
		
	@Transactional
	public void deleteAllArchives( MinIOModel minioModel) {		
		String nomeBucket = "interacoes";
		try {
			MinioClient minioClient =
		              MinioClient.builder()
		                  .endpoint("http://127.0.0.1:9000")
		                  .credentials("minioadmin", "minioadmin")
		                  .build();
			minioClient.removeObject(RemoveObjectArgs.builder().bucket(nomeBucket).object(minioModel.getUrlArquivo()).build());	
			minioClient.removeObject(RemoveObjectArgs.builder().bucket(nomeBucket).object(minioModel.getUsuarioId()+"/"+minioModel.getChamadoId()+"/"+minioModel.getInteracaoId()).build());
			minioClient.removeObject(RemoveObjectArgs.builder().bucket(nomeBucket).object(minioModel.getUsuarioId()+"/"+minioModel.getChamadoId()).build());	

			minioRepository.delete(minioModel);
			System.out.println("Arquivo " + minioModel.getId());

		}catch(Exception e) {
		}	
    }
}
