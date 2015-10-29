package pe.egcc.cjava.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pe.egcc.cjava.model.ProductoBean;
import pe.egcc.cjava.model.ProductoBeanList;

/**
 * 
 * 
 * @author Gustavo Coronel
 * @blog   gcoronelc.blogspot.com
 *
 */
@Service
public class ProductoService {
  
  private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);
  
  private static String URL_BASE = "http://localhost:8080/spring-ws-rest-server/egcc/";
  
  private RestTemplate restTemplate = new RestTemplate();


  public List<ProductoBean> traerTodos() {
    
    // Prepare acceptable media type
    List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
    acceptableMediaTypes.add(MediaType.APPLICATION_XML);
    
    // Prepare header
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(acceptableMediaTypes);
    HttpEntity<ProductoBean> entity = new HttpEntity<ProductoBean>(headers);
    
    // Send the request as GET
    List<ProductoBean> lista = null;
    try {
      String url = URL_BASE + "productos";
      ResponseEntity<ProductoBeanList> result;
      result = restTemplate.exchange(url, HttpMethod.GET, entity, ProductoBeanList.class);
      lista = result.getBody().getData();
    } catch (Exception e) {
      logger.error(e.getMessage());
      throw new RuntimeException("Error en el acceso al servicio.");
    }
    return lista;
  }

  public void insertar(ProductoBean productoBean) {
    logger.debug("Agregando un nuevo producto.");
    
    // Prepare acceptable media type
    List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
    acceptableMediaTypes.add(MediaType.APPLICATION_XML);
    
    // Prepare header
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(acceptableMediaTypes);
    
    // Pass the new person and header
    HttpEntity<ProductoBean> entity = new HttpEntity<ProductoBean>(productoBean, headers);

    
    // Send the request as POST
    try {
      String url = URL_BASE + "producto";
      ResponseEntity<ProductoBean> result;
      result = restTemplate.exchange(url, HttpMethod.POST, entity, ProductoBean.class);
      System.out.println("Codigo: " + result.getBody().getCodigo() + " - " + result.getBody().getNombre());
      productoBean.setCodigo(result.getBody().getCodigo());
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

}
