package com.sistemas.monolito.servicio.impl;

import com.sistemas.monolito.dominio.Orden;
import com.sistemas.monolito.repositorio.OrdenRepository;
import com.sistemas.monolito.servicio.OrdenService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdenServiceImpl implements OrdenService {
    @Autowired
    private OrdenRepository ordenRepository;

    @Override
    public Orden agregar(Orden entidad) {
        return ordenRepository.save(entidad);
    }

    @Override
    @Transactional
    public List<Orden> listarTodos() {
    	List<Orden> listado = ordenRepository.findAll(
    			Sort.by(Sort.Direction.ASC, "id"));
    	listado.forEach((orden)-> orden.getCliente().equals(null));
        return listado;
    }
    
    @Override
    public Optional<Orden> buscar(Long id) {
    	Optional<Orden> buscado = ordenRepository.findById(id);
        return buscado;
    }
    
    @Override
    public Orden actualizar(Orden entidad) {
        return ordenRepository.save(entidad);
    }

    @Override
    public void eliminar(Long id) {
        ordenRepository.deleteById(id);
    }

	@Override
	public List<Orden> listarPorClienteId(Long clienteId) {
		return ordenRepository.findByClienteId(clienteId);
	}
}
