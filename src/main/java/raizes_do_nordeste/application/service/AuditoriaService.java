package raizes_do_nordeste.application.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import raizes_do_nordeste.domain.entity.Pedido;
import raizes_do_nordeste.domain.entity.RegistroAuditoria;
import raizes_do_nordeste.domain.entity.Usuario;
import raizes_do_nordeste.domain.enums.AcaoAuditoria;
import raizes_do_nordeste.domain.enums.ResultadoAuditoria;
import raizes_do_nordeste.infrastructure.repository.RegistroAuditoriaRepository;

@Service
public class AuditoriaService {

	private final RegistroAuditoriaRepository registroAuditoriaRepository;

	
	//CONSTRUTOR
	public AuditoriaService(RegistroAuditoriaRepository registroAuditoriaRepository) {
		this.registroAuditoriaRepository = registroAuditoriaRepository;
	}
	
	//MÉTODOS
	public void registrarEvento(
			AcaoAuditoria acao,
			Usuario usuario,
			Pedido pedido,
			String statusAnterior,
			String statusNovo,
			ResultadoAuditoria resultado) {
		
		 RegistroAuditoria registro = new RegistroAuditoria(
				 acao, 
				 LocalDateTime.now(), 
				 usuario, 
				 pedido, 
				 statusAnterior, 
				 statusNovo, 
				 resultado);
		 
		registroAuditoriaRepository.save(registro);
		
	}
	
	
}
