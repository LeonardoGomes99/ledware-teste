package com.sistemachamados;

import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

@SpringBootApplication
@RestController
public class ApiSistemaChamadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSistemaChamadosApplication.class, args);
	}
	
	@GetMapping("/")
	public static void index() {
		
	}
	
	@PostMapping("/minio")
	public ResponseEntity<Object> postImage(@RequestBody Json json) {
		String crntImage = json.getImageBase64();
		//String crntImage = "asasasas";

		try (OutputStream stream = new FileOutputStream("src\\main\\resources\\static\\Image3.png")) {
			byte[] data = Base64.getDecoder().decode(crntImage);
		    stream.write(data);
			return ResponseEntity.status(HttpStatus.OK).body("Upload Realizado com Sucesso");
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.OK).body("Erro ao Realizar Upload de Imagem");
		}
		
	}
	
}
