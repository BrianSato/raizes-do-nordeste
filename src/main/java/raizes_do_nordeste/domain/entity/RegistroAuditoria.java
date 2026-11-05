package raizes_do_nordeste.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import raizes_do_nordeste.domain.enums.AcaoAuditoria;
import raizes_do_nordeste.domain.enums.ResultadoAuditoria;

@Entity
public class RegistroAuditoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Enumerated(EnumType.STRING)
	private AcaoAuditoria acao;
	private LocalDateTime dataHora;
	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private Pedido pedido;
	private String statusAnterior;
	private String statusNovo;
	@Enumerated(EnumType.STRING)
	private ResultadoAuditoria resultado;
	
	//CONSTRUTOR
	public RegistroAuditoria() {
		
	}
	
	public RegistroAuditoria(AcaoAuditoria acao, LocalDateTime dataHora, Usuario usuario, Pedido pedido,
			String statusAnterior, String statusNovo, ResultadoAuditoria resultado) {
		this.acao = acao;
		this.dataHora = dataHora;
		this.usuario = usuario;
		this.pedido = pedido;
		this.statusAnterior = statusAnterior;
		this.statusNovo = statusNovo;
		this.resultado = resultado;
	}

	//GETTERS E SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		id = id;
	}

	public AcaoAuditoria getAcao() {
		return acao;
	}

	public void setAcao(AcaoAuditoria acao) {
		this.acao = acao;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public String getStatusAnterior() {
		return statusAnterior;
	}

	public void setStatusAnterior(String statusAnterior) {
		this.statusAnterior = statusAnterior;
	}

	public String getStatusNovo() {
		return statusNovo;
	}

	public void setStatusNovo(String statusNovo) {
		this.statusNovo = statusNovo;
	}

	public ResultadoAuditoria getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoAuditoria resultado) {
		this.resultado = resultado;
	}
	
}
