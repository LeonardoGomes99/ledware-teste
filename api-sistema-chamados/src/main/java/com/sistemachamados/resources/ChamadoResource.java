package com.sistemachamados.resources;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemachamados.dtos.ChamadoDto;
import com.sistemachamados.dtos.UsuarioDto;
import com.sistemachamados.models.ChamadoModel;
import com.sistemachamados.models.UsuarioModel;
import com.sistemachamados.services.ChamadoService;
import com.sistemachamados.services.UsuarioService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/chamado")
public class ChamadoResource {

	final ChamadoService chamadoService;
	
	public ChamadoResource(ChamadoService chamadoService)
	{
		super();
		this.chamadoService = chamadoService;
	}
	
	@PostMapping
	public ResponseEntity<Object> saveChamado(@RequestBody @Valid ChamadoDto chamadoDto)
	{		
		var chamadoModel = new ChamadoModel();
		BeanUtils.copyProperties(chamadoDto, chamadoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(chamadoService.save(chamadoModel));
		
	}
	
	@GetMapping("/all/{id}")
    public ResponseEntity<Object> getChamadosByUserId(@PathVariable(value = "id") UUID usuarioId)
	{
        List<ChamadoModel> chamadoOptional = chamadoService.findAllByUsuarioId(usuarioId);
        if (chamadoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum Chamado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(chamadoOptional);
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteChamado(@PathVariable(value = "id") UUID id)
	{
        Optional<ChamadoModel> chamadoModelOptional = chamadoService.findById(id);
        if (!chamadoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Chamado Não Encontrado.");
        }
        chamadoService.delete(chamadoModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Chamado Excluído.");
    }
	
	@PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(
    		@PathVariable(value = "id") UUID id,
    		@RequestBody @Valid ChamadoDto chamadoDto
    )
	{
		Optional<ChamadoModel> chamadoModelOptional = chamadoService.findById(id);
        if (!chamadoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário Não Encontrado.");
        }
        var chamadoModel = new ChamadoModel();
        BeanUtils.copyProperties(chamadoDto, chamadoModel);
        chamadoModel.setId(chamadoModelOptional.get().getId());
        chamadoModel.setUsuarioId(chamadoModelOptional.get().getUsuarioId());
        return ResponseEntity.status(HttpStatus.OK).body(chamadoService.save(chamadoModel));
	}
	
}
