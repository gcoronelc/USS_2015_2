package pe.uss.ttc.trabajito.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import pe.uss.ttc.trabajito.R;
import pe.uss.ttc.trabajito.model.Cuenta;
import pe.uss.ttc.trabajito.model.Empleado;

/**
 * Created by Bryan on 11/11/2015.
 */

public class MainActivity extends Activity {

    private String codigo, paterno, materno, nombre, ciudad, usuario;
    private TextView tvApellidoPat, tvApellidoMat, tvNombre, tvUsuario, tvCiudad;
    private EditText etCuentaCli, etClaveCli;
    private Button btnAcceder;
    private String dato;
    public Cuenta cuenta = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvApellidoPat = (TextView) findViewById(R.id.tvApellidoPat);
        tvApellidoMat = (TextView) findViewById(R.id.tvApellidoMat);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvUsuario = (TextView) findViewById(R.id.tvUsuario);
        tvCiudad = (TextView) findViewById(R.id.tvCiudad);

        Bundle bundle = getIntent().getExtras();
        paterno = (bundle.getString("paterno"));
        materno = (bundle.getString("materno"));
        nombre = (bundle.getString("nombre"));
        ciudad = (bundle.getString("ciudad"));
        usuario = (bundle.getString("usuario"));
        codigo = bundle.getString("id");

        tvApellidoPat.setText(paterno);
        tvApellidoMat.setText(materno);
        tvNombre.setText(nombre);
        tvUsuario.setText(usuario);
        tvCiudad.setText(ciudad);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        crearActionBar(menu);
        return true;
    }

    private void crearActionBar(Menu menu) {
        MenuItem item;
        // Item 1
        item = menu.add(0, 0, 0, "Deposito");
        item.setIcon(R.drawable.deposito);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        // Item 2
        item = menu.add(0, 1, 1, "Rerito");
        item.setIcon(R.drawable.retiro);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        // Item 3
        item = menu.add(0, 2, 2, "Movimientos");
        item.setIcon(R.drawable.movimientos);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog, null);
                etCuentaCli = (EditText) dialoglayout.findViewById(R.id.etCuentaCli);
                etClaveCli = (EditText) dialoglayout.findViewById(R.id.etClaveCli);
                btnAcceder = (Button) dialoglayout.findViewById(R.id.btnAcceder);
                btnAcceder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("Deposito");
                        if (!etCuentaCli.getText().toString().trim().equalsIgnoreCase("") ||
                                !etClaveCli.getText().toString().trim().equalsIgnoreCase("")) {
                            new Traer1(MainActivity.this).execute();

                        } else
                            Toast.makeText(MainActivity.this, "Hay informacion por rellenar", Toast.LENGTH_SHORT).show();
                    }
                });
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(dialoglayout);
                builder.show();
                break;
            case 1:
                LayoutInflater inflater2 = getLayoutInflater();
                View dialoglayout2 = inflater2.inflate(R.layout.dialog, null);
                etCuentaCli = (EditText) dialoglayout2.findViewById(R.id.etCuentaCli);
                etClaveCli = (EditText) dialoglayout2.findViewById(R.id.etClaveCli);
                btnAcceder = (Button) dialoglayout2.findViewById(R.id.btnAcceder);
                btnAcceder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("Retiro");
                        if (!etCuentaCli.getText().toString().trim().equalsIgnoreCase("") ||
                                !etClaveCli.getText().toString().trim().equalsIgnoreCase("")) {
                            new Traer2(MainActivity.this).execute();

                        } else
                            Toast.makeText(MainActivity.this, "Hay informacion por rellenar", Toast.LENGTH_SHORT).show();
                    }
                });
                final AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setView(dialoglayout2);
                builder2.show();
                break;
            case 2:
                LayoutInflater inflater3 = getLayoutInflater();
                View dialoglayout3 = inflater3.inflate(R.layout.dialog, null);
                etCuentaCli = (EditText) dialoglayout3.findViewById(R.id.etCuentaCli);
                etClaveCli = (EditText) dialoglayout3.findViewById(R.id.etClaveCli);
                btnAcceder = (Button) dialoglayout3.findViewById(R.id.btnAcceder);
                btnAcceder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println("Movimientos");
                        if (!etCuentaCli.getText().toString().trim().equalsIgnoreCase("") ||
                                !etClaveCli.getText().toString().trim().equalsIgnoreCase("")) {
                            new Traer3(MainActivity.this).execute();

                        } else
                            Toast.makeText(MainActivity.this, "Hay informacion por rellenar", Toast.LENGTH_SHORT).show();
                    }
                });
                final AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
                builder3.setView(dialoglayout3);
                builder3.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class Traer1 extends AsyncTask<String, String, String> {

        private Activity context;

        Traer1(Activity context) {
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
                            if (cuenta != null) {
                                Intent i = new Intent(MainActivity.this, DepositoActivity.class);
                                i.putExtra("cCodigo", cuenta.getCodigo());
                                i.putExtra("cSaldo", cuenta.getSaldo());
                                i.putExtra("eCodigo",codigo);
                                startActivity(i);
                                etCuentaCli.setText("");
                                etClaveCli.setText("");
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

    class Traer2 extends AsyncTask<String, String, String> {

        private Activity context;

        Traer2(Activity context) {
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
                            if (cuenta != null) {
                                Intent i = new Intent(MainActivity.this, RetiroActivity.class);
                                i.putExtra("cCodigo", cuenta.getCodigo());
                                i.putExtra("cSaldo", cuenta.getSaldo());
                                i.putExtra("eCodigo",codigo);
                                startActivity(i);
                                etCuentaCli.setText("");
                                etClaveCli.setText("");
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

    class Traer3 extends AsyncTask<String, String, String> {

        private Activity context;

        Traer3(Activity context) {
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
                            if (cuenta != null) {
                                Intent i = new Intent(MainActivity.this, MovimientoActivity.class);
                                i.putExtra("cCodigo", cuenta.getCodigo());
                                i.putExtra("cMoneda", cuenta.getMoneda());
                                i.putExtra("cSucursal", cuenta.getSucursalApert());
                                i.putExtra("cEmpApert", cuenta.getEmpUsuaApert());
                                i.putExtra("cCliPat", cuenta.getClientPaterno());
                                i.putExtra("cCliMat", cuenta.getClientMaterno());
                                i.putExtra("cCliNombre", cuenta.getClientNombre());
                                i.putExtra("cCliDni", cuenta.getClientDni());
                                i.putExtra("cCliMail", cuenta.getClientMail());
                                i.putExtra("cEstado", cuenta.getEstado());
                                i.putExtra("cSaldo", cuenta.getSaldo());
                                startActivity(i);
                                etCuentaCli.setText("");
                                etClaveCli.setText("");
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
        HttpPost httppost = new HttpPost("http://eurekabank.webcindario.com/LoginCuenta.php");
        List<NameValuePair> nameValuePais = new ArrayList<>(2);
        nameValuePais.add(new BasicNameValuePair("codigo", etCuentaCli.getText().toString().trim()));
        nameValuePais.add(new BasicNameValuePair("clave", etClaveCli.getText().toString().trim()));

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
                    String datosCuenta[] = cargardatos[i].split("<br>");
                    cuenta = new Cuenta();
                    cuenta.setCodigo(datosCuenta[0]);
                    cuenta.setMoneda(datosCuenta[1]);
                    cuenta.setSucursalApert(datosCuenta[2]);
                    cuenta.setEmpUsuaApert(datosCuenta[3]);
                    cuenta.setClientPaterno(datosCuenta[4]);
                    cuenta.setClientMaterno(datosCuenta[5]);
                    cuenta.setClientNombre(datosCuenta[6]);
                    cuenta.setClientDni(datosCuenta[7]);
                    cuenta.setClientMail(datosCuenta[8]);
                    cuenta.setEstado(datosCuenta[9]);
                    cuenta.setSaldo(Double.parseDouble(datosCuenta[10]));
                    return true;
                }
            } catch (Exception e) {
                cuenta = null;
                return false;
            }
        }
        return false;
    }
}
