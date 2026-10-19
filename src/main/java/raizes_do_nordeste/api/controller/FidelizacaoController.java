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
import raizes_do_nordeste.api.dto.FidelizacaoPontosRequest;
import raizes_do_nordeste.api.dto.FidelizacaoRequest;
import raizes_do_nordeste.api.dto.FidelizacaoResponse;
import raizes_do_nordeste.api.exception.ErroResponse;
import raizes_do_nordeste.application.service.FidelizacaoService;

@RestController
@RequestMapping("/fidelidade")
@SecurityRequirement(name = "bearerAuth")
public class FidelizacaoController {

	private final FidelizacaoService fidelizacaoService;

	//CONTRUTOR
	public FidelizacaoController(FidelizacaoService fidelizacaoService) {
		this.fidelizacaoService = fidelizacaoService;
	}
	
	//MÉTODOS
	//Consulta de pedidos por ID
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Fidelização encontrada com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = FidelizacaoResponse.class)
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
		        description = "Fidelização não encontrada",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@GetMapping("/{id}")
	public FidelizacaoResponse buscarPorId(@PathVariable Integer id) {
		return fidelizacaoService.buscarPorId(id);
	}
	//Criação de pedidos
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Fidelização criada com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = FidelizacaoResponse.class)
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
		        description = "Usuário sem permissão para criar fidelização",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "404",
		        description = "Usuário informado não encontrado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@PostMapping
	public FidelizacaoResponse criarFidelizacao(
			@RequestBody FidelizacaoRequest fidelizacaoRequest) {
		
		return fidelizacaoService.criarFidelizacao(fidelizacaoRequest);
	}
	//Adicionar pontos
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Pontos adicionados com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = FidelizacaoResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "400",
		        description = "Quantidade de pontos inválida",
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
		        description = "Usuário sem permissão para adicionar pontos",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "404",
		        description = "Fidelização não encontrada",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@PostMapping("/{id}/pontos")
	public FidelizacaoResponse adicionarPontos(
			@PathVariable Integer id,
			@RequestBody FidelizacaoPontosRequest pontosRequest) {
		
		return fidelizacaoService.adicionarPontos(id, pontosRequest.getPontos());
	}
	//Utilizar pontos
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Pontos utilizados com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = FidelizacaoResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "400",
		        description = "Quantidade inválida, saldo insuficiente ou ausência de consentimento",
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
		        description = "Usuário sem permissão para utilizar pontos",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "404",
		        description = "Fidelização não encontrada",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@PostMapping("/{id}/pontos/utilizar")
	public FidelizacaoResponse utilizarPontos(
			@PathVariable Integer id,
			@RequestBody FidelizacaoPontosRequest pontosRequest) {
		
		return fidelizacaoService.utilizarPontos(id, pontosRequest.getPontos());
	}
}
