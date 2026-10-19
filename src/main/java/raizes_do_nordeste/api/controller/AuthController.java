package raizes_do_nordeste.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import raizes_do_nordeste.api.dto.AuthRequest;
import raizes_do_nordeste.api.dto.AuthResponse;
import raizes_do_nordeste.api.exception.ErroResponse;
import raizes_do_nordeste.application.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	
	//CONSTRUTOR
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	//MÉTODO
	
	//Autenticar usuário
	@ApiResponses(value = {
		    @ApiResponse(
		        responseCode = "200",
		        description = "Autenticação realizada com sucesso",
		        content = @Content(
		            schema = @Schema(implementation = AuthResponse.class)
		        )
		    ),
		    @ApiResponse(
		        responseCode = "401",
		        description = "Não autorizado - e-mail ou senha inválidos",
		        content = @Content(
		            schema = @Schema(implementation = ErroResponse.class)
		        )
		    )
		})
	@PostMapping
	public AuthResponse autenticar(@RequestBody AuthRequest authRequest) {
		return authService.autenticar(authRequest);
	}
}
