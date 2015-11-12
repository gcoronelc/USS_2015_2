package com.example.guevaracabrera.cuentabancario.LlenadoDatos;

import android.content.Context;
import android.widget.Toast;

import com.example.guevaracabrera.cuentabancario.Conexion;
import com.example.guevaracabrera.cuentabancario.Entidaes.Cliente;
import com.example.guevaracabrera.cuentabancario.Entidaes.Cuenta;
import com.example.guevaracabrera.cuentabancario.Entidaes.Empleado;
import com.example.guevaracabrera.cuentabancario.Entidaes.Moneda;
import com.example.guevaracabrera.cuentabancario.Entidaes.Movimiento;
import com.example.guevaracabrera.cuentabancario.Entidaes.Tipomovimiento;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alehexis on 11/11/2015.
 */
public class BDDao {

    public Cuenta BuscarCuenta(Context applicationContext, String NCuenta) {
        Cuenta cuenta = null;
        try {
            String url = Conexion.URL_APP + "ConsultarCuenta.php?Idcuenta=" + NCuenta;
            String jsonResult = Conexion.getConexion(url);
            JSONObject object = new JSONObject(jsonResult);
            String estado = object.getString("estado");
            if (estado.equals("0")) {
                throw new Exception("Dato incorrecto.");
            }
            cuenta = new Cuenta();
            cuenta.setChrCuencodigo(object.getString("chr_cuencodigo"));
            cuenta.setChrCuenclave(object.getString("chr_cuenclave"));
            cuenta.setDecCuensaldo(object.getDouble("dec_cuensaldo"));
            cuenta.setVchCuenestado(object.getString("vch_cuenestado"));
            Moneda moneda = new Moneda();
            moneda.setChrMonecodigo(object.getString("chr_monecodigo"));
            moneda.setVchMonedescripcion(object.getString("vch_monedescripcion"));
            cuenta.setMoneda(moneda);
            Cliente cliente = new Cliente();
            cliente.setChrCliecodigo(object.getString("chr_cliecodigo"));
            cliente.setChrCliedni(object.getString("chr_cliedni"));
            cliente.setVchClienombre(object.getString("vch_clienombre"));
            cliente.setVchCliepaterno(object.getString("vch_cliepaterno"));
            cliente.setVchCliematerno(object.getString("vch_cliematerno"));
            cuenta.setCliente(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cuenta;
    }

    public void Retiro(String cuenta, String idempleado, String cantidad){
        try {
            String url = Conexion.URL_APP + "HacerRetiro.php?idcuenta="+cuenta+"&idempleado="+idempleado+"&monto="+cantidad;
            String jsonResult = Conexion.getConexion(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Deposito(String cuenta, String idempleado, String cantidad){
        try {
            String url = Conexion.URL_APP + "HacerDeposito.php?idcuenta="+cuenta+"&idempleado="+idempleado+"&monto="+cantidad;
            String jsonResult = Conexion.getConexion(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public synchronized List<Movimiento> ListaMovimientosEmpleado(Context applicationContext,String IdEmpleado) {
        List<Movimiento> lista = null;
        try {
            String url = Conexion.URL_APP + "ListaMovimientosEmpleado.php?IdEmpleado="+IdEmpleado;
            String jsonResult = Conexion.getConexion(url);
            if (jsonResult.isEmpty()) {
                lista = new ArrayList<Movimiento>();
            } else {
                JSONObject json = new JSONObject(jsonResult);
                JSONArray jArray = json.getJSONArray("movimientos");
                lista = new ArrayList<>();
                Movimiento map = null;
                for (int i = 0; i < jArray.length(); i++) {
                    map = new Movimiento();
                    JSONObject e = jArray.getJSONObject(i);
                    map.setCuenta(new Cuenta(e.getString("chr_cuencodigo")));
                    map.setDecMoviimporte(e.getDouble("dec_moviimporte"));
                    map.setDttMovifecha(e.getString("dtt_movifecha"));
                    map.setEmpleado(new Empleado(e.getString("chr_emplcodigo")));

                    Tipomovimiento tipomovimiento=new Tipomovimiento();
                    tipomovimiento.setChrTipocodigo(e.getString("chr_tipocodigo"));
                    tipomovimiento.setVchTipodescripcion(e.getString("vch_tipodescripcion"));
                    tipomovimiento.setVchTipoaccion(e.getString("vch_tipoaccion"));
                    map.setTipomovimiento(tipomovimiento);
                    lista.add(map);
                }
            }
        } catch (Exception e) {
            Toast.makeText(applicationContext, "Error : " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return lista;
    }
    public synchronized List<Movimiento> ListaMovimientoCuenta(Context applicationContext,String iDcUENTA) {
        List<Movimiento> lista = null;
        try {
            String url = Conexion.URL_APP + "ListaMovimientoCuenta.php?IdCuenta="+iDcUENTA;
            String jsonResult = Conexion.getConexion(url);
            if (jsonResult.isEmpty()) {
                lista = new ArrayList<Movimiento>();
            } else {
                JSONObject json = new JSONObject(jsonResult);
                JSONArray jArray = json.getJSONArray("cuentas");
                lista = new ArrayList<>();
                Movimiento map = null;
                for (int i = 0; i < jArray.length(); i++) {
                    map = new Movimiento();
                    JSONObject e = jArray.getJSONObject(i);
                    map.setCuenta(new Cuenta(e.getString("cuenta")));
                    map.setDecMoviimporte(e.getDouble("importe"));
                    map.setDttMovifecha(e.getString("fecha"));
                    Tipomovimiento tipomovimiento=new Tipomovimiento();
                    tipomovimiento.setChrTipocodigo(e.getString("codtipo"));
                    tipomovimiento.setVchTipodescripcion(e.getString("tipo"));
                    tipomovimiento.setVchTipoaccion(e.getString("accion"));
                    map.setTipomovimiento(tipomovimiento);
                    lista.add(map);
                }
            }
        } catch (Exception e) {
            Toast.makeText(applicationContext, "Error : " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return lista;
    }

}
