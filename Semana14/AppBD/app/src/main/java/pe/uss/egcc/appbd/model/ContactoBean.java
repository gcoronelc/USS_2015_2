package pe.uss.egcc.appbd.model;

/**
 * Created by Gustavo Coronel on 26/11/2015.
 */
public class ContactoBean {

  private int id;
  private String nombre;
  private String telefono;
  private String email;

  public ContactoBean() {
  }


  public ContactoBean(String nombre, String telefono, String email) {
    this.nombre = nombre;
    this.telefono = telefono;
    this.email = email;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
