package raizes_do_nordeste.application.service;

import org.springframework.stereotype.Service;

import raizes_do_nordeste.api.dto.UsuarioResponse;
import raizes_do_nordeste.domain.entity.Usuario;
import raizes_do_nordeste.infrastructure.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	//construtor
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	//métodos
	public UsuarioResponse buscarPorId(Integer id) {
		
		Usuario usuario = usuarioRepository
				.findById(id)
				.orElseThrow();
		
		return converterParaResponse(usuario);
	}
	
	private UsuarioResponse converterParaResponse(Usuario usuario) {
		
		UsuarioResponse response = new UsuarioResponse();
		
		response.setId(usuario.getId());
		response.setNome(usuario.getNome());
		response.setCpf(usuario.getCpf());
		response.setEmail(usuario.getEmail());
		response.setRole(usuario.getRole());
		
		return response;
		
	}
}
