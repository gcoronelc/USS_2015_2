package edu.uss.edgar.practica10_httpclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MovimientosActivity extends AppCompatActivity
{

    private static final String TAG_DATE = "dtt_movifecha";
    private static final String TAG_TYPE = "vch_tipodescripcion";
    private static final String TAG_AMOUNT = "dec_moviimporte";
    private static final String TAG_CLIENTE = "nombres";
    private static final String TAG_SALDO = "dec_cuensaldo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimientos);
        TextView tvnombreusuario = (TextView) findViewById(R.id.tvUsuario);
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
        getMenuInflater().inflate(R.menu.menu_movimientos, menu);
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
        EditText etCuenta = (EditText) findViewById(R.id.movimienos_et_cuenta);
        TextView tvResultado = (TextView)findViewById(R.id.movimientos_tv_resultado);
        TextView tvcliente = (TextView)findViewById(R.id.tvCliente);
        TextView tvcuenta = (TextView)findViewById(R.id.tvCuenta);
        TextView tvsaldo = (TextView)findViewById(R.id.tvSaldo);
        ListView lvDatos = (ListView)findViewById(R.id.movimientos_lv_lista);

        try{
            //Dato
            String cuenta = etCuenta.getText().toString();
            //Lanzar consulta
            String url = Util.URL_APP + "ConsultarMovimientos.php?cuenta="
                    +cuenta;
            String jsonResult = Util.execJsonGetRequest(url);
            if(jsonResult.isEmpty())
            {
                throw new Exception("Cuenta no existe.");
            }
            //Procesar el Resultado
            JSONObject json = new JSONObject(jsonResult);
            //Obtener el elemento que contiene los movimientos
            JSONArray jArray = json.getJSONArray("movimientos");
            //Hasmap for ListView
            ArrayList<HashMap<String,String>> movimientos =
                    new ArrayList<HashMap<String, String>>();
            //Loop the Array
            for(int i=0; i< jArray.length(); i++)
            {
                HashMap<String,String> map = new HashMap<String,String>();
                JSONObject e = jArray.getJSONObject(i);

                map.put(TAG_TYPE, e.getString(TAG_TYPE));
                map.put(TAG_AMOUNT,e.getString(TAG_AMOUNT));
                map.put(TAG_DATE, e.getString(TAG_DATE));
                tvcliente.setText("Cliente: " + e.getString(TAG_CLIENTE));
                tvcuenta.setText("Cuenta: " + etCuenta.getText());
                tvsaldo.setText("Saldo: "+e.getString(TAG_SALDO));
                movimientos.add(map);
            }
            //Adaptando al ListView
            lvDatos.setAdapter(new ItemAdapter(movimientos));

        } catch (Exception e)
        {
            tvResultado.setText(e.getMessage());
            //throw e;
        }
    }

    public void onCLickRetornar(View view)
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        this.finish();
    }

}
