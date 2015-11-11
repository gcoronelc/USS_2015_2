package pe.egcc.cjava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.egcc.cjava.dao.espec.ProductoMapper;
import pe.egcc.cjava.model.ProductoBean;

/**
 * 
 * 
 * @author Gustavo Coronel
 * @blog   gcoronelc.blogspot.com
 *
 */
@Service
public class ProductoService {

  @Autowired
  private ProductoMapper productoMapper;

  public List<ProductoBean> traerTodos() {
    return productoMapper.traerTodos();
  }
  
  public ProductoBean traerPorId(Integer idprod) {
    return productoMapper.traerPorId(idprod);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void insertar(ProductoBean productoBean) {
    productoMapper.insertar(productoBean);
  }

}
