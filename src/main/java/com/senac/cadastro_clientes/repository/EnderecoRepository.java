package com.senac.cadastro_clientes.repository;

import com.senac.cadastro_clientes.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Integer countClienteById(Long id);
}
