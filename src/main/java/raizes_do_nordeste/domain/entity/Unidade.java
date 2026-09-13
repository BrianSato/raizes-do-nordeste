package raizes_do_nordeste.domain.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Unidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String endereco;
	@OneToMany(mappedBy = "unidade")
	private List<EstoqueProduto> estoques = new ArrayList<>();

	//contrutor
	public Unidade() {
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public List<EstoqueProduto> getEstoques() {
		return estoques;
	}

	public void setEstoques(List<EstoqueProduto> estoques) {
		this.estoques = estoques;
	}
	
	
}
