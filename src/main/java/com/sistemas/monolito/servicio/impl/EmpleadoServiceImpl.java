package com.sistemas.monolito.servicio.impl;

import com.sistemas.monolito.dominio.Empleado;
import com.sistemas.monolito.repositorio.EmpleadoRepository;
import com.sistemas.monolito.servicio.EmpleadoService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

	@Override
	public Empleado agregar(Empleado entidad) {
		return empleadoRepository.save(entidad);
	}

	@Override
	public List<Empleado> listarTodos() {
		return empleadoRepository.findAll();
	}

	@Override
	public Optional<Empleado> buscar(Long id) {
		return empleadoRepository.findById(id);
	}

	@Override
	public Empleado actualizar(Empleado entidad) {
		return empleadoRepository.save(entidad);
	}

	@Override
	public void eliminar(Long id) {
		empleadoRepository.deleteById(id);
	}
}
