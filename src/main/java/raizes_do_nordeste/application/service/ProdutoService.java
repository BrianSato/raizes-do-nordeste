package raizes_do_nordeste.application.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import raizes_do_nordeste.api.dto.ProdutoResponse;
import raizes_do_nordeste.domain.entity.EstoqueProduto;
import raizes_do_nordeste.domain.entity.Produto;
import raizes_do_nordeste.domain.entity.Unidade;
import raizes_do_nordeste.infrastructure.repository.EstoqueProdutoRepository;
import raizes_do_nordeste.infrastructure.repository.ProdutoRepository;
import raizes_do_nordeste.infrastructure.repository.UnidadeRepository;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;
	private final EstoqueProdutoRepository estoqueProdutoRepository;
	private final UnidadeRepository unidadeRepository;
	
	//CONSTRUTOR
	public ProdutoService(ProdutoRepository produtoRepository,
			EstoqueProdutoRepository estoqueProdutoRepository,
			UnidadeRepository unidadeRepository) {
		this.produtoRepository = produtoRepository;
		this.estoqueProdutoRepository = estoqueProdutoRepository;
		this.unidadeRepository = unidadeRepository;
	}
	
	//MÉTODOS
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
	public List<ProdutoResponse> listaCardapioPorUnidade(Integer unidadeId){
		
		Unidade unidade = unidadeRepository
				.findById(unidadeId)
				.orElseThrow(() -> new NoSuchElementException("Unidade não encontrada"));
		
		List<EstoqueProduto> estoques = estoqueProdutoRepository.findByUnidade(unidade);
		
		List<EstoqueProduto> estoquesDisponiveis = estoques.stream()
				.filter(estoque -> estoque.getQuantidade() > 0)
				.toList();
		
		List<ProdutoResponse> produtosDisponiveis = estoquesDisponiveis.stream()
				.map(estoque -> converterParaResponse(estoque.getProduto()))
				.toList();
		
		return produtosDisponiveis;
	}
}
