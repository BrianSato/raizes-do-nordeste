package raizes_do_nordeste.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import raizes_do_nordeste.api.dto.PagamentoRequest;
import raizes_do_nordeste.api.dto.PagamentoResponse;
import raizes_do_nordeste.application.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

	private final PagamentoService pagamentoService;
	
	//construtor
	public PagamentoController(PagamentoService pagamentoService) {
		this.pagamentoService = pagamentoService;
	}
	
	@PostMapping
	public PagamentoResponse processarPagamento(
			@RequestBody PagamentoRequest pagamentoRequest) {
		
		return pagamentoService.processarPagamento(pagamentoRequest);
	}
}
