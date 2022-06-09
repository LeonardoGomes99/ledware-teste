package com.sistemachamados.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistemachamados.models.ChamadoModel;

@Repository
public interface ChamadoRepository extends JpaRepository<ChamadoModel, UUID> {
//	@Query(value = "SELECT * FROM chamados", nativeQuery = true)			  
	List <ChamadoModel> findAllByUsuarioId(UUID usuarioId);	
}
