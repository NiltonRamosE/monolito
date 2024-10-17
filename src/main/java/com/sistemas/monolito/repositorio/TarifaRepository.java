package com.sistemas.monolito.repositorio;

import com.sistemas.monolito.dominio.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
}
