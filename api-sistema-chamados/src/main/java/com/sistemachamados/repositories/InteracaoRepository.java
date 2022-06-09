package com.sistemachamados.repositories;

import java.util.List;
import java.util.UUID;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemachamados.models.ChamadoModel;
import com.sistemachamados.models.InteracaoModel;

@Repository
public interface InteracaoRepository extends JpaRepository<InteracaoModel, UUID> {
	
	List <InteracaoModel> findByChamadoId(UUID chamadoId);
	
	void deleteByChamadoId(UUID chamadoId);	
}
