package raizes_do_nordeste.application.service;

import org.springframework.stereotype.Service;

import raizes_do_nordeste.domain.entity.Pedido;
import raizes_do_nordeste.infrastructure.repository.PedidoRepository;

@Service
public class PedidoService {
	
	private final PedidoRepository pedidoRepository;
	
	//construtor
	public PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}
	
	//métodos
	public Pedido buscarPorId(Integer id) {
		return pedidoRepository.findById(id).orElse(null);
	}
	public Pedido salvar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
	public Pedido criarPedido(Pedido pedido) {
		pedido.calcularTotal();
		return pedidoRepository.save(pedido);
	}
}
