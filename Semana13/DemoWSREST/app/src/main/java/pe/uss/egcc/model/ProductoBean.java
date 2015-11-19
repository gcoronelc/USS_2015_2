package pe.uss.egcc.model;

/**
 * Created by Gustavo Coronel on 19/11/2015.
 */
public class ProductoBean {

  private Integer codigo;
  private String nombre;
  private Double precio;
  private Double stock;

  public ProductoBean() {
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

  public Double getStock() {
    return stock;
  }

  public void setStock(Double stock) {
    this.stock = stock;
  }

}
