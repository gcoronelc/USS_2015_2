package pe.egcc.cjava.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pe.egcc.cjava.model.ProductoBean;
import pe.egcc.cjava.model.ProductoBeanList;
import pe.egcc.cjava.service.ProductoService;

/**
 * 
 * 
 * @author Gustavo Coronel
 * @blog gcoronelc.blogspot.com
 *
 */

@Controller
public class HomeController {

  private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

  @Autowired
  private ProductoService productoService;

  
  @RequestMapping(value = "/productos", method = RequestMethod.GET, 
       headers = "Accept=application/json")
  @ResponseBody
  public ProductoBeanList conProductos() {
    logger.debug("Atendiendo solicitud de todos los productos.");
    ProductoBeanList productoBeanList = new ProductoBeanList();
    productoBeanList.setData(productoService.traerTodos());
    return productoBeanList;
  }

  

  
  @RequestMapping(value = "/producto/{id}", method = RequestMethod.GET, 
  headers = "Accept=application/json,application/xml")
  public @ResponseBody ProductoBean getProducto(@PathVariable("id") Integer id) {
    logger.debug("Atendiendo solicitud de producto con id: " + id);
    return productoService.traerPorId(id);
  }

  @RequestMapping(value = "/producto", method = RequestMethod.POST, 
  headers = "Accept=application/json, application/xml")
  public @ResponseBody ProductoBean insProductoGrabar(@RequestBody ProductoBean productoBean) {
    logger.debug("Atendiendo solicitud para agregar un nuevo producto.");
    try {
      // Transaccion
      productoService.insertar(productoBean);
    } catch (Exception e) {
      logger.error(e.getMessage());
      productoBean.setCodigo(-1);
    }
    return productoBean;
  }

}
