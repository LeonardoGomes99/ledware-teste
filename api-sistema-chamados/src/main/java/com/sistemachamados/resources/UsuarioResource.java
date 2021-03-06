package com.sistemachamados.resources;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.BeanUtils;

import com.sistemachamados.dtos.LoginDto;
import com.sistemachamados.dtos.UsuarioDto;
import com.sistemachamados.models.UsuarioModel;
import com.sistemachamados.repositories.UsuarioRepository;
import com.sistemachamados.services.UsuarioService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/usuario")
public class UsuarioResource {

	final UsuarioService usuarioService;

	public UsuarioResource(UsuarioService usuarioService)
	{
		super();
		this.usuarioService = usuarioService;
	}
	
	@PostMapping
	public ResponseEntity<Object> saveUsuario(@RequestBody @Valid UsuarioDto usuarioDto)
	{
		if(usuarioService.existsByEmail(usuarioDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Email já sendo Utilizado! ");
        }
		
		var usuarioModel = new UsuarioModel();
		BeanUtils.copyProperties(usuarioDto, usuarioModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuarioModel));
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<Object> loginUsuario(@RequestBody @Valid LoginDto loginDto){		

		Optional<UsuarioModel> usuarioModelOptional = usuarioService.Login(loginDto.getEmail(),loginDto.getSenha());
				
		if(!usuarioModelOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email ou Senha Incorretos");
		}
		
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioModelOptional);
	}
	
	@GetMapping
    public ResponseEntity<Page<UsuarioModel>> getAllUsuario(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable)
	{
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll(pageable));
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<Object> getOneUsuario(@PathVariable(value = "id") UUID id)
	{
        Optional<UsuarioModel> usuarioOptional = usuarioService.findById(id);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario Não Encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarioOptional.get());
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<Object> updateUsuario(
    		@PathVariable(value = "id") UUID id,
    		@RequestBody @Valid UsuarioDto usuarioDto
    )
	{
		Optional<UsuarioModel> usuarioModelOptional = usuarioService.findById(id);
        if (!usuarioModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário Não Encontrado.");
        }
        var usuarioModel = new UsuarioModel();
        BeanUtils.copyProperties(usuarioDto, usuarioModel);
        usuarioModel.setId(usuarioModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.save(usuarioModel));
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable(value = "id") UUID id)
	{
        Optional<UsuarioModel> usuarioModelOptional = usuarioService.findById(id);
        if (!usuarioModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário Não Encontrado.");
        }
        usuarioService.delete(usuarioModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Usuário Excluído.");
    }
}
