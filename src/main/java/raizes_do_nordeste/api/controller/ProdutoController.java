package raizes_do_nordeste.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import raizes_do_nordeste.api.dto.ProdutoResponse;
import raizes_do_nordeste.api.exception.ErroResponse;
import raizes_do_nordeste.application.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private final ProdutoService produtoService;

	//CONSTRUTOR
	public ProdutoController(ProdutoService produtoService) {
		super();
		this.produtoService = produtoService;
	}

	//MÉTODOS
	
	//Consulta de pedidos por ID
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Produto encontrado com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = ProdutoResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "404",
		        description = "Produto não encontrado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@GetMapping("/{id}")
	public ProdutoResponse buscaPorId(@PathVariable Integer id) {
		return produtoService.buscarPorId(id);
	}
	//Lista de produtos
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Lista paginada de produtos retornada com sucesso"
		    ),
		    @ApiResponse(
		    	    responseCode = "400",
		    	    description = "Parâmetros de paginação inválidos",
		    	    content = @Content(
		    	        schema = @Schema(implementation = ErroResponse.class)
		    	    )
		    	)
		})
	@GetMapping
	public Page<ProdutoResponse>listaProdutos(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit){
		
		return produtoService.listarProdutos(page, limit);
	}
	
}
