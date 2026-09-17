package raizes_do_nordeste.application.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import raizes_do_nordeste.api.dto.ItemPedidoRequest;
import raizes_do_nordeste.api.dto.ItemPedidoResponse;
import raizes_do_nordeste.api.dto.PedidoRequest;
import raizes_do_nordeste.api.dto.PedidoResponse;
import raizes_do_nordeste.domain.entity.ItemPedido;
import raizes_do_nordeste.domain.entity.Pedido;
import raizes_do_nordeste.domain.entity.Produto;
import raizes_do_nordeste.domain.entity.Unidade;
import raizes_do_nordeste.domain.entity.Usuario;
import raizes_do_nordeste.domain.enums.StatusPedido;
import raizes_do_nordeste.infrastructure.repository.PedidoRepository;
import raizes_do_nordeste.infrastructure.repository.ProdutoRepository;
import raizes_do_nordeste.infrastructure.repository.UnidadeRepository;
import raizes_do_nordeste.infrastructure.repository.UsuarioRepository;

@Service
public class PedidoService {
	
	private final PedidoRepository pedidoRepository;
	private final ProdutoRepository produtoRepository;
	private final UsuarioRepository usuarioRepository;
	private final UnidadeRepository unidadeRepository;
	
	//construtor
	public PedidoService(
			ProdutoRepository produtoRepository,
			PedidoRepository pedidoRepository,
			UsuarioRepository usuarioRepository,
			UnidadeRepository unidadeRepository) {
		this.produtoRepository = produtoRepository;
		this.pedidoRepository = pedidoRepository;
		this.usuarioRepository = usuarioRepository;
		this.unidadeRepository = unidadeRepository;
	}
	
	//métodos
	public PedidoResponse buscarPorId(Integer id) {
		
		Pedido pedido = pedidoRepository
				.findById(id)
				.orElseThrow();
		
		return converterParaResponse(pedido);
	}
	
	public Pedido salvar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
	public PedidoResponse criarPedido(PedidoRequest pedidoRequest) {
		
		Pedido pedido = new Pedido();
		pedido.setCanalPedido(pedidoRequest.getCanalPedido());
		pedido.setDataHora(LocalDateTime.now());
		
		Usuario usuario = usuarioRepository
				.findById(pedidoRequest.getUsuarioId())
				.orElseThrow();
		Unidade unidade = unidadeRepository
				.findById(pedidoRequest.getUnidadeId())
				.orElseThrow();
		
		pedido.setUsuario(usuario);
		pedido.setUnidade(unidade);
		
		for(ItemPedidoRequest itemRequest: pedidoRequest.getItens()) {
			
			Produto produto = produtoRepository
					.findById(itemRequest.getProdutoId())
					.orElseThrow();
			
			ItemPedido item = new ItemPedido();
			
			item.setProduto(produto);
			item.setQuantidade(itemRequest.getQuantidade());
			item.setPrecoUnitario(produto.getPreco());
			
			pedido.adicionarItem(item);
		}
		
		pedido.calcularTotal();
		
		Pedido pedidoSalvo = pedidoRepository.save(pedido);
		
		return converterParaResponse(pedidoSalvo);
		
	}
	public PedidoResponse atualizarStatus(Integer id, StatusPedido novoStatus) {
		
		Pedido pedido = pedidoRepository
				.findById(id)
				.orElseThrow();
		
		pedido.atualizarStatus(novoStatus);
		
		Pedido pedidoSalvo = pedidoRepository.save(pedido);
		
		return converterParaResponse(pedidoSalvo);
	}
	
	private PedidoResponse converterParaResponse(Pedido pedido) {
		PedidoResponse response = new PedidoResponse();
		
		response.setId(pedido.getId());
		response.setCanalPedido(pedido.getCanalPedido());
		response.setDataHora(pedido.getDataHora());
		response.setStatus(pedido.getStatus());
		response.setValorTotal(pedido.getValorTotal());
		response.setUsuarioId(pedido.getUsuario().getId());
		response.setUnidadeId(pedido.getUnidade().getId());
		
		List<ItemPedidoResponse> itensResponse = new ArrayList<>();
		
		for(ItemPedido item : pedido.getItens()) {
			
			ItemPedidoResponse itemResponse = new ItemPedidoResponse();
			
			itemResponse.setProdutoId(item.getProduto().getId());
			itemResponse.setQuantidade(item.getQuantidade());
			itemResponse.setPrecoUnitario(item.getPrecoUnitario());
			
			itensResponse.add(itemResponse);
		}
		
		response.setItens(itensResponse);
		
		return response;
	}
}
