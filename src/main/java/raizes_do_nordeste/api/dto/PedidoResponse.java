package raizes_do_nordeste.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import raizes_do_nordeste.domain.enums.CanalPedido;
import raizes_do_nordeste.domain.enums.StatusPedido;

public class PedidoResponse {

	private Integer id;
	private CanalPedido canalPedido;
	private LocalDateTime dataHora;
	private StatusPedido status;
	private Double valorTotal;
	private Integer usuarioId;
	private Integer unidadeId;
	private List<ItemPedidoResponse> itens;
	
	//getters e setters
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CanalPedido getCanalPedido() {
		return canalPedido;
	}
	public void setCanalPedido(CanalPedido canalPedido) {
		this.canalPedido = canalPedido;
	}
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	public StatusPedido getStatus() {
		return status;
	}
	public void setStatus(StatusPedido status) {
		this.status = status;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
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
	public List<ItemPedidoResponse> getItens() {
		return itens;
	}
	public void setItens(List<ItemPedidoResponse> itens) {
		this.itens = itens;
	}
	
}
