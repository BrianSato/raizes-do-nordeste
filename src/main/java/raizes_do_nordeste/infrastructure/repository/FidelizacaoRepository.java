package raizes_do_nordeste.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import raizes_do_nordeste.domain.entity.Fidelizacao;
import raizes_do_nordeste.domain.entity.Usuario;

public interface FidelizacaoRepository extends JpaRepository<Fidelizacao, Integer>{
	
	Optional<Fidelizacao> findByUsuario(Usuario usuario);
}
