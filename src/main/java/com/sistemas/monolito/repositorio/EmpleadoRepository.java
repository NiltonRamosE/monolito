package com.sistemas.monolito.repositorio;

import com.sistemas.monolito.dominio.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}
