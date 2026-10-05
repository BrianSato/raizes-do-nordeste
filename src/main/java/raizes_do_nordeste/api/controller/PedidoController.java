package raizes_do_nordeste.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import raizes_do_nordeste.api.dto.PedidoRequest;
import raizes_do_nordeste.api.dto.PedidoResponse;
import raizes_do_nordeste.application.service.PedidoService;
import raizes_do_nordeste.domain.enums.CanalPedido;
import raizes_do_nordeste.domain.enums.StatusPedido;

@RestController
@RequestMapping("/pedidos")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {
	
	private final PedidoService pedidoService;

	//contrutor
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	//métodos
	@GetMapping
	public List<PedidoResponse> listarPedidos(
			@RequestParam(required = false) StatusPedido status,
			@RequestParam(required = false) CanalPedido canalPedido){
		
		return pedidoService.listarPedidos(status, canalPedido);
	}
	@GetMapping("/{id}")
	public PedidoResponse buscarPorId(@PathVariable Integer id) {
		return pedidoService.buscarPorId(id);
	}
	@PostMapping
	public ResponseEntity<PedidoResponse> criarPedido(
			@Valid @RequestBody PedidoRequest pedidoRequest){
		
		PedidoResponse resposta = pedidoService.criarPedido(pedidoRequest);
	
		return ResponseEntity
				.status(201)
				.body(resposta);
	}
	@PutMapping("/{id}/status")
	public PedidoResponse atualizarStatus(
			@PathVariable Integer id,
			@RequestParam StatusPedido novoStatus) {
		
		return pedidoService.atualizarStatus(id, novoStatus);
	}
	@PutMapping("/{id}/cancelar")
	public PedidoResponse cancelarPedido(@PathVariable Integer id) {
		
		return pedidoService.cancelarPedido(id);
	}
}
