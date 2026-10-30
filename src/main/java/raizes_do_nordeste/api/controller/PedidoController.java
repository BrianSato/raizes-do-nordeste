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

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import raizes_do_nordeste.api.dto.PedidoRequest;
import raizes_do_nordeste.api.dto.PedidoResponse;
import raizes_do_nordeste.api.exception.ErroResponse;
import raizes_do_nordeste.application.service.PedidoService;
import raizes_do_nordeste.domain.enums.CanalPedido;
import raizes_do_nordeste.domain.enums.StatusPedido;

@RestController
@RequestMapping("/pedidos")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {
	
	private final PedidoService pedidoService;

	//CONSTRUTOR
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	//MÉTODOS
	
	//Lista de pedidos
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Lista de pedidos retornada com sucesso",
		        content = @Content(
		            array = @ArraySchema(
		                schema = @Schema(implementation = PedidoResponse.class)
		            )
		        )
		    ),
		    @ApiResponse(
		        responseCode = "401",
		        description = "Usuário não autenticado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "403",
		        description = "Usuário sem permissão",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@GetMapping
	public List<PedidoResponse> listarPedidos(
			@RequestParam(required = false) StatusPedido status,
			@RequestParam(required = false) CanalPedido canalPedido){
		
		return pedidoService.listarPedidos(status, canalPedido);
	}
	//Consulta de pedidos por ID
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Pedido encontrado",
		        content = @Content(
		            schema = @Schema(implementation = PedidoResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "401",
		        description = "Usuário não autenticado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "403",
		        description = "Usuário sem permissão",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "404",
		        description = "Pedido não encontrado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@GetMapping("/{id}")
	public PedidoResponse buscarPorId(@PathVariable Integer id) {
		return pedidoService.buscarPorId(id);
	}
	//Criação de pedidos
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "201",
		        description = "Pedido criado com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = PedidoResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "400",
		        description = "Dados inválidos ou regra de negócio não atendida",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "401",
		        description = "Usuário não autenticado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "403",
		        description = "Usuário sem permissão para criar pedidos",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		    	responseCode = "404",
		    	description = "Produto ou unidade não encontrado",
		    	content = @Content(
		    	    schema = @Schema(implementation = ErroResponse.class)
		    	)
		    ),
		    @ApiResponse(
		    	responseCode = "409",
		    	description = "Estoque insuficiente para atender ao pedido",
		    	content = @Content(
		    	    schema = @Schema(implementation = ErroResponse.class)
		    	)
		    )
		})
	@PostMapping
	public ResponseEntity<PedidoResponse> criarPedido(
			@Valid @RequestBody PedidoRequest pedidoRequest){
		
		PedidoResponse resposta = pedidoService.criarPedido(pedidoRequest);
	
		return ResponseEntity
				.status(201)
				.body(resposta);
	}
	//Atualizar status
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Status do pedido atualizado com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = PedidoResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "400",
		        description = "Parâmetro de status inválido",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "401",
		        description = "Usuário não autenticado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "403",
		        description = "Usuário sem permissão",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "404",
		        description = "Pedido não encontrado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "409",
		        description = "Transição de status não permitida",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@PutMapping("/{id}/status")
	public PedidoResponse atualizarStatus(
			@PathVariable Integer id,
			@RequestParam StatusPedido novoStatus) {
		
		return pedidoService.atualizarStatus(id, novoStatus);
	}
	//Cancelar pedido
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Pedido cancelado com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = PedidoResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "401",
		        description = "Usuário não autenticado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "403",
		        description = "Usuário sem permissão",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "404",
		        description = "Pedido não encontrado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "409",
		        description = "Pedido não pode ser cancelado no estado atual",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@PutMapping("/{id}/cancelar")
	public PedidoResponse cancelarPedido(@PathVariable Integer id) {
		
		return pedidoService.cancelarPedido(id);
	}
}
