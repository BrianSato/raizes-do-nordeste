package raizes_do_nordeste.application.service;

import org.springframework.stereotype.Service;

import raizes_do_nordeste.api.dto.FidelizacaoRequest;
import raizes_do_nordeste.api.dto.FidelizacaoResponse;
import raizes_do_nordeste.domain.entity.Fidelizacao;
import raizes_do_nordeste.domain.entity.Usuario;
import raizes_do_nordeste.infrastructure.repository.FidelizacaoRepository;
import raizes_do_nordeste.infrastructure.repository.UsuarioRepository;

@Service
public class FidelizacaoService {
	
	private final UsuarioRepository usuarioRepository;
	private final FidelizacaoRepository fidelizacaoRepository;

	//construtor
	public FidelizacaoService(
			
			UsuarioRepository usuarioRepository, 
			FidelizacaoRepository fidelizacaoRepository) {
		
		this.usuarioRepository = usuarioRepository;
		this.fidelizacaoRepository = fidelizacaoRepository;
		
	}
	
	//métodos
	public FidelizacaoResponse buscarPorId(Integer id) {

		Fidelizacao fidelizacao = fidelizacaoRepository
				.findById(id)
				.orElseThrow();
		
		return converterParaResponse(fidelizacao);
	}

	public FidelizacaoResponse criarFidelizacao(FidelizacaoRequest fidelizacaoRequest) {
		
		Usuario usuario = usuarioRepository
				.findById(fidelizacaoRequest.getUsuarioId())
				.orElseThrow();
		
		Fidelizacao fidelizacao = new Fidelizacao();
		
		fidelizacao.setUsuario(usuario);
		fidelizacao.setSaldoPontos(0);
		fidelizacao.setConsentimento(fidelizacaoRequest.isConsentimento());
		
		Fidelizacao fidelizacaoSalva = fidelizacaoRepository.save(fidelizacao);
		
		return converterParaResponse(fidelizacaoSalva);
	}
	
	public FidelizacaoResponse adicionarPontos(Integer id, Integer pontos) {
		
		Fidelizacao fidelizacao = fidelizacaoRepository
				.findById(id)
				.orElseThrow();
		
		fidelizacao.adicionarPontos(pontos);
		
		Fidelizacao fidelizacaoSalva = fidelizacaoRepository.save(fidelizacao);
		
		return converterParaResponse(fidelizacaoSalva);
	}
	
	public FidelizacaoResponse utilizarPontos(Integer id, Integer pontos) {

		Fidelizacao fidelizacao = fidelizacaoRepository
				.findById(id)
				.orElseThrow();
		
		fidelizacao.utilizarPontos(pontos);
		
		Fidelizacao fidelizacaoSalva = fidelizacaoRepository.save(fidelizacao);
		
		return converterParaResponse(fidelizacaoSalva);
	}
	
	private FidelizacaoResponse converterParaResponse(Fidelizacao fidelizacao) {
		
		FidelizacaoResponse response = new FidelizacaoResponse();
		
		response.setId(fidelizacao.getId());
		response.setSaldoPontos(fidelizacao.getSaldoPontos());
		response.setConsentimento(fidelizacao.isConsentimento());
		response.setUsuarioId(fidelizacao.getUsuario().getId());
		
		return response;
	}
}
