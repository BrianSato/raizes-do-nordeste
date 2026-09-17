package raizes_do_nordeste.api.dto;

public class PagamentoRequest {
	
	private Integer pedidoId;
	private String formaPagamento;
	
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
	
}
