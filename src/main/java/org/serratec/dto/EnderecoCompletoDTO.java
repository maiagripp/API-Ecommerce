package org.serratec.dto;

import org.serratec.models.Endereco;
import org.springframework.web.client.RestTemplate;

public class EnderecoCompletoDTO {

	private String endereco;
	private String rua;
	private String numeroResidencia;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;

	public EnderecoCompletoDTO(Endereco ender) {

		String uri = "https://viacep.com.br/ws/" + ender.getCep() + "/json/";

		RestTemplate rest = new RestTemplate();
		EnderecoViaCepDTO viaCep = rest.getForObject(uri, EnderecoViaCepDTO.class);

		this.endereco = String.format("%s, %s", viaCep.getRua(), ender.getNumeroResidencia());
		this.complemento = ender.getComplemento();
		this.bairro = viaCep.getBairro();
		this.cidade = viaCep.getCidade();
		this.estado = viaCep.getEstado();
	}

	public String getEndereco() {
		return endereco;
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
