package pe.egcc.cjava.dao.espec;

import java.util.List;

import pe.egcc.cjava.model.ProductoBean;

/**
 * 
 * 
 * @author Gustavo Coronel
 * @blog gcoronelc.blogspot.com
 *
 */
public interface ProductoMapper {

  /*
   * void actualizar(ProductoBean productoBean);
   * 
   * void eliminar(Integer idprod);
   */

  ProductoBean traerPorId(Integer idprod);

  void insertar(ProductoBean productoBean);

  List<ProductoBean> traerTodos();

}
