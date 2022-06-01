package com.sistemachamados.services;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemachamados.models.ChamadoModel;
import com.sistemachamados.models.InteracaoModel;
import com.sistemachamados.models.UsuarioModel;
import com.sistemachamados.repositories.InteracaoRepository;

@Service
public class InteracaoService {
	
	@Autowired
	InteracaoRepository interacaoRepository;
	
	public InteracaoService(InteracaoRepository interacaoRepository) {
		this.interacaoRepository = interacaoRepository;
	}
	
	@Transactional
	public InteracaoModel save(InteracaoModel interacaoModel) {
        return interacaoRepository.save(interacaoModel);
    }
	
	public Optional<InteracaoModel> findById(UUID id) {
        return interacaoRepository.findById(id);
    }
	
	@Transactional
    public void delete(InteracaoModel interacaoModel) {
		interacaoRepository.delete(interacaoModel);
    }

}
