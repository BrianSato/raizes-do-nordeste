package raizes_do_nordeste.domain.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Fidelizacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer saldoPontos;
	private boolean consentimento;
	@OneToOne
	private Usuario usuario;
	@OneToMany(mappedBy = "fidelizacao")
	private List<MovimentacaoFidelidade> movimentacoes = new ArrayList<>();
	
	//construtor
	public Fidelizacao() {
	}
	
	//getters e setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSaldoPontos() {
		return saldoPontos;
	}

	public void setSaldoPontos(Integer saldoPontos) {
		this.saldoPontos = saldoPontos;
	}

	public boolean isConsentimento() {
		return consentimento;
	}

	public void setConsentimento(boolean consentimento) {
		this.consentimento = consentimento;
	}
	
	//métodos
	public void adicionarPontos(Integer pontos) {
		
		if(pontos <= 0) {
			throw new IllegalArgumentException("A quantidade de pontos deve ser maior que zero");
		}
		
		saldoPontos += pontos;
	}
	
	public void utilizarPontos(Integer pontos) {
		if (pontos <= 0) {
			throw new IllegalArgumentException("A quantidade de pontos deve ser maior que zero");
		}

		if (!consentimento) {
			throw new IllegalArgumentException("Não autorizado a utilização dos pontos");
		}

		if (saldoPontos < pontos) {
			throw new IllegalArgumentException("A quantidade de pontos é insuficiente.");
		}

		saldoPontos -= pontos;
		
	}
	
}
