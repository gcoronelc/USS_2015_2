package edu.uss.edgar.practica10_httpclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class activity_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickIngresar(View view)
    {
        //Controles
        EditText etUsuario = (EditText) findViewById(R.id.etUsuario);
        EditText etClave = (EditText) findViewById(R.id.etClave);



        try
        {
            //Dato
            String usuario = etUsuario.getText().toString();
            String clave = etClave.getText().toString();
            //Lanzar consulta

            String url = Util.URL_APP + "login.php?usuario=" + usuario +"&clave="+clave;
            String jsonResult = Util.execJsonGetRequest(url);

            //Procesar el resultado
            JSONObject object = new JSONObject(jsonResult);
            String estado = object.getString("estado");

            if(estado.equals("0"))
            {
                throw new Exception("Datos incorrectos.");
            }

            Util.codigo_empleado=object.getString("chr_emplcodigo");
            Util.nombre_empleado=object.getString("nombre");

           Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            this.finish();
            //tvResultado.setText(object.getString("chr_emplcodigo"));

        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "AQUI EL ERROR " + e.getMessage() + "", Toast.LENGTH_LONG).show();

        }
    }

}
