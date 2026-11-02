package raizes_do_nordeste.application.service;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import raizes_do_nordeste.api.dto.UsuarioResponse;
import raizes_do_nordeste.domain.entity.Usuario;
import raizes_do_nordeste.infrastructure.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	//CONSTRUTOR
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	//MÉTODOS
	public UsuarioResponse buscarPorId(Integer id) {
		
		String emailAutenticado = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();
		
		Usuario usuario = usuarioRepository
				.findById(id)
				.orElseThrow();
		
		boolean proprioUsuario = emailAutenticado.equals(usuario.getEmail());
		
		Authentication authentication = SecurityContextHolder
		        .getContext()
		        .getAuthentication();
		
		boolean gerente = authentication.getAuthorities()
				.stream()
				.anyMatch(permissao ->
						permissao.getAuthority().equals("ROLE_GERENTE"));
		
		if(!proprioUsuario && !gerente) {
			throw new AccessDeniedException(
					"Você não tem permissão para consultar este usuário."
				);
			}
		
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
