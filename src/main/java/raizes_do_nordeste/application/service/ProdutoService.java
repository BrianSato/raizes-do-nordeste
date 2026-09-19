package raizes_do_nordeste.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import raizes_do_nordeste.api.dto.ProdutoResponse;
import raizes_do_nordeste.domain.entity.Produto;
import raizes_do_nordeste.infrastructure.repository.ProdutoRepository;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;
	
	//copntrutor
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	//métodos
	public ProdutoResponse buscarPorId(Integer id) {
		
		Produto produto = produtoRepository
				.findById(id)
				.orElseThrow();
		
		return converterParaResponse(produto);
	}
	
	private ProdutoResponse converterParaResponse(Produto produto) {
		
		ProdutoResponse response = new ProdutoResponse();
		
		response.setId(produto.getId());
		response.setNome(produto.getNome());
		response.setPreco(produto.getPreco());
		response.setValidade(produto.getValidade());
		
		return response;
	}
	public Page<ProdutoResponse> listarProdutos(int page,int limit){
		
		PageRequest pageable = PageRequest.of(page, limit);
		
		return produtoRepository
				.findAll(pageable)
				.map(this::converterParaResponse);
	}
}
