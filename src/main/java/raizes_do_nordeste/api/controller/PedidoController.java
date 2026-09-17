package raizes_do_nordeste.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import raizes_do_nordeste.api.dto.PedidoRequest;
import raizes_do_nordeste.api.dto.PedidoResponse;
import raizes_do_nordeste.application.service.PedidoService;
import raizes_do_nordeste.domain.entity.Pedido;
import raizes_do_nordeste.domain.enums.StatusPedido;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	private final PedidoService pedidoService;

	//contrutor
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	//métodos
	@GetMapping("/{id}")
	public PedidoResponse buscarPorId(@PathVariable Integer id) {
		return pedidoService.buscarPorId(id);
	}
	@PostMapping
	public PedidoResponse criarPedido(@RequestBody PedidoRequest pedidoRequest) {
		return pedidoService.criarPedido(pedidoRequest);
	}
	@PutMapping("/{id}/status")
	public PedidoResponse atualizarStatus(
			@PathVariable Integer id,
			@RequestParam StatusPedido novoStatus) {
		
		return pedidoService.atualizarStatus(id, novoStatus);
	}
}
