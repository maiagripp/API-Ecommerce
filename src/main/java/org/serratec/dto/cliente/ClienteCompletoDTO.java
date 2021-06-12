package org.serratec.dto.cliente;

import java.time.LocalDate;

import org.serratec.dto.EnderecoCompletoDTO;
import org.serratec.models.Cliente;

public class ClienteCompletoDTO {
	
	private String email;
	private String username;
	private String nome;
	private String cpf;
	private String telefone;
	private LocalDate dataNascimento;
	private EnderecoCompletoDTO endereco;
	
	public ClienteCompletoDTO(Cliente cliente) {
		this.email = cliente.getEmail();
		this.username = cliente.getUsername();
		this.nome = cliente.getNome();
		this.cpf = cliente.getCPF();
		this.telefone = cliente.getTelefone();
		this.dataNascimento = cliente.getDataNascimento();
		this.endereco = new EnderecoCompletoDTO(cliente.getEndereco());
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public EnderecoCompletoDTO getEndereco() {
		return endereco;
	}
}
