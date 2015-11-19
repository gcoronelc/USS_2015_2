package pe.egcc.uss.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.egcc.uss.model.ProductoBean;
import pe.egcc.uss.service.ProductoService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProductoService productoService;
	
	@RequestMapping(value = "/producto/{id}", method = RequestMethod.GET, 
	headers = "Accept=application/json")
	@ResponseBody
	public ProductoBean traerProducto(@PathVariable("id") Integer codigo){
	  return productoService.traerProducto(codigo);
	}
	
}
