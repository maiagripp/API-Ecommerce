package org.serratec.dto.produto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.tomcat.util.codec.binary.Base64;
import org.serratec.models.Categoria;
import org.serratec.models.Produto;
import org.serratec.repository.ProdutoRepository;

public class ProdutoCadastroDTO {
	@NotNull
	@NotBlank
	private String nome;
	@NotNull
	@NotBlank
	private String descricao;
	@NotNull
	@NotBlank
	@Min(value = 0)
	private Double preco;
	@NotNull
	@NotBlank
	@Min(value = 0)
	private Integer quantidadeEstoque;
	@NotNull
	@NotBlank
	private Categoria categoria;
	@NotNull
	@NotBlank
	private LocalDateTime dataCadastro;
	private String imgBase64;
	
	public Produto toProduto(ProdutoRepository produtoRepository) {
		Produto p = new Produto();
		p.setNome(this.nome);
		p.setDescricao(this.descricao);
		p.setPreco(this.preco);
		p.setQuantidadeEstoque(this.quantidadeEstoque);
		p.setCategoria(this.categoria);
		p.setDataCadastro(LocalDateTime.now());
		p.setCategoria(null);
		
		if(imgBase64 != null) {
			byte[] capa = Base64.decodeBase64(imgBase64);
			p.setImagemBase64(capa);	
		}
		
		return p;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getImagemBase64() {
		return imgBase64;
	}
	public void setImagemBase64(String imagemBase64) {
		this.imgBase64 = imagemBase64;
	}
}
