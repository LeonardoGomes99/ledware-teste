package com.sistemachamados.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemachamados.models.InteracaoModel;

@Repository
public interface InteracaoRepository extends JpaRepository<InteracaoModel, UUID> {
	
}
