package com.sistemachamados.models;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "usuario")
public class UsuarioModel implements Serializable{

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
	
	@Column(name = "nome", nullable = false, length = 40)
    private String nome;
	
	@Column(name = "email", nullable = false, unique = true, length = 40)
    private String email;
	
	@Column(name = "senha", nullable = false, length = 255)
    private String senha;
	
	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
        this.id = id;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
