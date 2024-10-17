package com.sistemas.monolito.servicio.impl;

import com.sistemas.monolito.dominio.Asignacion;
import com.sistemas.monolito.repositorio.AsignacionRepository;
import com.sistemas.monolito.servicio.AsignacionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AsignacionServiceImpl implements AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Override
    public Asignacion agregar(Asignacion asignacion) {
        return asignacionRepository.save(asignacion);
    }
    
    @Override
    @Transactional
    public List<Asignacion> listarTodos(){
    	List<Asignacion> asignaciones = asignacionRepository.findAll();
    	
    	asignaciones.stream().forEach((asignacion)->{
    		asignacion.getFase().equals(null);
    		if(asignacion.getEmpleado()!=null)
    			asignacion.getEmpleado().equals(null);
    	});
    	return asignaciones;
    }

    @Override
    @Transactional
    public Optional<Asignacion> buscar(Long id) {
        Optional<Asignacion> buscado = asignacionRepository.findById(id);
        buscado.ifPresent((asignacion) -> {
            asignacion.getOrden().equals(null);
            asignacion.getFase().equals(null);
            if (asignacion.getEmpleado() != null) {
                asignacion.getEmpleado().equals(null);
            }
        });

        return buscado;
    }
    
    @Override
    public Asignacion actualizar(Asignacion entidad) {
        return asignacionRepository.save(entidad);
    }

    @Override
    public void eliminar(Long id) {
        asignacionRepository.deleteById(id);
    }
    
	@Override
	@Transactional
	public List<Asignacion> listarPorOrdenId(Long ordenId) {
		List<Asignacion> lista = asignacionRepository.findByOrdenId(ordenId);
		
		lista.stream().forEach((asignacion)->{
			asignacion.getFase().equals(null);
			asignacion.getOrden().equals(null);
			if(asignacion.getEmpleado()!=null)
				asignacion.getEmpleado().equals(null);
			});
		Collections.sort(lista, (x, y)-> x.getId().compareTo(y.getId()));
		return lista;
	}
}
