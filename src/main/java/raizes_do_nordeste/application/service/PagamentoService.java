package raizes_do_nordeste.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import raizes_do_nordeste.api.dto.PagamentoRequest;
import raizes_do_nordeste.api.dto.PagamentoResponse;
import raizes_do_nordeste.domain.entity.Pagamento;
import raizes_do_nordeste.domain.entity.Pedido;
import raizes_do_nordeste.domain.enums.StatusPagamento;
import raizes_do_nordeste.domain.enums.StatusPedido;
import raizes_do_nordeste.infrastructure.repository.PagamentoRepository;
import raizes_do_nordeste.infrastructure.repository.PedidoRepository;

@Service
public class PagamentoService {
	
	private final PagamentoRepository pagamentoRepository;
	private final PedidoRepository pedidoRepository;

	//construtor
	public PagamentoService(
			PagamentoRepository pagamentoRepository,
			PedidoRepository pedidoRepository) {
		this.pagamentoRepository = pagamentoRepository;
		this.pedidoRepository = pedidoRepository;
	}
	
	//métodos
	public PagamentoResponse processarPagamento(PagamentoRequest pagamentoRequest) {
		
		Pedido pedido = pedidoRepository
				.findById(pagamentoRequest.getPedidoId())
				.orElseThrow();
		
		Pagamento pagamento = new Pagamento();
		
		pagamento.setPedido(pedido);
		pagamento.setFormaPagamento(pagamentoRequest.getFormaPagamento());
		pagamento.setValor(pedido.getValorTotal());
		
		//pagamento mock
		pagamento.setStatus(StatusPagamento.APROVADO);
		pagamento.setDataHora(LocalDateTime.now());
		
		Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
		
		pedido.atualizarStatus(StatusPedido.PAGAMENTO_APROVADO);
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
