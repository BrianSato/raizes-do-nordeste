package raizes_do_nordeste.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import raizes_do_nordeste.domain.entity.Pedido;
import raizes_do_nordeste.domain.enums.CanalPedido;
import raizes_do_nordeste.domain.enums.StatusPedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	List<Pedido> findByStatus(StatusPedido status);
	
	List<Pedido> findByCanalPedido(CanalPedido canalPedido);
	
	List<Pedido> findByStatusAndCanalPedido(
			StatusPedido status,
			CanalPedido canalPedido
	);
}
