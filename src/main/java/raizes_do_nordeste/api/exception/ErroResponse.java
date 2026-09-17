package raizes_do_nordeste.api.exception;

import java.time.LocalDateTime;

public class ErroResponse {

	private LocalDateTime timestamp;
	private Integer status;
	private String erro;
	private String mensagem;
	
	//construtor
	public ErroResponse(){	
	}

	public ErroResponse(
			LocalDateTime timestamp, 
			Integer status, 
			String erro, 
			String mensagem) {
		
		this.timestamp = timestamp;
		this.status = status;
		this.erro = erro;
		this.mensagem = mensagem;
	}

	//getters e setters
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
