package edu.uss.edgar.practica10_httpclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class ClienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cliente, menu);
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

    public void onClickConsultar(View view)
    {
        //Controles
        EditText etCodigo = (EditText) findViewById(R.id.cliente_et_codigo);
        TextView tvResultado = (TextView) findViewById(R.id.cliente_tv_resultado);

        try
        {
            //Dato
            String codigo = etCodigo.getText().toString();

            //Lanzar consulta

            String url = Util.URL_APP + "ConsultarCliente.php?codigo=" + codigo;
            String jsonResult = Util.execJsonGetRequest(url);

            //Procesar el resultado
            JSONObject object = new JSONObject(jsonResult);
            String estado = object.getString("estado");

            if(estado.equals("0"))
            {
                throw new Exception("Dato incorrecto.");
            }

            String resultado = "Codigo: " + object.getString("chr_cliecodigo")
                    + "\nPaterno: " + object.getString("vch_cliepaterno")
                    + "\nMaterno: " + object.getString("vch_cliematerno")
                    + "\nNombre : " + object.getString("vch_clienombre");
            tvResultado.setText(resultado);

        }
        catch(Exception e)
        {
            tvResultado.setText("AQUI EL ERROR "+e.getMessage());
        }
    }

    public void onClickRetornar(View view)
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        this.finish();
    }


}
