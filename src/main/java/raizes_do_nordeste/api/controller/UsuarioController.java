package raizes_do_nordeste.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import raizes_do_nordeste.api.dto.UsuarioResponse;
import raizes_do_nordeste.application.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	private UsuarioService usuarioService;

	//construtor
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	//metodos
	@GetMapping("/{id}")
	public UsuarioResponse buscarPorId(@PathVariable Integer id) {
		return usuarioService.buscarPorId(id);
	}
}
