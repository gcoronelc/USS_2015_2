package pe.uss.ttc.trabajito.model;

/**
 * Created by Bryan on 11/11/2015.
 */
public class Cuenta {
    String codigo;
    String moneda;
    String sucursalApert;
    String empUsuaApert;
    String clientPaterno;
    String clientMaterno;
    String clientNombre;
    String clientDni;
    String clientMail;
    String estado;
    Double Saldo;

    public Cuenta() {
    }

    public Cuenta(String codigo, String moneda, String sucursalApert, String empUsuaApert, String clientPaterno, String clientMaterno, String clientNombre, String clientDni, String clientMail, String estado, Double saldo) {
        this.codigo = codigo;
        this.moneda = moneda;
        this.sucursalApert = sucursalApert;
        this.empUsuaApert = empUsuaApert;
        this.clientPaterno = clientPaterno;
        this.clientMaterno = clientMaterno;
        this.clientNombre = clientNombre;
        this.clientDni = clientDni;
        this.clientMail = clientMail;
        this.estado = estado;
        this.Saldo = saldo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getSucursalApert() {
        return sucursalApert;
    }

    public void setSucursalApert(String sucursalApert) {
        this.sucursalApert = sucursalApert;
    }

    public String getEmpUsuaApert() {
        return empUsuaApert;
    }

    public void setEmpUsuaApert(String empUsuaApert) {
        this.empUsuaApert = empUsuaApert;
    }

    public String getClientPaterno() {
        return clientPaterno;
    }

    public void setClientPaterno(String clientPaterno) {
        this.clientPaterno = clientPaterno;
    }

    public String getClientMaterno() {
        return clientMaterno;
    }

    public void setClientMaterno(String clientMaterno) {
        this.clientMaterno = clientMaterno;
    }

    public String getClientNombre() {
        return clientNombre;
    }

    public void setClientNombre(String clientNombre) {
        this.clientNombre = clientNombre;
    }

    public String getClientDni() {
        return clientDni;
    }

    public void setClientDni(String clientDni) {
        this.clientDni = clientDni;
    }

    public String getClientMail() {
        return clientMail;
    }

    public void setClientMail(String clientMail) {
        this.clientMail = clientMail;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getSaldo() {
        return Saldo;
    }

    public void setSaldo(Double saldo) {
        Saldo = saldo;
    }
}
