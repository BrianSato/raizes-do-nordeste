package raizes_do_nordeste.application.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import raizes_do_nordeste.api.dto.AuthRequest;
import raizes_do_nordeste.api.dto.AuthResponse;
import raizes_do_nordeste.api.exception.AutenticacaoException;
import raizes_do_nordeste.config.JwtService;
import raizes_do_nordeste.domain.entity.Usuario;
import raizes_do_nordeste.infrastructure.repository.UsuarioRepository;

@Service
public class AuthService {
	
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	//construtor
	public AuthService(
			UsuarioRepository usuarioRepository, 
			PasswordEncoder passwordEncoder, 
			JwtService jwtService) {
		
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		
	}
	//métodos
	public AuthResponse autenticar(AuthRequest authRequest) {
		
		Usuario usuario = usuarioRepository
				.findByEmail(authRequest.getEmail())
				.orElseThrow();
		
		if(!passwordEncoder.matches(
				authRequest.getSenha(),
				usuario.getSenha())) {
			
			throw new AutenticacaoException("Senha inválida.");
		}
		
		String token = jwtService.gerarToken(
				usuario.getId(),
				usuario.getEmail(),
				usuario.getRole().name()
		);
		
		AuthResponse response = new AuthResponse();
		
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setEmail(usuario.getEmail());
		response.setRole(usuario.getRole());
		response.setToken(token);
		
		return response;
	}

	

	
}
