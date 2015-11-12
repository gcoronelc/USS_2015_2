package edu.uss.edgar.practica10_httpclient;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvnombreusuario = (TextView) findViewById(R.id.tvUsuario);
        if(Util.nombre_empleado.toString().equalsIgnoreCase("")){

            Intent intent = new Intent(getApplicationContext(),activity_login.class);
            startActivity(intent);
            this.finish();
        }
        tvnombreusuario.setText("Usuario: "+Util.nombre_empleado);


        //Si se trata de Android Version 3 o superior
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onClickCliente(View view)
    {
        Intent intent = new Intent(getApplicationContext(),ClienteActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void onClickRetiro(View view)
    {
        Intent intent = new Intent(getApplicationContext(),activity_retiro.class);
        startActivity(intent);
        this.finish();
    }

    public void onClickDeposito(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Activity_deposito.class);
        startActivity(intent);
        this.finish();
    }

    public void onClickMovimientos(View view)
    {
        Intent intent = new Intent(getApplicationContext(),MovimientosActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void onClickSalir(View view)
    {

        Util.nombre_empleado="";
        Util.codigo_empleado="";
        Intent intent = new Intent(getApplicationContext(),activity_login.class);
        startActivity(intent);
        this.finish();
    }

}
