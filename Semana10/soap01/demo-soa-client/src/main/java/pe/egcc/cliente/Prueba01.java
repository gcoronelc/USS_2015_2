package pe.egcc.cliente;

import java.util.List;

import pe.egcc.ws.ClienteBean;
import pe.egcc.ws.ClienteWS;
import pe.egcc.ws.ClienteWSService;

public class Prueba01 {

  public static void main(String[] args) {

    // Inicia la fabrica de proxies
    ClienteWSService clienteWSService = new ClienteWSService();

    // Obtener un proxy
    ClienteWS clienteWS = clienteWSService.getClienteWSPort();

    // Obtener lista
    List<ClienteBean> lista = clienteWS.listaClientes();

    // Mostrar datos
    for (ClienteBean clienteBean : lista) {
      System.out.println(clienteBean.getId() + " - " + clienteBean.getNombre() + " - " + clienteBean.getEmail());
    }
  }

}
