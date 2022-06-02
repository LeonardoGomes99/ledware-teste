package com.sistemachamados.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemachamados.dtos.MinIODto;
import com.sistemachamados.models.MinIOModel;
import com.sistemachamados.services.MinIOService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/MinIO")
public class MinIOResource {
	
	final MinIOService minioService;
	
	public MinIOResource(MinIOService minioService)
	{
		super();
		this.minioService = minioService;
	}
	
	@PostMapping
	public ResponseEntity<Object> saveArquivo(@RequestBody @Valid MinIODto minioDto)
	{		
		var Variavel = MinIOService.convertBase64ToImage(minioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body((Variavel));
        //return ResponseEntity.status(HttpStatus.CREATED).body((minioDto));
//		var minioModel = new MinIOModel();
//		BeanUtils.copyProperties(minioDto, minioModel);
//        return ResponseEntity.status(HttpStatus.CREATED).body(minioService.save(minioModel));
		
	}


}
