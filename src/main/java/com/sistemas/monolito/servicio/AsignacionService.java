package com.sistemas.monolito.servicio;

import java.util.List;

import com.sistemas.monolito.dominio.Asignacion;

public interface AsignacionService extends iGenericoService<Asignacion, Long> {
	public List<Asignacion> listarPorOrdenId(Long ordenId);
}
