package pe.egcc.uss.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import pe.egcc.uss.model.ProductoBean;

@Service
public class ProductoService extends AbstractService{

  @SuppressWarnings("unchecked")
  public ProductoBean traerProducto(Integer codigo){
    ProductoBean bean;
    String sql = "select codigo, nombre, precio, stock from producto where codigo = ?";
    Object[] args = {codigo};
    try{
      bean = jdbcTemplate.queryForObject(sql, args, new BeanPropertyRowMapper(ProductoBean.class));
    } catch(EmptyResultDataAccessException e){
      bean = new ProductoBean();
      bean.setCodigo(-1);
    }
    return bean;
  }
  
  
}
