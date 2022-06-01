package com.sistemachamados.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sistemachamados.models.ChamadoModel;
import com.sistemachamados.models.UsuarioModel;
import com.sistemachamados.repositories.ChamadoRepository;
import com.sistemachamados.repositories.UsuarioRepository;

@Service
public class ChamadoService {
	
	@Autowired
	ChamadoRepository chamadoRepository;
	
	public ChamadoService(ChamadoRepository chamadoRepository) {
		this.chamadoRepository = chamadoRepository;
	}
	
	@Transactional
	public ChamadoModel save(ChamadoModel chamadoModel) {
        return chamadoRepository.save(chamadoModel);
    }
	
	public Page<ChamadoModel> findAll(Pageable pageable) {
        return chamadoRepository.findAll(pageable);
    }
	
	public Optional<ChamadoModel> findById(UUID id) {
        return chamadoRepository.findById(id);
    }
	
	public List<ChamadoModel> findAllByUsuarioId(UUID usuarioId) {
        return chamadoRepository.findAllByUsuarioId(usuarioId);
    }
	
	@Transactional
    public void delete(ChamadoModel chamadoModel) {
		chamadoRepository.delete(chamadoModel);
    }

}
