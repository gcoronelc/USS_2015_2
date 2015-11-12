package pe.uss.ttc.trabajito.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import pe.uss.ttc.trabajito.R;
import pe.uss.ttc.trabajito.model.Empleado;

/**
 * Created by Tania on 10/11/2015.
 */
public class LoginActivity extends Activity {

    private String dato;
    private EditText nombre, contra;
    private Button ingresar;
    public Empleado emp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nombre = (EditText) findViewById(R.id.etUsuarioEmp);
        contra = (EditText) findViewById(R.id.etContraEmp);
        ingresar = (Button) findViewById(R.id.btnLogin);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nombre.getText().toString().trim().equalsIgnoreCase("") ||
                        !contra.getText().toString().trim().equalsIgnoreCase("")) {
                    new Traer(LoginActivity.this).execute();

                } else
                    Toast.makeText(LoginActivity.this, "Hay informacion por rellenar", Toast.LENGTH_SHORT).show();
            }

        });
    }

    class Traer extends AsyncTask<String, String, String> {

        private Activity context;

        Traer(Activity context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            dato = verificar();
            if (!dato.equals("error")) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (LimpiarDatos(dato)) {
                            if (emp != null) {
                                //Toast.makeText(context, "Bienvenido " + emp.getNombre() + " " + emp.getPaterno(), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.putExtra("id", emp.getCodigo());
                                i.putExtra("paterno", emp.getPaterno());
                                i.putExtra("materno", emp.getMaterno());
                                i.putExtra("nombre", emp.getNombre());
                                i.putExtra("ciudad", emp.getCiudad());
                                i.putExtra("usuario", emp.getUsuario());
                                startActivity(i);
                                nombre.setText("");
                                contra.setText("");
                            }
                        }
                    }
                });
            } else
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "Error conexion con servidor", Toast.LENGTH_SHORT).show();
                    }
                });
            return null;
        }
    }

    private String verificar() {

        String resquest = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://eurekabank.webcindario.com/Loguin.php");
        List<NameValuePair> nameValuePais = new ArrayList<>(2);
        nameValuePais.add(new BasicNameValuePair("usuario", nombre.getText().toString().trim()));
        nameValuePais.add(new BasicNameValuePair("clave", contra.getText().toString().trim()));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePais));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            resquest = httpClient.execute(httppost, responseHandler);
            return resquest;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    private boolean LimpiarDatos(String reqst) {
        if (!reqst.equals("error")) {
            String[] cargardatos = reqst.split("/");
            try {
                for (int i = 0; i < cargardatos.length; i++) {
                    String datospersona[] = cargardatos[i].split("<br>");
                    emp = new Empleado();
                    emp.setCodigo(datospersona[0]);
                    emp.setPaterno(datospersona[1]);
                    emp.setMaterno(datospersona[2]);
                    emp.setNombre(datospersona[3]);
                    emp.setCiudad(datospersona[4]);
                    emp.setDireccion(datospersona[5]);
                    emp.setUsuario(datospersona[6]);
                    return true;
                }
            } catch (Exception e) {
                emp = null;
                return false;
            }
        }
        return false;
    }

}
