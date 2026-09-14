package raizes_do_nordeste.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import raizes_do_nordeste.domain.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
