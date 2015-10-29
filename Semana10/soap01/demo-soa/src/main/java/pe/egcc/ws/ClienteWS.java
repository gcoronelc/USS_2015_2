package pe.egcc.ws;

import java.util.List;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import pe.egcc.dao.ClienteDao;
import pe.egcc.model.ClienteBean;

@WebService  // JAX-WS
public class ClienteWS {

  public List<ClienteBean> listaClientes(){
    return ClienteDao.getInstance().getAll();
  }
  
  public static void main(String[] args) {
    Endpoint.publish("http://localhost:8080/clientews", new ClienteWS());
    System.out.println("Servicio inicializado!");
  
  }
}
