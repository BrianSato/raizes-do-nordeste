package raizes_do_nordeste.api.dto;

public class ItemPedidoRequest {

	private Integer produtoId;
	private Integer quantidade;
	
	//getters e setters
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
