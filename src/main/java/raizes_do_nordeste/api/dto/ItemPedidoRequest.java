package raizes_do_nordeste.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemPedidoRequest {

	@NotNull(message = "produtoId é obrigatório")
	private Integer produtoId;
	@NotNull(message = "A quantidade é obrigatória")
	@Min(value = 1, message = "A quantidade deve ser maior que zero")
	private Integer quantidade;
	
	//GETTERS E SETTERS
	public Integer getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
