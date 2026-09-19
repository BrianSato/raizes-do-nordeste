package raizes_do_nordeste.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import raizes_do_nordeste.api.dto.UnidadeResponse;
import raizes_do_nordeste.application.service.UnidadeService;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

	private UnidadeService unidadeService;

	//construtor
	public UnidadeController(UnidadeService unidadeService) {
		this.unidadeService = unidadeService;
	}
	
	//métodos
	@GetMapping("/{id}")
	public UnidadeResponse buscarPorId(@PathVariable Integer id) {
		return unidadeService.buscarPorId(id);
	}
}
