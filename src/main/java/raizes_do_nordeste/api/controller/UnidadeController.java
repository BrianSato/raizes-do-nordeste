package raizes_do_nordeste.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import raizes_do_nordeste.api.dto.ProdutoResponse;
import raizes_do_nordeste.api.dto.UnidadeResponse;
import raizes_do_nordeste.api.exception.ErroResponse;
import raizes_do_nordeste.application.service.ProdutoService;
import raizes_do_nordeste.application.service.UnidadeService;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

	private final UnidadeService unidadeService;
	private final ProdutoService produtoService;

	//CONSTRUTOR
	public UnidadeController(UnidadeService unidadeService,ProdutoService produtoService) {
		this.unidadeService = unidadeService;
		this.produtoService = produtoService;
	}
	
	//MÉTODO
	
	//Consulta de unidade por ID
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Unidade encontrada com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = UnidadeResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "404",
		        description = "Unidade não encontrada",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@GetMapping("/{id}")
	public UnidadeResponse buscarPorId(@PathVariable Integer id) {
		return unidadeService.buscarPorId(id);
	}
	//Listar cardápio
		@ApiResponses(value = {
			    @ApiResponse(
			        responseCode = "200",
			        description = "Cardápio consultado com sucesso",
			        content = @Content(
			            array = @ArraySchema(
			            		schema = @Schema(implementation = ProdutoResponse.class)
			            )
			        )
			    ),
			    @ApiResponse(
			        responseCode = "404",
			        description = "Unidade não encontrada",
			        content = @Content(
			            schema = @Schema(implementation = ErroResponse.class)
			        )
			    )
			})
	@GetMapping("/{id}/cardapio")
	public List<ProdutoResponse> listarCardapio(@PathVariable Integer id) {
		return produtoService.listaCardapioPorUnidade(id);
	}
}
