package raizes_do_nordeste.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import raizes_do_nordeste.api.dto.PagamentoRequest;
import raizes_do_nordeste.api.dto.PagamentoResponse;
import raizes_do_nordeste.domain.entity.EstoqueProduto;
import raizes_do_nordeste.domain.entity.Fidelizacao;
import raizes_do_nordeste.domain.entity.ItemPedido;
import raizes_do_nordeste.domain.entity.Pagamento;
import raizes_do_nordeste.domain.entity.Pedido;
import raizes_do_nordeste.domain.enums.StatusPagamento;
import raizes_do_nordeste.domain.enums.StatusPedido;
import raizes_do_nordeste.infrastructure.repository.EstoqueProdutoRepository;
import raizes_do_nordeste.infrastructure.repository.FidelizacaoRepository;
import raizes_do_nordeste.infrastructure.repository.PagamentoRepository;
import raizes_do_nordeste.infrastructure.repository.PedidoRepository;

@Service
public class PagamentoService {
	
	private final PagamentoRepository pagamentoRepository;
	private final PedidoRepository pedidoRepository;
	private final EstoqueProdutoRepository estoqueProdutoRepository;
	private final FidelizacaoRepository fidelizacaoRepository;

	//construtor
	public PagamentoService(
			PagamentoRepository pagamentoRepository,
			PedidoRepository pedidoRepository,
			EstoqueProdutoRepository estoqueProdutoRepository,
			FidelizacaoRepository fidelizacaoRepository) {
		this.pagamentoRepository = pagamentoRepository;
		this.pedidoRepository = pedidoRepository;
		this.estoqueProdutoRepository = estoqueProdutoRepository;
		this.fidelizacaoRepository = fidelizacaoRepository;
	}
	
	//métodos
	public PagamentoResponse buscarPorId(Integer id) {
		
		Pagamento pagamento = pagamentoRepository
				.findById(id)
				.orElseThrow();
		
		return converterParaResponse(pagamento);
	}
	
	@Transactional
	public PagamentoResponse processarPagamento(PagamentoRequest pagamentoRequest) {
		
		Pedido pedido = pedidoRepository
				.findById(pagamentoRequest.getPedidoId())
				.orElseThrow();
		
		if(pedido.getPagamento() != null) {
			throw new IllegalStateException(
					"Pedido já possui um pagamento registrado");
		}
		
		Pagamento pagamento = new Pagamento();
		
		pagamento.setPedido(pedido);
		pedido.setPagamento(pagamento);
		pagamento.setFormaPagamento(pagamentoRequest.getFormaPagamento());
		pagamento.setValor(pedido.getValorTotal());
		
		//pagamento mock
		if(pagamentoRequest.getAprovado()) {
			pagamento.setStatus(StatusPagamento.APROVADO);
		}else {
			pagamento.setStatus(StatusPagamento.RECUSADO);
		}
	
		pagamento.setDataHora(LocalDateTime.now());
		
		Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
		
		if(pagamento.getStatus() == StatusPagamento.APROVADO) {

			for(ItemPedido item : pedido.getItens()) {
				
				EstoqueProduto estoque  = estoqueProdutoRepository
						.findByProdutoAndUnidade(
								item.getProduto(),
								pedido.getUnidade())
						.orElseThrow(() -> new IllegalStateException(
								"Produto não disponível no estoque da unidade"));
				
				estoque.removerQuantidade(item.getQuantidade());
				
				estoqueProdutoRepository.save(estoque);
			}
			
			pedido.atualizarStatus(StatusPedido.PAGAMENTO_APROVADO);
			
			Fidelizacao fidelizacao = fidelizacaoRepository
					.findByUsuario(pedido.getUsuario())
					.orElse(null);
			
			if(fidelizacao != null && fidelizacao.isConsentimento()) {
				
				Integer pontos = pedido.getValorTotal().intValue();
				
				fidelizacao.adicionarPontos(pontos);
				
				fidelizacaoRepository.save(fidelizacao);
			}
		}
		
		pedidoRepository.save(pedido);
		
		return converterParaResponse(pagamentoSalvo);
	}
	
	private PagamentoResponse converterParaResponse(Pagamento pagamento) {
		
		PagamentoResponse response = new PagamentoResponse();
		
		response.setId(pagamento.getId());
		response.setPedidoId(pagamento.getPedido().getId());
		response.setFormaPagamento(pagamento.getFormaPagamento());
		response.setValor(pagamento.getValor());
		response.setStatus(pagamento.getStatus());
		response.setDataHora(pagamento.getDataHora());
		
		return response;
	}
}
