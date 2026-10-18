package raizes_do_nordeste.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PagamentoRequest {
	
	@NotNull(message = "pedido é obrigatório")
	private Integer pedidoId;
	@NotBlank(message = "formaPagamento é obrigatória")
	private String formaPagamento;
	
	@NotNull(message = "O campo aprovado é obrigatório")
	private Boolean aprovado;
	
	//getters e setters
	public Integer getPedidoId() {
		return pedidoId;
	}
	public void setPedidoId(Integer pedidoId) {
		this.pedidoId = pedidoId;
	}
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public Boolean getAprovado() {
		return aprovado;
	}
	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}
	
}
