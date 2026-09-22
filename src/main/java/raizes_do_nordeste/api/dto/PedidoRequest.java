package raizes_do_nordeste.api.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import raizes_do_nordeste.domain.enums.CanalPedido;

public class PedidoRequest {
	
	@NotNull(message = "canalPedido é obrigatório")
	private CanalPedido canalPedido;
	private Integer usuarioId;
	private Integer unidadeId;
	private List<ItemPedidoRequest> itens;

	//getters e setters
	public List<ItemPedidoRequest> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedidoRequest> itens) {
		this.itens = itens;
	}

	public CanalPedido getCanalPedido() {
		return canalPedido;
	}

	public void setCanalPedido(CanalPedido canalPedido) {
		this.canalPedido = canalPedido;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getUnidadeId() {
		return unidadeId;
	}

	public void setUnidadeId(Integer unidadeId) {
		this.unidadeId = unidadeId;
	}
	
}
