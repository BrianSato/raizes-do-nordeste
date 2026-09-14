package raizes_do_nordeste.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import raizes_do_nordeste.domain.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}
