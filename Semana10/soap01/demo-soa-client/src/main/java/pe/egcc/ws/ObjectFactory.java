
package pe.egcc.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the pe.egcc.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ListaClientesResponse_QNAME = new QName("http://ws.egcc.pe/", "listaClientesResponse");
    private final static QName _ListaClientes_QNAME = new QName("http://ws.egcc.pe/", "listaClientes");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: pe.egcc.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListaClientes }
     * 
     */
    public ListaClientes createListaClientes() {
        return new ListaClientes();
    }

    /**
     * Create an instance of {@link ListaClientesResponse }
     * 
     */
    public ListaClientesResponse createListaClientesResponse() {
        return new ListaClientesResponse();
    }

    /**
     * Create an instance of {@link ClienteBean }
     * 
     */
    public ClienteBean createClienteBean() {
        return new ClienteBean();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaClientesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.egcc.pe/", name = "listaClientesResponse")
    public JAXBElement<ListaClientesResponse> createListaClientesResponse(ListaClientesResponse value) {
        return new JAXBElement<ListaClientesResponse>(_ListaClientesResponse_QNAME, ListaClientesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListaClientes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.egcc.pe/", name = "listaClientes")
    public JAXBElement<ListaClientes> createListaClientes(ListaClientes value) {
        return new JAXBElement<ListaClientes>(_ListaClientes_QNAME, ListaClientes.class, null, value);
    }

}
