package pe.uss.monsalve.trabajo_unidad_2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class ActividadLogin extends AppCompatActivity implements View.OnClickListener{

    private Button botonIniciarSesion;
    private EditText campoUsuario;
    private EditText campoPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        botonIniciarSesion = (Button) findViewById(R.id.botonIniciarSesion);
        campoUsuario = (EditText) findViewById(R.id.campoUsuario);
        campoPassword = (EditText) findViewById(R.id.campoPassword);

        botonIniciarSesion.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.botonIniciarSesion:
                String usuario, password;
                usuario = campoUsuario.getText().toString();
                password = campoPassword.getText().toString();
                if(usuario.isEmpty()){
                    imprimir("Digite el nombre de usuario");
                }else if(password.isEmpty()){
                    imprimir("Digite la contraseña");
                }else {
                    iniciarSesion(usuario, password);
                }
                break;
        }
    }

    private void iniciarSesion(String usuario, String password){
        try{
            String url = Util.URL_APP + "login.php?usuario=" + usuario + "&password=" + password;

            String resultadoJson = Util.execJsonGetRequest(url);
            JSONObject objetoJson = new JSONObject(resultadoJson);
            String estado = objetoJson.getString("estado");

            if(estado.equals("0")){
                throw new Exception("Nombre de usuario o contraseña incorrectos");
            }

            String nombre = objetoJson.getString("vch_emplnombre");
            String nombreCompleto = nombre;
            nombreCompleto += " " + objetoJson.getString("vch_emplpaterno");
            nombreCompleto += " " + objetoJson.getString("vch_emplmaterno");
            imprimir("Bienvenido "+nombre);

            Intent intent = new Intent(this, ActividadEmpleado.class);
            intent.putExtra("nombreCompleto", nombreCompleto);
            intent.putExtra("codEmpleado", objetoJson.getString("chr_emplcodigo"));

            startActivity(intent);
        }catch(Exception e){
            imprimir(e.getMessage());
        }
    }

    private void imprimir(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}
