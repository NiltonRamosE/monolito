package com.sistemas.monolito.repositorio;

import com.sistemas.monolito.dominio.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
