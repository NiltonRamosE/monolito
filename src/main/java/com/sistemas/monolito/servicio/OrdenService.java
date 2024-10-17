package com.sistemas.monolito.servicio;

import java.util.List;

import com.sistemas.monolito.dominio.Orden;

public interface OrdenService extends iGenericoService<Orden, Long> {
	public List<Orden> listarPorClienteId(Long clienteId);
}
