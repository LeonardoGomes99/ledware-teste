package com.sistemachamados.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemachamados.models.Usuario;
import com.sistemachamados.repositories.UsuarioRepository;

@RestController
@RequestMapping(path="/usuario")
public class UsuarioResource {

	private UsuarioRepository usuarioRepository;
	
	public UsuarioResource(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	@PostMapping("/save")
	public ResponseEntity<Usuario> save(@RequestBody Usuario usuario){
		usuarioRepository.save(usuario);
		return new ResponseEntity<>(usuario, HttpStatus.OK);
	}
}
