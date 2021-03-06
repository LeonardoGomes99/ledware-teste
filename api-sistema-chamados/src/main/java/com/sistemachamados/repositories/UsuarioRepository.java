package com.sistemachamados.repositories;

import com.sistemachamados.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {
	boolean existsByEmail(String email);
	Optional<UsuarioModel> findByEmailAndSenha(String email, String senha);
}
