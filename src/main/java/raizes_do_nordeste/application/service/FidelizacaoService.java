package raizes_do_nordeste.application.service;

import org.springframework.stereotype.Service;

import raizes_do_nordeste.domain.entity.Fidelizacao;
import raizes_do_nordeste.infrastructure.repository.FidelizacaoRepository;

@Service
public class FidelizacaoService {
	
	private final FidelizacaoRepository fidelizacaoRepository;

	//construtor
	public FidelizacaoService(FidelizacaoRepository fidelizacaoRepository) {
		this.fidelizacaoRepository = fidelizacaoRepository;
	}
	
	//métodos{
	public Fidelizacao adicionarPontos(Fidelizacao fidelizacao, Integer pontos) {
		fidelizacao.adicionarPontos(pontos);
		return fidelizacaoRepository.save(fidelizacao);
	}
	public Fidelizacao buscarPorId(Integer id) {
		return fidelizacaoRepository.findById(id).orElse(null);
	}
	public Fidelizacao utilizarPontos(Fidelizacao fidelizacao, Integer pontos) {
		fidelizacao.utilizarPontos(pontos);
		return fidelizacaoRepository.save(fidelizacao);
	}
}
