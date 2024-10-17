package com.sistemas.monolito.repositorio;

import com.sistemas.monolito.dominio.Asignacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {
	public List<Asignacion> findByOrdenId(Long id);
}
