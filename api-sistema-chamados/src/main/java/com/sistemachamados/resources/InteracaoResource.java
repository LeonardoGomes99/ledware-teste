package com.sistemachamados.resources;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemachamados.dtos.ChamadoDto;
import com.sistemachamados.dtos.InteracaoDto;
import com.sistemachamados.models.ChamadoModel;
import com.sistemachamados.models.InteracaoModel;
import com.sistemachamados.models.UsuarioModel;
import com.sistemachamados.services.InteracaoService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/interacao")
public class InteracaoResource {
	
	final InteracaoService interacaoService;

	public InteracaoResource(InteracaoService interacaoService)
	{
		super();
		this.interacaoService = interacaoService;
	}
	
	@PostMapping
	public ResponseEntity<Object> saveInteracao(@RequestBody InteracaoDto interacaoDto ){
		var interacaoModel = new InteracaoModel();
		BeanUtils.copyProperties(interacaoDto, interacaoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(interacaoService.save(interacaoModel));
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<Object> getOneUsuario(@PathVariable(value = "id") UUID id)
	{
        Optional<InteracaoModel> InteracaoOptional = interacaoService.findById(id);
        if (!InteracaoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Interação Não Encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(InteracaoOptional.get());
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<Object> updateInteracao(
    		@PathVariable(value = "id") UUID id,
    		@RequestBody @Valid InteracaoDto interacaoDto
    )
	{
		Optional<InteracaoModel> interacaoModelOptional = interacaoService.findById(id);
        if (!interacaoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Interação Não Encontrado.");
        }
        var interacaoModel = new InteracaoModel();
        BeanUtils.copyProperties(interacaoDto, interacaoModel);
        interacaoModel.setId(interacaoModelOptional.get().getId());
        interacaoModel.setChamadoId(interacaoModelOptional.get().getChamadoId());
        interacaoModel.setUsuarioId(interacaoModelOptional.get().getUsuarioId());
        return ResponseEntity.status(HttpStatus.OK).body(interacaoService.save(interacaoModel));
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteInteracao(@PathVariable(value = "id") UUID id)
	{
        Optional<InteracaoModel> interacaoModelOptional = interacaoService.findById(id);
        if (!interacaoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Interação Não Encontrado.");
        }
        interacaoService.delete(interacaoModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Interação Excluída.");
    }
	
	

}
