package com.sistemachamados.models;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "interacoes")
public class InteracaoModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
	
	@Column(name = "descricao", nullable = false, length = 255)
    private String descricao;
	
	@Column(name = "data", nullable = false, length = 255)
    private String data;
	
	@Column(name = "chamado_id")
	@Type(type="org.hibernate.type.UUIDCharType")
    private UUID chamadoId;
	
	@Column(name = "usuario_id")
	@Type(type="org.hibernate.type.UUIDCharType")
    private UUID usuarioId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public UUID getChamadoId() {
		return chamadoId;
	}

	public void setChamadoId(UUID chamadoId) {
		this.chamadoId = chamadoId;
	}

	public UUID getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(UUID usuarioId) {
		this.usuarioId = usuarioId;
	}  
	
	
}
