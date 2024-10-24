package com.sistemas.monolito.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistemas.monolito.dominio.Asignacion;
import com.sistemas.monolito.dominio.Fase;
import com.sistemas.monolito.dominio.Orden;
import com.sistemas.monolito.servicio.ClienteService;
import com.sistemas.monolito.servicio.FaseService;
import com.sistemas.monolito.servicio.OrdenService;
import com.sistemas.monolito.servicio.TarifaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/orden")
public class OrdenController {
	@Autowired private OrdenService ordenService; 
	@Autowired private ClienteService clienteService; 
	@Autowired private TarifaService tarifaService; 
	@Autowired private FaseService faseService;

	@GetMapping({"/", "/index"})
	public String getIndex(Model model) {
	    model.addAttribute("listaOrdenes", ordenService.listarTodos());
	    return "orden/ordenIndex";
	}

	@GetMapping("/nuevo")
	public String getOrdenFormNew(Model model) {
	    Orden orden = new Orden();
	    orden.setFechaOrden(new Date()); 
	    orden.setFechaEntrega(new Date());
	    model.addAttribute("orden", orden);
	    model.addAttribute("listaClientes", clienteService.listarTodos()); 
	    model.addAttribute("listaTarifas", tarifaService.listarTodos());
	    return "orden/ordenForm";
	}
	
	@PostMapping("/nuevo")
	public String postOrdenFormNew(
	    @Valid @ModelAttribute("orden") Orden orden, 
	    BindingResult bindingResult,
	    Model model,
	    RedirectAttributes redirectAttributes) {
	    List<Asignacion> asignaciones = new ArrayList<Asignacion>();
	    
	    if (bindingResult.hasErrors()) {
	        // En caso de errores regresa al formulario
	        model.addAttribute("listaClientes", clienteService.listarTodos()); 
	        model.addAttribute("listaTarifas", tarifaService.listarTodos());
	        return "orden/ordenForm";
	    }
	    
	    // Agregar Asignaciones a la orden
	    for (Fase fase : faseService.listarTodos()) { 
	        Asignacion asignacion = new Asignacion();
	        asignacion.setOrden(orden);
	        asignacion.setFase(fase);
	        asignaciones.add(asignacion);
	    }
	    
	    orden.setAsignaciones(asignaciones); 
	    ordenService.agregar(orden);
	    redirectAttributes.addFlashAttribute("flash", "Agregado correctamente");
	    return "redirect:/orden/index";
	}
	
	@GetMapping("/editar/{id}")
	public String getOrdenFormEdit(
	    @PathVariable("id") Long id, 
	    Model model) {
	    Optional<Orden> buscado = ordenService.buscar(id);
	    if (buscado.isPresent()) {
	        model.addAttribute("orden", buscado.get());
	    } else {
	        model.addAttribute("orden", new Orden());
	    }
	    model.addAttribute("listaClientes", clienteService.listarTodos()); 
	    model.addAttribute("listaTarifas", tarifaService.listarTodos());
	    return "orden/ordenForm";
	}

	@PostMapping("/editar")
	private String postOrdenFormEdit(
	    @Valid @ModelAttribute("orden") Orden orden, 
	    BindingResult bindingResult,
	    Model model,
	    RedirectAttributes redirectAttributes) {
	    if (bindingResult.hasErrors()) {
	        // En caso de error regresa al formulario
	        model.addAttribute("listaClientes", clienteService.listarTodos()); 
	        model.addAttribute("listaTarifas", tarifaService.listarTodos());
	        return "orden/ordenForm";
	    }
	    ordenService.agregar(orden);
	    redirectAttributes.addFlashAttribute("flash", "Modificado correctamente");
	    return "redirect:/orden/index";
	}
	
	@GetMapping("/eliminar/{id}")
	private String getOrdenEliminar(
	    @PathVariable("id") Long id,
	    RedirectAttributes redirectAttributes) {
	    ordenService.eliminar(id);
	    redirectAttributes.addFlashAttribute("flash", "Eliminado correctamente");
	    return "redirect:/orden/index";
	}




}
