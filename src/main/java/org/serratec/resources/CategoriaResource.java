package org.serratec.resources;

import java.util.List;
import java.util.Optional;

import org.serratec.exception.CategoriaException;
import org.serratec.models.Categoria;
import org.serratec.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriaResource {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	@PostMapping("/categoria")
	public ResponseEntity<?> postCategoria(@RequestBody Categoria categoria){
		categoriaRepository.save(categoria);
		
		return new ResponseEntity<>("Cadastrado com sucesso", HttpStatus.OK);
	}
	
	@GetMapping("/categoria/todas")
	public ResponseEntity<?> getTodos(){
		List<Categoria> todos = categoriaRepository.findAll();
		
		return new ResponseEntity<>(todos, HttpStatus.OK);
	}
	
	@GetMapping("/categoria/{nome}")
	public ResponseEntity<?> getPorNome(@PathVariable String nome){
		try {
			Categoria categoria = categoriaRepository.findByNome(nome).orElseThrow(() -> new CategoriaException("Categoria não encontrada"));
			return new ResponseEntity<>(categoria, HttpStatus.OK);
			
		} catch (CategoriaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	@PutMapping("/categoria/{id}")
	public ResponseEntity<?> putCategoria(@PathVariable Long id, @RequestBody Categoria novo) {
		Optional<Categoria> optional = categoriaRepository.findById(id);
		 
		if(optional.isEmpty()){
			return new ResponseEntity<>("Categoria não encontrada", HttpStatus.NOT_FOUND);
		}
		
		Categoria existente = optional.get();
		existente.setNome(novo.getNome());
		existente.setDescricao(novo.getDescricao());
		
		categoriaRepository.save(existente);
		
		return new ResponseEntity<>("Categoria atualizada com sucesso", HttpStatus.OK);
	}
	
	@DeleteMapping("/categoria/{id}")
	public ResponseEntity<?> deleteCategoria(@PathVariable Long id){
		Optional<Categoria> optional = categoriaRepository.findById(id);
		 
		if(optional.isEmpty()){
			return new ResponseEntity<>("Categoria não encontrada", HttpStatus.NOT_FOUND);
		}
		
		Categoria existente = optional.get();
		
		categoriaRepository.delete(existente);
		
		return new ResponseEntity<>("Categoria deletada com sucesso", HttpStatus.OK);
	}
	
}
