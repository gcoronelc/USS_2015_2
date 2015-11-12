package pe.uss.ttc.trabajito.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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

import pe.uss.ttc.trabajito.Adapters.AdapterMovimiento;
import pe.uss.ttc.trabajito.R;
import pe.uss.ttc.trabajito.model.Movimiento;

public class MovimientoActivity extends Activity {

    private String dato;
    ArrayList<Movimiento> alMovimiento;
    Movimiento movimiento;
    String codigo;
    String moneda;
    String sucursalApert;
    String empUsuaApert;
    String clientPaterno;
    String clientMaterno;
    String clientNombre;
    String clientDni;
    String clientMail;
    String estado;
    Double Saldo;
    private TextView tvCodigo,tvMoneda,tvApellidos,tvNombret,tvDni,tvSaldo;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movimiento);

        tvCodigo = (TextView) findViewById(R.id.tvCCodigo);
        tvMoneda = (TextView) findViewById(R.id.tvCMoneda);
        tvApellidos = (TextView) findViewById(R.id.tvCApellidos);
        tvNombret = (TextView) findViewById(R.id.tvCNombret);
        tvDni = (TextView) findViewById(R.id.tvCDni);
        tvSaldo = (TextView) findViewById(R.id.tvSaldo);

        lista = (ListView) findViewById(R.id.lvMovimiento);
        alMovimiento = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        codigo = (bundle.getString("cCodigo"));
        moneda = (bundle.getString("cMoneda"));
        sucursalApert = (bundle.getString("cSucursal"));
        empUsuaApert = (bundle.getString("cEmpApert"));
        clientPaterno = (bundle.getString("cCliPat"));
        clientMaterno = (bundle.getString("cCliMat"));
        clientNombre = (bundle.getString("cCliNombre"));
        clientDni = (bundle.getString("cCliDni"));
        clientMail = (bundle.getString("cCliMail"));
        estado = (bundle.getString("cEstado"));
        Saldo = (bundle.getDouble("cSaldo"));

        tvCodigo.setText(codigo);
        tvMoneda.setText(moneda);
        tvApellidos.setText(clientPaterno+" "+clientMaterno);
        tvNombret.setText(clientNombre);
        tvDni.setText(clientDni);
        tvSaldo.setText(String.valueOf(Saldo));

        alMovimiento.add(new Movimiento("TIPO","FECHA","IMPORTE"));

        new Traer(MovimientoActivity.this).execute();


    }

    class Traer extends AsyncTask<String,String,String> {

        private Activity context;

        Traer(Activity context){
            this.context=context;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            dato = verificar();
            if(!dato.equals("error")) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if(LimpiarDatos(dato)) {
                            AdapterMovimiento adapterMovimiento = new AdapterMovimiento(MovimientoActivity.this,alMovimiento);
                            lista.setAdapter(adapterMovimiento);
                        }
                        else {

                        }
                    }
                });
            }
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Error conexion con servidor", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

    private  String verificar()   {

        String resquest="";
        HttpClient httpClient= new DefaultHttpClient();
        HttpPost httppost= new HttpPost("http://eurekabank.webcindario.com/movimientos.php");

        List<NameValuePair> nameValuePais= new ArrayList<>(1);
        nameValuePais.add(new BasicNameValuePair("codigo", codigo.toString().trim()));
        try{
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePais));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            resquest = httpClient.execute(httppost, responseHandler);
            return resquest;
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error";
    }

    private boolean LimpiarDatos(String reqst){
        boolean rpta = false;
        if(!reqst.equals("error")){
            String[] cargardatos=reqst.split("/");
            try{
                for (int i =0; i<cargardatos.length;i++) {
                    String datospersona[] = cargardatos[i].split("<br>");
                    if (datospersona[0].matches("\\d{4}-\\d{2}-\\d{2}")){
                        movimiento = new Movimiento();
                        movimiento.setMovFecha(datospersona[0]);
                        movimiento.setMovTipo(datospersona[1]);
                        movimiento.setMovImporte(datospersona[2]);
                        alMovimiento.add(movimiento);

                    }else{
                        System.out.println(cargardatos.length +" cantidad");
                        return true;
                    }
                }
            }
            catch (Exception e){
                movimiento=null;
                return false;
            }
        }
        return false;
    }
}
