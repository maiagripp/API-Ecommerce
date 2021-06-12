package org.serratec.resources;

import java.util.ArrayList;
import java.util.List;

import org.serratec.dto.cliente.ClienteCadastroDTO;
import org.serratec.dto.cliente.ClienteCompletoDTO;
import org.serratec.exception.ClienteException;
import org.serratec.models.Cliente;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.EnderecoRepository;
import org.serratec.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "API - Clientes do Ecommerce")
public class ClienteResource {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	EmailService emailService;
	
	@ApiOperation(value = "Consulta todos os clientes")
	@GetMapping("/cliente/todos")
	public ResponseEntity<?> getTodos() {
		List<Cliente> clientes = clienteRepository.findAll();
		List<ClienteCompletoDTO> clientesDtos = new ArrayList<>();
		
		for (Cliente cliente : clientes) {
			clientesDtos.add(new ClienteCompletoDTO(cliente));
		}
		
		return new ResponseEntity<>(clientesDtos, HttpStatus.OK);
	}
	
	@GetMapping("/cliente/detalhe/{id}")
	public ResponseEntity<?> getDetalhe(@PathVariable Long id) {
		
		
		try {
			Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteException("Cliente não encontrado"));
			return new ResponseEntity<>(new ClienteCompletoDTO(cliente), HttpStatus.OK);
			
		} catch (ClienteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@ApiOperation(value = "Cadastro de um novo cliente")
	@PostMapping("/cliente")
	public ResponseEntity<?> postCliente(@Validated @RequestBody ClienteCadastroDTO clienteDTO) {
		Cliente cliente = clienteDTO.toCliente();
		
		try {					
			clienteRepository.save(cliente);
			
			emailService.enviar("Bem vindo", "Seu e-mail foi cadastrado com sucesso", cliente.getEmail());
			
			return new ResponseEntity<>("Cliente cadastrado com sucesso!", HttpStatus.CREATED);
		} catch (ClienteException e) {
			if(clienteRepository.existsByEmail(cliente.getEmail()))
				return new ResponseEntity<>("Já existe um cliente com este email", HttpStatus.BAD_REQUEST);
			if(clienteRepository.existsByCPF(cliente.getCPF()))
				return new ResponseEntity<>("Já existe um cliente com este CPF", HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
//	@ApiOperation(value = "Atualização de uma categoria a partir do seu id")
//	@PutMapping("/categoria/{id}")
//	public ResponseEntity<?> putCategoria(@PathVariable Long id, @RequestBody Categoria novo) {
//		Optional<Categoria> optional = categoriaRepository.findById(id);
//		 
//		if(optional.isEmpty()){
//			return new ResponseEntity<>("Categoria não encontrada", HttpStatus.NOT_FOUND);
//		}
//		
//		Categoria existente = optional.get();
//		existente.setNome(novo.getNome());
//		existente.setDescricao(novo.getDescricao());
//		
//		categoriaRepository.save(existente);
//		
//		return new ResponseEntity<>("Categoria atualizada com sucesso", HttpStatus.OK);
//	}
	
	@DeleteMapping("/cliente")
	public ResponseEntity<?> deletaCliente(@PathVariable String email) {

		try {
			Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(() -> new ClienteException("Cliente não encontrado"));
			clienteRepository.delete(cliente);
			return new ResponseEntity<>("Cliente removido com sucesso", HttpStatus.OK);
			
		} catch (ClienteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	} 
}
