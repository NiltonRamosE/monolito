package com.sistemas.monolito.controlador;

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

import com.sistemas.monolito.dominio.Empleado;
import com.sistemas.monolito.servicio.EmpleadoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("empleado")
public class EmpleadoController {
	@Autowired private EmpleadoService empleadoService;

	@GetMapping({"/", "/index"})
	public String getIndex(Model model) {
	    model.addAttribute("listaEmpleados", empleadoService.listarTodos());
	    return "empleado/empleadoIndex";
	}

	@GetMapping("/nuevo")
	public String getEmpleadoFormNew(Model model) {
	    model.addAttribute("empleado", new Empleado());
	    return "empleado/empleadoForm";
	}
	
	@PostMapping("/nuevo")
	public String postEmpleadoFormNew(
	    @Valid @ModelAttribute("empleado") Empleado empleado, 
	    BindingResult bindingResult, 
	    RedirectAttributes redirectAttrs) {
	    if (bindingResult.hasErrors()) {
	        // Si hubo errores muestra el formulario para corregir
	        return "empleado/empleadoForm";
	    }
	    empleadoService.agregar(empleado);
	    redirectAttrs.addFlashAttribute("flash", "Agregado correctamente");
	    return "redirect:/empleado/index";
	}

	@GetMapping("/editar/{id}")
	public String getEmpleadoFormEdit(
	    @PathVariable("id") Long id, 
	    Model model) {
	    Optional<Empleado> buscado = empleadoService.buscar(id);
	    model.addAttribute("empleado", buscado.isPresent() ? buscado.get() : new Empleado());
	    return "empleado/empleadoForm";
	}

	@PostMapping("/editar")
	public String postEmpleadoFormEdit(
	    @Valid @ModelAttribute("empleado") Empleado empleado, 
	    BindingResult bindingResult, 
	    RedirectAttributes redirectAttrs) {
	    if (bindingResult.hasErrors()) {
	        // En caso de errores muestra el formulario para corregir
	        return "empleado/empleadoForm";
	    }
	    empleadoService.actualizar(empleado);
	    redirectAttrs.addFlashAttribute("flash", "Actualizado correctamente");
	    return "redirect:/empleado/index";
	}

	@GetMapping("/eliminar/{id}")
	public String getEmpleadoEliminar(
	    @PathVariable("id") Long id, 
	    RedirectAttributes redirectAttrs) {
	    empleadoService.eliminar(id);
	    redirectAttrs.addFlashAttribute("flash", "Eliminado correctamente");
	    return "redirect:/empleado/index";
	}

	
	

}
