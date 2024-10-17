package com.sistemas.monolito.controlador;

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
import com.sistemas.monolito.dominio.Cliente;
import com.sistemas.monolito.dominio.Orden;
import com.sistemas.monolito.servicio.AsignacionService;
import com.sistemas.monolito.servicio.ClienteService;
import com.sistemas.monolito.servicio.OrdenService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	@Autowired private ClienteService clienteService;
	@Autowired private OrdenService ordenService;
	@Autowired private AsignacionService asignacionService;
	
	@GetMapping({"/", "/index"})
	public String getIndex(Model model) {
		model.addAttribute("listaClientes", clienteService.listarTodos());
		return "cliente/clienteIndex";
	}
	
	@GetMapping("/nuevo")
	public String getClienteFormNew(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "cliente/clienteForm";
	}
	
	@PostMapping("/nuevo")
	public String postClienteFormNew(
		    @Valid @ModelAttribute("cliente") Cliente cliente, 
		    BindingResult bindingResult, 
		    RedirectAttributes redirectAttrs) {
	    if (bindingResult.hasErrors()) {
	        return "cliente/clienteForm";
	    }
	    clienteService.agregar(cliente);
	    redirectAttrs.addFlashAttribute("flash", "Agregado correctamente");
	    return "redirect:/cliente/index";
	}
	
	@GetMapping("/editar/{id}")
	public String getClienteFormEdit(
			@PathVariable("id") Long id,
			Model model) {
		Optional<Cliente> buscado = clienteService.buscar(id);
		model.addAttribute("cliente", 
				buscado.isPresent() ? buscado.get() : new Cliente());
		return "cliente/clienteForm";
	}
	
	@PostMapping("/editar")
	public String postClienteFormEdit(
			@Valid @ModelAttribute("cliente") Cliente cliente,
			BindingResult bindingResult,
			RedirectAttributes redirectAttrs) {
		if(bindingResult.hasErrors()) {
			return "cliente/clienteForm";
		}
		clienteService.actualizar(cliente);
		redirectAttrs.addFlashAttribute("flash", "Actualizado correctamente");
		return "redirect:/cliente/index";
	}
	
	@GetMapping("/eliminar/{id}")
	public String getClienteEliminar(
			@PathVariable("id") Long id,
			RedirectAttributes redirectAttrs) {
		clienteService.eliminar(id);
		redirectAttrs.addFlashAttribute("flash", "Eliminado correctamente");
		return "redirect:/cliente/index";
	}
	
	@GetMapping("/monitorear")
	public String getClienteMonitorear(Model model) {
		model.addAttribute("listaClientes", clienteService.listarTodos());
		model.addAttribute("cliente", new Cliente());
		return "cliente/clienteMonitorForm";
	}
	
	
	@PostMapping("/monitorear/lista")
	public String getMonitorearLista(
	    @ModelAttribute("cliente") Cliente cliente, 
	    Model model) {
	    Optional<Cliente> buscado = clienteService.buscar(cliente.getId());
	    if (buscado.isPresent()) {
	        model.addAttribute("cliente", buscado.get());
	        model.addAttribute("listaOrdenes", ordenService.listarPorClienteId(cliente.getId()));
	        return "cliente/clienteMonitorIndex";
	    } else {
	        return "redirect:/cliente/monitorear";
	    }
	}

	@GetMapping("/monitorear/orden/{id}")
	public String getMonitorearDetalle(
	    @PathVariable("id") Long id, 
	    Model model) {
	    Optional<Orden> buscado = ordenService.buscar(id);
	    List<Asignacion> asignaciones = asignacionService.listarPorOrdenId(id); 
	    model.addAttribute("orden", buscado.get());
	    model.addAttribute("listaAsignaciones", asignaciones);
	    return "cliente/clienteMonitorDetalle";
	}
}
