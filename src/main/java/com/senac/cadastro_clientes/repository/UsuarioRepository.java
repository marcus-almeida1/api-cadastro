package com.senac.cadastro_clientes.repository;

import com.senac.cadastro_clientes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
