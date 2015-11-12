package pe.uss.ttc.trabajito.model;

/**
 * Created by Bryan on 11/11/2015.
 */
public class Movimiento {

    String movTipo;
    String movFecha;
    String movImporte;

    public Movimiento() {
    }

    public Movimiento(String movTipo, String movFecha, String movImporte) {
        this.movTipo = movTipo;
        this.movFecha = movFecha;
        this.movImporte = movImporte;
    }

    public String getMovTipo() {
        return movTipo;
    }

    public void setMovTipo(String movTipo) {
        this.movTipo = movTipo;
    }

    public String getMovFecha() {
        return movFecha;
    }

    public void setMovFecha(String movFecha) {
        this.movFecha = movFecha;
    }

    public String getMovImporte() {
        return movImporte;
    }

    public void setMovImporte(String movImporte) {
        this.movImporte = movImporte;
    }

    /*@Override
    public String toString() {
        return   movFecha+"     "+movImporte+"     "+movTipo;
    }*/
}
