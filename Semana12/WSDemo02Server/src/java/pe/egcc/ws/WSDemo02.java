package pe.egcc.ws;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;


/**
 *
 * @author Gustavo Coronel
 * @blog http://gcoronelc.blogspot.pe
 */
@WebService(serviceName = "WSDemo02")
public class WSDemo02 {

  @WebMethod(operationName = "hello")
  public String hello(@WebParam(name = "name") String name) {
    return "Hello " + name + " !";
  }
  
  @WebMethod(operationName = "sumar")
  public int sumar(@WebParam(name = "n1") int n1,@WebParam(name = "n2") int n2){
    return (n1+n2);
  }
}
