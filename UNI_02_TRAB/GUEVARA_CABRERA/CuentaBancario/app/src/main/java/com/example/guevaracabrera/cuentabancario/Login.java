package com.example.guevaracabrera.cuentabancario;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.guevaracabrera.cuentabancario.Entidaes.Empleado;

import org.json.JSONObject;

public class Login extends AppCompatActivity {
    Button inicar, cancelar;
    EditText cuenta, clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        inicar = (Button) findViewById(R.id.JTB_Iniciar);
        cancelar = (Button) findViewById(R.id.JTB_Cancel);
        cuenta = (EditText) findViewById(R.id.Txt_Cuenta);
        clave = (EditText) findViewById(R.id.Txt_Clave);
        EjecutarBotornes();
    }

    private void EjecutarBotornes() {
        inicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empleado empleado = IniciarLogin(cuenta.getText().toString(), clave.getText().toString());
                if (empleado != null) {
                    Intent e = new Intent(getApplicationContext(), Principal.class);
                    e.putExtra("empleado", empleado);
                    startActivity(e);
                } else {
                    Toast.makeText(getApplicationContext(), "Ingreso de Datos Incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Empleado IniciarLogin(String usuario, String Clave) {
        Empleado empleado = null;
        try {
            String url = Conexion.URL_APP + "IniciarLogin.php?usuario=" + usuario.trim() + "&clave=" + Clave.trim();
            String jsonResult = Conexion.getConexion(url);
            JSONObject object = new JSONObject(jsonResult);
            String estado = object.getString("estado");
            if (estado.equals("0")) {
                throw new Exception("Dato incorrecto.");
            }
            empleado = new Empleado();
            empleado.setChrEmplcodigo(object.getString("chr_emplcodigo"));
            empleado.setVchEmplpaterno(object.getString("vch_emplpaterno"));
            empleado.setVchEmplmaterno(object.getString("vch_emplmaterno"));
            empleado.setVchEmplnombre(object.getString("vch_emplnombre"));
            empleado.setVchEmplciudad(object.getString("vch_emplciudad"));
            empleado.setVchEmpldireccion(object.getString("vch_empldireccion"));
            empleado.setVchEmplusuario(object.getString("vch_emplusuario"));
            empleado.setVchEmplclave(object.getString("vch_emplclave"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return empleado;
    }


}
