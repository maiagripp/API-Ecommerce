package org.serratec.resources;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.dto.pedido.PedidoAtualizarItemDTO;
import org.serratec.dto.pedido.PedidoCadastroDTO;
import org.serratec.dto.pedido.PedidoDetalhesDTO;
import org.serratec.dto.pedido.PedidoFinalizarDTO;
import org.serratec.exception.PedidoException;
import org.serratec.models.Pedido;
import org.serratec.models.ProdutosPedido;
import org.serratec.models.StatusPedido;
import org.serratec.repository.ClienteRepository;
import org.serratec.repository.PedidoRepository;
import org.serratec.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "API - Pedidos do Ecommerce")
public class PedidoResource {

	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@ApiOperation(value = "Consulta todos os pedidos")
	@GetMapping("/pedido/todos")
	public ResponseEntity<?> getTodos() {
		
		List<Pedido> pedido = pedidoRepository.findAll();
		return new ResponseEntity<>(pedido, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Consulta um pedido com detalhamento do mesmo")
	@GetMapping("/pedido/detalhado/{numeroPedido}")
	public ResponseEntity<?> getDetalhadaTodos(@PathVariable String numeroPedido ) {
		
		Optional<Pedido> optional = pedidoRepository.findByNumeroPedido(numeroPedido);
		
		if(optional.isEmpty())
			return new ResponseEntity<>("Pedido não encontrado!", HttpStatus.NOT_FOUND);
		
		
		return new ResponseEntity<>(new PedidoDetalhesDTO(optional.get()), HttpStatus.OK);
	}
	
	@ApiOperation(value = "Cadastro de um novo pedido")
	@PostMapping("/pedido")
	public ResponseEntity<?> postPedido(@Validated @RequestBody PedidoCadastroDTO dto){
		try {
			Pedido pedido = dto.toPedido(clienteRepository, produtoRepository);
			pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
			pedidoRepository.save(pedido);
			return new ResponseEntity<>("Pedido " +  pedido.getNumeroPedido() + " cadastrado com sucesso ", HttpStatus.CREATED);		
		} catch (PedidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}	
	}
	
	@ApiOperation(value = "Alteração de um pedido")
	@PutMapping("/pedido")
	public ResponseEntity<?> atualizarPedido(@RequestBody PedidoAtualizarItemDTO dto){
		
		try {
			Pedido pedido = dto.toPedido(pedidoRepository);
			
			for (ProdutosPedido pp : pedido.getProdutos()) {
				if (pp.getProduto().getCodigo().equals(dto.getCodigoProduto())) {
					if (dto.getQuantidade() == 0) {
						pedido.getProdutos().remove(pp);
						pedido.setValorTotal(pedido.getTotalPedido());
					}else {
						pp.setQuantidade(dto.getQuantidade());
						pedido.setValorTotal(pedido.getTotalPedido());
					}
					pedidoRepository.save(pedido);
					
					return new ResponseEntity<>("Item atualizado com sucesso!", HttpStatus.OK);
				}
			}
			
			return new ResponseEntity<>("Item não encontrado.", HttpStatus.NOT_FOUND);
			
		}catch (PedidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@ApiOperation(value = "Deletando um pedido")
	@DeleteMapping("/pedido/excluir/{numeroPedido}")
	public ResponseEntity<?> deletePedido(@PathVariable String numeroPedido){
		Optional <Pedido> optional = pedidoRepository.findByNumeroPedido(numeroPedido);
		
		if(optional.isEmpty()) {
			return new ResponseEntity<>("Pedido não encontrado", HttpStatus.NOT_FOUND);
		}
	
		Pedido existente = optional.get();
		
		pedidoRepository.delete(existente);
		
		return new ResponseEntity<>("Pedido deletado com sucesso", HttpStatus.OK);
	}
	
	@ApiOperation(value = "Finalizando um pedido")
	@PostMapping("/pedido/finalizar")
	public ResponseEntity<?> postFinalizar(@Validated @RequestBody PedidoFinalizarDTO dto){
		try {
			Pedido pedido = dto.toPedido(pedidoRepository);
			pedido.setStatusPedido(StatusPedido.FINALIZADO);
			pedido.setFormaPagamento(dto.getFormaPagamento());
			
			pedidoRepository.save(pedido);
			LocalDate entrega = LocalDate.now().plusDays(7);
			/*
			 * return ("Data estimada para entrega " + entrega + " Produtos adquiridos " +
			 * pedido.getProdutos(), nova.getVenda().getLeitor().getEmail());
			 */
			return new ResponseEntity<>("Data estimada para entrega " + entrega + " Produtos adquiridos " + pedido.getProdutos().stream().map(obj-> new PedidoDetalhesDTO(obj)).collect(Collectors.toList()) + "Total " + pedido.getTotalPedido(), HttpStatus.OK);		
		} catch (PedidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}	
	}
}
