package raizes_do_nordeste.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class EstoqueProduto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer quantidade;
	@ManyToOne
	private Produto produto;
	@ManyToOne
	private Unidade unidade;
	
	//contrutor
	public EstoqueProduto() {
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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	//métodos
	public void adicionarQuantidade(Integer quantidadeAdicionar) {
		if(quantidadeAdicionar <=0) {
			throw new IllegalArgumentException("A quantidade adicionada deve ser maior que 0");
		}
		quantidade += quantidadeAdicionar;
	}
	public void removerQuantidade(Integer quantidadeRemover) {
		if(quantidadeRemover > quantidade) {
			throw new IllegalArgumentException("Quantidade de remoção é maior do que a quantidade atual");
		}
		quantidade -= quantidadeRemover;
	}
}
