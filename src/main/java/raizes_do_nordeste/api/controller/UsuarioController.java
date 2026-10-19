package raizes_do_nordeste.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import raizes_do_nordeste.api.dto.UsuarioResponse;
import raizes_do_nordeste.api.exception.ErroResponse;
import raizes_do_nordeste.application.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

	private UsuarioService usuarioService;

	//CONSTRUTOR
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	//MÉTODO
	
	//Consulta de pedidos por ID
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Usuário encontrado com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = UsuarioResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "401",
		        description = "Usuário não autenticado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "404",
		        description = "Usuário não encontrado",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@GetMapping("/{id}")
	public UsuarioResponse buscarPorId(@PathVariable Integer id) {
		return usuarioService.buscarPorId(id);
	}
}
