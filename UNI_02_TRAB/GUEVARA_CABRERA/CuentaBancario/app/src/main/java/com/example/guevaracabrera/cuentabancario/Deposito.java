package com.example.guevaracabrera.cuentabancario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guevaracabrera.cuentabancario.Entidaes.Cuenta;
import com.example.guevaracabrera.cuentabancario.Entidaes.Empleado;
import com.example.guevaracabrera.cuentabancario.LlenadoDatos.BDDao;

/**
 * Created by Alehexis on 11/11/2015.
 */
public class Deposito extends AppCompatActivity {
    EditText Ncuenta, cantidad;
    Button Buscar;
    BDDao cuentas;
    LinearLayout Datos;
    Empleado empleado;
    TextView dni, Nombre, Apellido;
    Cuenta cuenta;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_deposito);
        cuentas = new BDDao();
        Ncuenta = (EditText) findViewById(R.id.Txt_NCuenta);
        Buscar = (Button) findViewById(R.id.JTB_B);
        Datos = (LinearLayout) findViewById(R.id.IdDe1);
        Datos.setVisibility(View.INVISIBLE);
        empleado = (Empleado) getIntent().getExtras().getSerializable("empleado");
        cantidad = (EditText) findViewById(R.id.Txt_Cantidad);
        dni = (TextView) findViewById(R.id.Txt_Dni);
        Nombre = (TextView) findViewById(R.id.Txt_Apellidos);
        Apellido = (TextView) findViewById(R.id.Txt_Nombre);
        Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuenta = cuentas.BuscarCuenta(getApplicationContext(), Ncuenta.getText().toString());
                if (cuenta == null) {
                    Toast.makeText(getApplicationContext(), "Cuenta No existe", Toast.LENGTH_SHORT).show();
                } else {
                    Datos.setVisibility(View.VISIBLE);
                    dni.setText(cuenta.getCliente().getChrCliedni());
                    Nombre.setText(cuenta.getCliente().getVchClienombre());
                    Apellido.setText(cuenta.getCliente().getVchCliepaterno() + " " + cuenta.getCliente().getVchCliematerno());
                }
            }
        });
    }

    public void Depositar(View view) {
        if (Double.parseDouble(cantidad.getText().toString()) > 0) {
            cuentas.Deposito(cuenta.getChrCuencodigo(), empleado.getChrEmplcodigo(), cantidad.getText().toString());
            Toast.makeText(getApplicationContext(), "Deposito Exitoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Ingrese una cantidad Mayor a 0", Toast.LENGTH_SHORT).show();
        }
    }
    public void Regresarp(View view){
        Intent e = new Intent(getApplicationContext(), Principal.class);
        e.putExtra("empleado", empleado);
        startActivity(e);
    }

}
