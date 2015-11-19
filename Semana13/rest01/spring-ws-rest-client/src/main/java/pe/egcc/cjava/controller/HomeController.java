package pe.egcc.cjava.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import pe.egcc.cjava.model.ProductoBean;
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

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String home(Locale locale, Model model) {
    logger.info("Cargando la pagina inicial.");
    return "home";
  }

 

  @RequestMapping(value = "conProductos.htm", method = RequestMethod.GET)
  public ModelAndView conProductos() {
    ModelAndView mv = new ModelAndView("conProductos");
    mv.addObject("lista", productoService.traerTodos());
    return mv;
  }

  @RequestMapping(value = "insProducto.htm", method = RequestMethod.GET)
  public ModelAndView insProducto() {
    ModelAndView mav = new ModelAndView("insProducto");
    return mav;
  }

  @RequestMapping(value = "insProductoGrabar.htm", method = RequestMethod.POST)
  public ModelAndView insProductoGrabar(@ModelAttribute("productoBean") ProductoBean productoBean) {
    ModelAndView mv = new ModelAndView("insProducto");
    try {
      // Transaccion
      productoService.insertar(productoBean);
      // Retorno
      mv.addObject("msg", "Proceso ok. ID = " + productoBean.getCodigo() + ".");
    } catch (Exception e) {
      mv.addObject("error", e.getMessage());
    }
    return mv;
  }

}
