package pe.egcc.cjava.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Gustavo Coronel
 * @blog gcoronelc.blogspot.com
 *
 */
@XmlRootElement(name="producto")
public class ProductoBean {

  private Integer codigo;
  private String nombre;
  private Double precio;
  private Integer stock;

  public ProductoBean() {
  }

  public ProductoBean(String nombre, Double precio, Integer stock) {
    super();
    this.nombre = nombre;
    this.precio = precio;
    this.stock = stock;
  }

  public Integer getCodigo() {
    return codigo;
  }

  public void setCodigo(Integer codigo) {
    this.codigo = codigo;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Double getPrecio() {
    return precio;
  }

  public void setPrecio(Double precio) {
    this.precio = precio;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }
  
}
