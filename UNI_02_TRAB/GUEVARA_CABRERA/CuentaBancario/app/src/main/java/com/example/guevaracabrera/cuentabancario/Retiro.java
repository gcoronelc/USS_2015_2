package com.example.guevaracabrera.cuentabancario;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
public class Retiro extends AppCompatActivity {
    EditText Ncuenta, cantidad;
    Button Buscar;
    BDDao cuentas;
    LinearLayout Datos;
    Empleado empleado;
    TextView dni, Nombre, Apellido;
    Cuenta cuenta;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_retiro);
        cuentas = new BDDao();
        Ncuenta = (EditText) findViewById(R.id.TxtNCuenta);
        Buscar = (Button) findViewById(R.id.JTB_BuscarC);
        Datos = (LinearLayout) findViewById(R.id.IdDe);
        Datos.setVisibility(View.INVISIBLE);
        empleado = (Empleado) getIntent().getExtras().getSerializable("empleado");
        cantidad = (EditText) findViewById(R.id.Txt_Cantidad1);
        dni = (TextView) findViewById(R.id.Txt_Dni1);
        Nombre = (TextView) findViewById(R.id.Txt_Apellidos1);
        Apellido = (TextView) findViewById(R.id.Txt_Nombre1);

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

    public void Retirar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater activador = LayoutInflater.from(this);
        View entrada = activador.inflate(R.layout.actividad_contra, null);
        final EditText contra = (EditText) entrada.findViewById(R.id.Txt_xontrase);
        builder.setTitle("Contraseña del " + cuenta.getChrCuencodigo().toString()).
                setView(entrada).
                setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Ejecutar(contra);
                    }
                }).setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void Ejecutar(EditText contra) {
        if (cuenta.getChrCuenclave().equalsIgnoreCase(contra.getText().toString())) {
            if (Double.parseDouble(cantidad.getText().toString()) > 0) {
                if (cuenta.getDecCuensaldo() > Double.parseDouble(cantidad.getText().toString())) {
                    cuentas.Retiro(cuenta.getChrCuencodigo(), empleado.getChrEmplcodigo(), cantidad.getText().toString());
                    Toast.makeText(getApplicationContext(), "Retiro Efectivo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Saldo Insuficiente", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Ingrese una cantidad Mayor a 0", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Contraseña Invalida", Toast.LENGTH_SHORT).show();
        }
    }

    public void Regresar(View view){
        Intent e = new Intent(getApplicationContext(), Principal.class);
        e.putExtra("empleado", empleado);
        startActivity(e);
    }



}