package raizes_do_nordeste.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import raizes_do_nordeste.domain.entity.EstoqueProduto;
import raizes_do_nordeste.domain.entity.Produto;
import raizes_do_nordeste.domain.entity.Unidade;

public interface EstoqueProdutoRepository extends JpaRepository<EstoqueProduto, Integer>{

	Optional<EstoqueProduto> findByProdutoAndUnidade(
			Produto produto,
			Unidade unidade
	);
}
