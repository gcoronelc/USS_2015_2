package pe.uss.egcc.ws;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
/**
 *
 * @author Gustavo Coronel
 */
@WebService
public class WSServer {

  public String saludo(String nombre){
    return "Hola " + nombre;
  }
  
  public int sumar(int n1, int n2){
    return (n1 + n2);
  }
  
  public static void main(String[] args) {
    Endpoint.publish("http://localhost:8085/WSServer", new WSServer());
    System.out.println("Servicio inicializado.");
  }
  
}
