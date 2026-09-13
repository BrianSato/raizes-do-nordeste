package raizes_do_nordeste.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import raizes_do_nordeste.domain.enums.CanalPedido;
import raizes_do_nordeste.domain.enums.StatusPedido;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Enumerated(EnumType.STRING)
	private CanalPedido canalPedido;
	private LocalDateTime dataHora;
	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.AGUARDANDO_PAGAMENTO;
	private Double valorTotal;
	@ManyToOne
	private Usuario usuario;
	@ManyToOne
	private Unidade unidade;
	@OneToOne(mappedBy = "pedido")
	private Pagamento pagamento;
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens = new ArrayList<>();
	
	//construtor
	public Pedido() {
	}
	
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
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	public List<ItemPedido> getItens() {
		return itens;
	}
	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}
	
	//Métodos
	public void adicionarItem(ItemPedido item) {
		itens.add(item);
		item.setPedido(this);
	}
	public void atualizarStatus(StatusPedido novoStatus) {
		status = novoStatus;
	}
	public double calcularTotal() {
		double total = 0;
		for(ItemPedido item : itens) {
			total += item.calcularSubtotal();
		}
		valorTotal = total;
		return total;
	}
	public void cancelar() {
		if(status == StatusPedido.PRONTO 
				|| status == StatusPedido.ENTREGUE 
				|| status == StatusPedido.CANCELADO) {
			throw new IllegalArgumentException("Pedido não pode ser cancelado");
		}
		status = StatusPedido.CANCELADO;
	}

}
