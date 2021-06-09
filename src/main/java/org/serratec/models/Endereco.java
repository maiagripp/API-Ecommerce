package org.serratec.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cep;
	private String rua;
	private String numeroResidencia;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	
	public Long getId() {
		return id;
	}
	public String getCep() {
		return cep;
	}
	public String getRua() {
		return rua;
	}
	public String getNumeroResidencia() {
		return numeroResidencia;
	}
	public String getComplemento() {
		return complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public String getEstado() {
		return estado;
	}
	
	
	
}
