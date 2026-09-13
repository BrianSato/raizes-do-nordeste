package raizes_do_nordeste.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class MovimentacaoFidelidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String tipo;
	private Integer pontos;
	private LocalDateTime dataHora;
	@ManyToOne
	private Fidelizacao fidelizacao;
	
	//contrutor
	public MovimentacaoFidelidade() {
	}

	//getters e setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getPontos() {
		return pontos;
	}

	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public Fidelizacao getFidelizacao() {
		return fidelizacao;
	}

	public void setFidelizacao(Fidelizacao fidelizacao) {
		this.fidelizacao = fidelizacao;
	}
	
	
	
	
}
