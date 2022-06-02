package com.sistemachamados.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sistemachamados.dtos.MinIODto;
import com.sistemachamados.models.MinIOModel;
import com.sistemachamados.repositories.MinIORepository;

@Service
public class MinIOService {
	
	@Autowired
	MinIORepository minioRepository;
	
	public MinIOService(MinIORepository minioRepository) {
		this.minioRepository = minioRepository;
	}
	
	public static String convertBase64ToImage(MinIODto minioDto) {			
		
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
			return e.toString();
		}
	}
	
	@Transactional
	public MinIOModel save(MinIOModel minioModel) {
        return minioRepository.save(minioModel);
    }
	
	@Transactional
    public void delete(MinIOModel minioModel) {
		minioRepository.delete(minioModel);
    }
	
	public boolean UploadToMinio(MinIOModel minioModel) {		
		return true;
	}
	
	public List DownloadFromMinio(MinIOModel minioModel) {		
		List myList = new ArrayList<>();
		return myList;
	}
}
