package edu.uss.edgar.practica10_httpclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class Activity_deposito extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);


        TextView tvnombreusuario = (TextView) findViewById(R.id.tvNombreUsuario);
        if(Util.nombre_empleado.toString().equalsIgnoreCase("")){

            Intent intent = new Intent(getApplicationContext(),activity_login.class);
            startActivity(intent);
            this.finish();
        }
        tvnombreusuario.setText("Usuario: " + Util.nombre_empleado);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_deposito, menu);
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

    public void onClickAceptar(View view)
    {
        //Controles
        EditText etCuenta = (EditText) findViewById(R.id.textCuenta);
        TextView tvMonto = (TextView)findViewById(R.id.textMonto);
        TextView tvResultado = (TextView) findViewById(R.id.tvResultado);
        String cuenta = etCuenta.getText().toString();
        String monto= tvMonto.getText().toString();
        String empleado= Util.codigo_empleado;
        try{
            //Dato


            //Lanzar consulta
            String url = Util.URL_APP + "RegistrarDeposito.php?cuenta="
                    +cuenta+"&importe="+monto+"&empleado="+empleado;
            String jsonResult = Util.execJsonGetRequest(url);
            if(jsonResult.isEmpty())
            {
                throw new Exception("Cuenta no existe.");
            }


            //Procesar el resultado
            JSONObject object = new JSONObject(jsonResult);
            String estado = object.getString("estado");
            String msje = object.getString("message");
            if(estado.equals("0"))
            {
                throw new Exception(msje);
            }
            else
            {
                etCuenta.setText("");
                tvMonto.setText("");
                tvResultado.setText(msje);

            }

        } catch (Exception e)
        {
           tvResultado.setText("AQUI EL ERROR "+e.getMessage());

        }
    }
    public void onCLickRetornar(View view)
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
