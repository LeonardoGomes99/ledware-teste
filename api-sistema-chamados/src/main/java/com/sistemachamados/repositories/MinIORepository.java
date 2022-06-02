package com.sistemachamados.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemachamados.models.MinIOModel;

@Repository
public interface MinIORepository extends JpaRepository<MinIOModel, UUID>{

}
