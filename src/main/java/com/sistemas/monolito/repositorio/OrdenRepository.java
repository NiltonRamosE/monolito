package com.sistemas.monolito.repositorio;

import com.sistemas.monolito.dominio.Orden;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdenRepository extends JpaRepository<Orden, Long> {
	List<Orden> findByClienteId(Long id);
}
