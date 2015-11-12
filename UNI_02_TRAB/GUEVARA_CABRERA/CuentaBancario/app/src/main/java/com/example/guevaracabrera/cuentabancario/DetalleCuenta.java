package com.example.guevaracabrera.cuentabancario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guevaracabrera.cuentabancario.Entidaes.Cuenta;
import com.example.guevaracabrera.cuentabancario.Entidaes.Empleado;
import com.example.guevaracabrera.cuentabancario.Item_Adapter.ItemAdapterMovi;
import com.example.guevaracabrera.cuentabancario.LlenadoDatos.BDDao;

/**
 * Created by Alehexis on 11/11/2015.
 */
public class DetalleCuenta extends AppCompatActivity {
    TextView dni, Nombre, Apellido,Saldo;
    ListView lista;
    BDDao cuentas;
    LinearLayout Datos;
    Button Buscar;
    EditText Ncuenta;
    Cuenta cuenta;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_consultar);
        cuentas = new BDDao();
        Ncuenta = (EditText) findViewById(R.id.TxtC);

        Datos = (LinearLayout) findViewById(R.id.Detalles);
        Datos.setVisibility(View.INVISIBLE);
        dni = (TextView) findViewById(R.id.TxtDn);
        Nombre = (TextView) findViewById(R.id.TxtAp);
        Apellido = (TextView) findViewById(R.id.TxtN);
        Saldo=(TextView) findViewById(R.id.TxtSal);
        lista = (ListView) findViewById(R.id.ListCuenta);
        Buscar = (Button) findViewById(R.id.JtBb);

    }

    public void Buscar(View view) {
        cuenta = cuentas.BuscarCuenta(getApplicationContext(), Ncuenta.getText().toString());
        if (cuenta == null) {
            Toast.makeText(getApplicationContext(), "Cuenta No existe", Toast.LENGTH_SHORT).show();
        } else {
            Datos.setVisibility(View.VISIBLE);
            Saldo.setText(Double.toString(cuenta.getDecCuensaldo()));
            dni.setText(cuenta.getCliente().getChrCliedni());
            Nombre.setText(cuenta.getCliente().getVchClienombre());
            Apellido.setText(cuenta.getCliente().getVchCliepaterno() + " " + cuenta.getCliente().getVchCliematerno());
            lista.setAdapter(new ItemAdapterMovi(cuentas.ListaMovimientoCuenta(getApplicationContext(), cuenta.getChrCuencodigo().toString())));
        }
    }
}


