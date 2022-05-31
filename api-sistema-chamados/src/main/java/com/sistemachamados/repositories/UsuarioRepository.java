package com.sistemachamados.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemachamados.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

}
