package raizes_do_nordeste.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import raizes_do_nordeste.api.dto.FidelizacaoPontosRequest;
import raizes_do_nordeste.api.dto.FidelizacaoRequest;
import raizes_do_nordeste.api.dto.FidelizacaoResponse;
import raizes_do_nordeste.application.service.FidelizacaoService;

@RestController
@RequestMapping("/fidelidade")
public class FidelizacaoController {

	private final FidelizacaoService fidelizacaoService;

	//construtor
	public FidelizacaoController(FidelizacaoService fidelizacaoService) {
		this.fidelizacaoService = fidelizacaoService;
	}
	
	//métodos
	@GetMapping("/{id}")
	public FidelizacaoResponse buscarPorId(@PathVariable Integer id) {
		return fidelizacaoService.buscarPorId(id);
	}
	@PostMapping
	public FidelizacaoResponse criarFidelizacao(
			@RequestBody FidelizacaoRequest fidelizacaoRequest) {
		
		return fidelizacaoService.criarFidelizacao(fidelizacaoRequest);
	}
	@PostMapping("/{id}/pontos")
	public FidelizacaoResponse adicionarPontos(
			@PathVariable Integer id,
			@RequestBody FidelizacaoPontosRequest pontosRequest) {
		
		return fidelizacaoService.adicionarPontos(id, pontosRequest.getPontos());
	}
	@PostMapping("/{id}/pontos/utilizar")
	public FidelizacaoResponse utilizarPontos(
			@PathVariable Integer id,
			@RequestBody FidelizacaoPontosRequest pontosRequest) {
		
		return fidelizacaoService.utilizarPontos(id, pontosRequest.getPontos());
	}
}
