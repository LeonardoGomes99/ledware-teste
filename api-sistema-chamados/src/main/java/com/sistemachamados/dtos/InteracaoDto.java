package com.sistemachamados.dtos;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class InteracaoDto {
	
	@NotBlank
	private String descricao;
	
	@NotBlank
	private String data;
	
	@NotNull
	private UUID chamadoId;
	
	@NotNull
	private UUID usuarioId;

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
