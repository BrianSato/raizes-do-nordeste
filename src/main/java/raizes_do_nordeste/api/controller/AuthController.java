package raizes_do_nordeste.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import raizes_do_nordeste.api.dto.AuthRequest;
import raizes_do_nordeste.api.dto.AuthResponse;
import raizes_do_nordeste.application.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	
	//contrutor
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping
	public AuthResponse autenticar(@RequestBody AuthRequest authRequest) {
		return authService.autenticar(authRequest);
	}
}
