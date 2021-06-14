package org.serratec.dto.pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.serratec.dto.cliente.ClienteSimplificadoDTO;
import org.serratec.models.Pedido;
import org.serratec.models.ProdutosPedido;

public class PedidoDetalhesDTO {

	private String codigoPedido;
	private ClienteSimplificadoDTO cliente;
	private LocalDateTime dataPedido;
	private Double totalPedido;
	private List<ProdutosPedido> itens = new ArrayList<>();
	
	public PedidoDetalhesDTO(Pedido pedido) {
		this.codigoPedido = pedido.getNumeroPedido();
		this.cliente = new ClienteSimplificadoDTO(pedido.getCliente());
		this.dataPedido = pedido.getDataPedido();
		this.totalPedido = pedido.getTotalPedido();
		this.itens = pedido.getProdutos();
	}

	public PedidoDetalhesDTO(ProdutosPedido obj) {
		// TODO Auto-generated constructor stub
	}

	public String getCodigoPedido() {
		return codigoPedido;
	}

	public ClienteSimplificadoDTO getCliente() {
		return cliente;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public Double getTotalPedido() {
		return totalPedido;
	}

	public List<ProdutosPedido> getItens() {
		return itens;
	}
	
}
