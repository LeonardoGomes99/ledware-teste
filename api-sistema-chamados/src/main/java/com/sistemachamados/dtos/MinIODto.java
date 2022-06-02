package com.sistemachamados.dtos;

import java.util.Base64;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MinIODto {

	@NotBlank
	private String nomeArquivo;

	@NotBlank
	private String tipoArquivo;
	
	@NotBlank
	private String urlArquivo;

	@NotNull
	private UUID interacaoId;
	
	@NotNull
	private UUID chamadoId;
	
	@NotNull
	private UUID usuarioId;
	
	@NotNull
	private String arquivo;

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
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
