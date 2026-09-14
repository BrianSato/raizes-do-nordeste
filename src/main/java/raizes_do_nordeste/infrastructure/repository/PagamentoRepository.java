package raizes_do_nordeste.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import raizes_do_nordeste.domain.entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento , Integer>{

}
