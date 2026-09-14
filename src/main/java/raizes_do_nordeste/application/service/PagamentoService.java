package raizes_do_nordeste.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import raizes_do_nordeste.domain.entity.Pagamento;
import raizes_do_nordeste.domain.enums.StatusPagamento;
import raizes_do_nordeste.infrastructure.repository.PagamentoRepository;

@Service
public class PagamentoService {
	
	private final PagamentoRepository pagamentoRepository;

	//construtor
	public PagamentoService(PagamentoRepository pagamentoRepository) {
		this.pagamentoRepository = pagamentoRepository;
	}
	
	//métodos
	public Pagamento processarPagamento(Pagamento pagamento) {
		pagamento.setStatus(StatusPagamento.APROVADO);
		pagamento.setDataHora(LocalDateTime.now());
		return pagamentoRepository.save(pagamento);
	}
}
