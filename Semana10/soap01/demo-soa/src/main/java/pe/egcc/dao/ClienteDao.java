package pe.egcc.dao;

import java.util.ArrayList;
import java.util.List;

import pe.egcc.model.ClienteBean;

public class ClienteDao {
  
  public static ClienteDao getInstance(){
    return new ClienteDao();
  }
  
  public List<ClienteBean> getAll(){
    List<ClienteBean> lista = new ArrayList<ClienteBean>();
    lista.add(new ClienteBean(1000, "PEDRO RAMOS", "pramos@eureka.pe"));
    lista.add(new ClienteBean(1000, "CARLOS TORRES", "ctorres@eureka.pe"));
    lista.add(new ClienteBean(1000, "CLAUDIA FERNANDEZ", "cfernandez@eureka.pe"));
    lista.add(new ClienteBean(1000, "MARCO ALCANTARA", "malcantara@eureka.pe"));
    lista.add(new ClienteBean(1000, "ZULEMA AGUINAGA", "zaguinaga@eureka.pe"));
    lista.add(new ClienteBean(1000, "JUAN TENORIO", "jtenorio@eureka.pe"));
    return lista;
  }

}
