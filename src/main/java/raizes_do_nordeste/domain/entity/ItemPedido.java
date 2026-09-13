package raizes_do_nordeste.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer quantidade;
	private Double precoUnitario;
	@ManyToOne
	private Pedido pedido;
	@ManyToOne
	private Produto produto;
	
	//contrutor
	public ItemPedido() {
		super();
	}

	//getters e setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	//Métodos
	public double calcularSubtotal() {
		if (quantidade <= 0) {
		    throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
		}

		if (precoUnitario < 0) {
		    throw new IllegalArgumentException("O preço unitário não pode ser negativo.");
		}

		return quantidade * precoUnitario;
	}
	
}
