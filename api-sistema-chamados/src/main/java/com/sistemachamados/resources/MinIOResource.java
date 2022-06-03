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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemachamados.dtos.MinIODto;
import com.sistemachamados.models.ChamadoModel;
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
	
	@GetMapping("/all/{id}")
	public ResponseEntity<Object> getAllImages(@PathVariable(value = "id") UUID id)
	{		
		List minioModelOptional = minioService.findByChamadoId(id);	 
		var minioModel = new MinIOModel();

		int size = minioModelOptional.size();
				
		Object [] ArchiveChamadoWithUrl = new Object [size];		

        
        for (int i = 0; i < size; i++) {        	
        	BeanUtils.copyProperties(minioModelOptional.get(i), minioModel);
			ArchiveChamadoWithUrl[i] = minioService.getAllUrl(minioModel.getId());
        }

    	return ResponseEntity.status(HttpStatus.CREATED).body(ArchiveChamadoWithUrl); 
 	        
	}
	
	@PostMapping
	public ResponseEntity<Object> saveArquivo(@RequestBody @Valid MinIODto minioDto)
	{		
		var urlArquivoLocal = MinIOService.convertBase64ToArchive(minioDto);		
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
        
        minioService.removeLocalFolder(minioModel.getInteracaoId());
        
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
        int size = minioModelOptional.size();
        
        for (int i = 0; i < size; i++) {
        	minioService.deleteAllArchives(minioModelOptional.get(i));
        }
        	        	        
        return ResponseEntity.status(HttpStatus.CREATED).body("Ok"); 
 	        
	}


}
