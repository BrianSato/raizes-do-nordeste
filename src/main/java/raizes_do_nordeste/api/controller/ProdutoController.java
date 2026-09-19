package raizes_do_nordeste.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import raizes_do_nordeste.api.dto.ProdutoResponse;
import raizes_do_nordeste.application.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private final ProdutoService produtoService;

	//contrutor
	public ProdutoController(ProdutoService produtoService) {
		super();
		this.produtoService = produtoService;
	}

	//métodos
	@GetMapping("/{id}")
	public ProdutoResponse buscaPorId(@PathVariable Integer id) {
		return produtoService.buscarPorId(id);
	}
	@GetMapping
	public Page<ProdutoResponse>listaProdutos(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int limit){
		
		return produtoService.listarProdutos(page, limit);
	}
	
}
