package raizes_do_nordeste.api.dto;

public class FidelizacaoRequest {

	private Integer usuarioId;
	private boolean consentimento;
	
	//getters e setters
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public boolean isConsentimento() {
		return consentimento;
	}
	public void setConsentimento(boolean consentimento) {
		this.consentimento = consentimento;
	}
}
