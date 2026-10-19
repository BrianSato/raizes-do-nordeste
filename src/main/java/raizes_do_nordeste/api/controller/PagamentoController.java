package raizes_do_nordeste.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import raizes_do_nordeste.api.dto.PagamentoRequest;
import raizes_do_nordeste.api.dto.PagamentoResponse;
import raizes_do_nordeste.api.exception.ErroResponse;
import raizes_do_nordeste.application.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
@SecurityRequirement(name = "bearerAuth")
public class PagamentoController {

	private final PagamentoService pagamentoService;
	
	//CONSTRUTOR
	public PagamentoController(PagamentoService pagamentoService) {
		this.pagamentoService = pagamentoService;
	}
	
	//MÉTODOS
	
	//Consultar de pagamentos por ID
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Pagamento encontrado com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = PagamentoResponse.class)
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
		        description = "Pagamento não encontrado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@GetMapping("/{id}")
	public PagamentoResponse buscarPorId(@PathVariable Integer id) {
		return pagamentoService.buscarPorId(id);
	}
	//Processar pagamento
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Pagamento processado com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = PagamentoResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "400",
		        description = "Dados inválidos",
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
		        description = "Conflito com regra de negócio",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@PostMapping
	public PagamentoResponse processarPagamento(
			@Valid @RequestBody PagamentoRequest pagamentoRequest) {
		
		return pagamentoService.processarPagamento(pagamentoRequest);
	}
}
