package com.sistemachamados.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		var urlArquivoLocal = MinIOService.convertBase64ToImage(minioDto);		
		if(urlArquivoLocal == "Error") {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possivel subir este arquivo para Nuvem");
		}		
		minioDto.setUrlArquivo(urlArquivoLocal);		
		
		boolean StatusUpload = minioService.UploadToMinio(minioDto);
		
		if(!StatusUpload) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possivel subir este arquivo para Nuvem");
		}
		
		var minioModel = new MinIOModel();
        BeanUtils.copyProperties(minioDto, minioModel);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(minioService.save(minioModel));       	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteArchive(@PathVariable(value = "id") UUID id)
	{
        Optional<MinIOModel> minioModelOptional = minioService.findById(id);
        
        boolean StatusDelete = minioService.deleteArchive(minioModelOptional.get());
        
        if(!StatusDelete) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Não foi possível remover o arquivo");       	
        }
		
        return ResponseEntity.status(HttpStatus.CREATED).body(StatusDelete);       	
	}
	
	@DeleteMapping("/all/{id}")
	public ResponseEntity<Object> deleteAllArchives(@PathVariable(value = "id") UUID id)
	{		
	        List<MinIOModel> minioModelOptional = minioService.findByChamadoId(id);
	        minioService.deleteAll(minioModelOptional.get(0));
	        return ResponseEntity.status(HttpStatus.CREATED).body(minioModelOptional.get(0)); 
 	        
	}


}
