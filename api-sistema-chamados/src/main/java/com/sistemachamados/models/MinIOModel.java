package com.sistemachamados.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "interacoesarquivos")
public class MinIOModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
	
	@Column(name = "nome_arquivo", nullable = false, length = 255)
    private String nomeArquivo;
	
	@Column(name = "tipo_arquivo", nullable = false, length = 255)
    private String tipoArquivo;
	
	@Column(name = "url_arquivo", nullable = false, length = 255)
    private String urlArquivo;
	
	@Column(name = "interacao_id")
	@Type(type="org.hibernate.type.UUIDCharType")
    private UUID interacaoId;
	
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

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}

	public UUID getInteracaoId() {
		return interacaoId;
	}

	public void setInteracaoId(UUID interacaoId) {
		this.interacaoId = interacaoId;
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
