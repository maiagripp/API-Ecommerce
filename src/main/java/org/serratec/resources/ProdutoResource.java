package org.serratec.resources;


import java.util.List;
import java.util.Optional;

import org.serratec.dto.produto.ProdutoCadastroDTO;
import org.serratec.exception.ProdutoException;
import org.serratec.models.Produto;
import org.serratec.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProdutoResource {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@GetMapping(value = "/produto/imagem/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> getCapa (@PathVariable Long id){
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if (produto.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(produto.get().getImagemBase64(), HttpStatus.OK); 
	}
	
	@PostMapping("/produto")
	public ResponseEntity<?> postProduto(@RequestBody ProdutoCadastroDTO dto) {
		try {
			Produto produto = dto.toProduto(produtoRepository);
			produtoRepository.save(produto);
			return new ResponseEntity<>("Produto cadastrado com sucesso!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}
	
	@GetMapping("/produto/todos")
	public ResponseEntity<?> getTodos() {
		List<Produto> todos = produtoRepository.findAll();
		
		return new ResponseEntity<>(todos, HttpStatus.OK);
	}
	
	@GetMapping("/produto/{nome}")
	public ResponseEntity<?> getPorNome(@PathVariable String nome) {
		try {
			Produto produto = produtoRepository.findByNome(nome).orElseThrow(() -> new ProdutoException("Produto não encontrado"));
			return new ResponseEntity<>(produto, HttpStatus.OK);
			
		} catch (ProdutoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/produto/{id}")
	public ResponseEntity<?> putProduto(@PathVariable Long id, @RequestBody Produto novo) {
		Optional <Produto> optional = produtoRepository.findById(id);
		
		if(optional.isEmpty()) {
			return new ResponseEntity<>("Produto não encontrado" , HttpStatus.NOT_FOUND);
		}
		
		Produto existente = optional.get();
		existente.setNome(novo.getNome());
		existente.setDescricao(novo.getDescricao());
		existente.setPreco(novo.getPreco());
		existente.setQuantidadeEstoque(novo.getQuantidadeEstoque());
		existente.setImagemBase64(novo.getImagemBase64());
		
		produtoRepository.save(existente);
		
		return new ResponseEntity<>("Produto atualizado com sucesso", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/produto/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
		Optional <Produto> optional = produtoRepository.findById(id);
		
		if(optional.isEmpty()) {
			return new ResponseEntity<>("Produto não encontrado", HttpStatus.NOT_FOUND);
		}
		
		Produto existente = optional.get();
		
		produtoRepository.delete(existente);
		
		return new ResponseEntity<>("Produto deletado com sucesso", HttpStatus.OK);
	}
	
}
