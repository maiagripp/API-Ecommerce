package org.serratec.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	private String username;
	
	private String senha;
	
	private String nome;
	
	private String CPF;
	
	private String telefone;
	
	private LocalDate dataNascimento;
	
	@OneToOne
	private Endereco endereco;

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getSenha() {
		return senha;
	}

	public String getNome() {
		return nome;
	}

	public String getCPF() {
		return CPF;
	}

	public String getTelefone() {
		return telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public Endereco getEndereco() {
		return endereco;
	}
	
	
}
