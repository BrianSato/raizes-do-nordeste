package raizes_do_nordeste.api.dto;

public class FidelizacaoResponse {

	private Integer id;
	private Integer saldoPontos;
	private boolean consentimento;
	private Integer usuarioId;
	
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
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

}
