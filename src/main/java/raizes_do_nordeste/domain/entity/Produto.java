package raizes_do_nordeste.domain.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;
	private LocalDate validade;
	@OneToMany(mappedBy = "produto")
	private List<ItemPedido> itens = new ArrayList<>();
	@OneToMany(mappedBy = "produto")
	private List<EstoqueProduto> estoques = new ArrayList<>();
	
	//contrutor
	public Produto() {
	}

	//getters e setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public LocalDate getValidade() {
		return validade;
	}

	public void setValidade(LocalDate validade) {
		this.validade = validade;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}

	public List<EstoqueProduto> getEstoques() {
		return estoques;
	}

	public void setEstoques(List<EstoqueProduto> estoques) {
		this.estoques = estoques;
	}
	
	
	
}
