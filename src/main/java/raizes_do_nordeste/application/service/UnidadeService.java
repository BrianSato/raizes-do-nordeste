package raizes_do_nordeste.application.service;

import org.springframework.stereotype.Service;

import raizes_do_nordeste.api.dto.UnidadeResponse;
import raizes_do_nordeste.domain.entity.Unidade;
import raizes_do_nordeste.infrastructure.repository.UnidadeRepository;

@Service
public class UnidadeService {

	private final UnidadeRepository unidadeRepository;
	
	//contrutor
	public UnidadeService(UnidadeRepository unidadeRepository) {
		this.unidadeRepository = unidadeRepository;
	}
	
	//métodos
	public UnidadeResponse buscarPorId(Integer id) {
		
		Unidade unidade = unidadeRepository
				.findById(id)
				.orElseThrow();
		
		return converterParaResponse(unidade);
	}
	
	private UnidadeResponse converterParaResponse(Unidade unidade) {
		
		UnidadeResponse response = new UnidadeResponse();
		
		response.setId(unidade.getId());
		response.setNome(unidade.getNome());
		response.setEndereco(unidade.getEndereco());
		
		return response;
	}
}
