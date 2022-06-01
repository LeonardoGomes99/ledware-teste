package com.sistemachamados.services;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sistemachamados.models.UsuarioModel;
import com.sistemachamados.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Transactional
	public UsuarioModel save(UsuarioModel usuarioModel) {
        return usuarioRepository.save(usuarioModel);
    }
	
	public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
	
	public Page<UsuarioModel> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }
	
	public Optional<UsuarioModel> findById(UUID id) {
        return usuarioRepository.findById(id);
    }
	
	@Transactional
    public void delete(UsuarioModel usuarioModel) {
		usuarioRepository.delete(usuarioModel);
    }
	
	
}
