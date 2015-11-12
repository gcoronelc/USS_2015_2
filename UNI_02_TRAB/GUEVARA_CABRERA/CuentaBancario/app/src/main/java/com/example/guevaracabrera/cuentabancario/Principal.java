package com.example.guevaracabrera.cuentabancario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guevaracabrera.cuentabancario.Entidaes.Empleado;
import com.example.guevaracabrera.cuentabancario.Entidaes.Movimiento;
import com.example.guevaracabrera.cuentabancario.Item_Adapter.ItemAdapterMovi;
import com.example.guevaracabrera.cuentabancario.LlenadoDatos.BDDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alehexis on 10/11/2015.
 */
public class Principal extends AppCompatActivity {
    Empleado empleado;
    TextView Txt_NomE, Txt_ApellidosP, Txt_ApellidoM;
    ListView listaTransa;
    BDDao datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_menuprincipal);
        empleado = (Empleado) getIntent().getExtras().getSerializable("empleado");
        Txt_NomE = (TextView) findViewById(R.id.Txt_NomE);
        Txt_ApellidosP = (TextView) findViewById(R.id.Txt_ApellidosP);
        Txt_ApellidoM = (TextView) findViewById(R.id.Txt_ApellidoM);
        listaTransa = (ListView) findViewById(R.id.TransacionesEmpleado);
        datos = new BDDao();
        LLenarDatos();
        LlenarLista();
    }

    private void LLenarDatos() {
        Txt_NomE.setText(empleado.getVchEmplnombre());
        Txt_ApellidosP.setText(empleado.getVchEmplpaterno());
        Txt_ApellidoM.setText(empleado.getVchEmplmaterno());
    }

    private void LlenarLista() {
        List<Movimiento> litas = new ArrayList<>();
        litas=datos.ListaMovimientosEmpleado(getApplication(), empleado.getChrEmplcodigo());
        if (litas != null) {
            listaTransa.setAdapter(new ItemAdapterMovi(litas));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.MenuDeposito) {
            Intent e = new Intent(getApplicationContext(), Deposito.class);
            e.putExtra("empleado", empleado);
            startActivity(e);

        }
        if (id == R.id.MenuRetiro) {
            Intent e = new Intent(getApplicationContext(), Retiro.class);
            e.putExtra("empleado", empleado);
            startActivity(e);
        }
        if (id == R.id.MenuConsultar) {
            Intent e = new Intent(getApplicationContext(), DetalleCuenta.class);
            startActivity(e);
        }

        return super.onOptionsItemSelected(item);
    }
}
