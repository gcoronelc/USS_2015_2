package pe.uss.ttc.trabajito.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class RetiroActivity extends Activity {

    String cCodigo,eCodigo;
    Double cSaldo;
    TextView tvrSaldo, tvrCuenta;
    EditText etrMonto;
    Button btnRetiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retiro);
        tvrSaldo = (TextView) findViewById(R.id.tvrSaldo);
        tvrCuenta = (TextView) findViewById(R.id.tvrCodigo);
        etrMonto = (EditText) findViewById(R.id.etrMonto);
        btnRetiro = (Button) findViewById(R.id.btnRetiro);

        Bundle bundle = getIntent().getExtras();
        cCodigo = (bundle.getString("cCodigo"));
        eCodigo = (bundle.getString("eCodigo"));
        cSaldo = (bundle.getDouble("cSaldo"));

        tvrCuenta.setText(cCodigo);
        tvrSaldo.setText(String.valueOf(cSaldo));

        btnRetiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etrMonto.getText().toString().trim().equalsIgnoreCase("")){
                    if (Double.parseDouble(String.valueOf(etrMonto.getText()))<=cSaldo) {
                        new Traer(RetiroActivity.this).execute();
                    }else{
                        Toast.makeText(RetiroActivity.this,"Saldo insuficiente",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(RetiroActivity.this, "Hay informacion por rellenar", Toast.LENGTH_SHORT).show();
                }

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
            if (verificar()==true) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Double saldoactual = cSaldo - Double.parseDouble(String.valueOf(etrMonto.getText()));
                        AlertDialog alertDialog = new AlertDialog.Builder(RetiroActivity.this).create();
                        alertDialog.setTitle("Detalle de Retiro");
                        alertDialog.setMessage("****** EurekaBank ******\n\n" +
                                "Cuenta: " + cCodigo + "\n" +
                                "Saldo Anterior: " + cSaldo + "\n" +
                                "Monto Retirado: " + etrMonto.getText() + "\n" +
                                "Saldo Actual: " + saldoactual);
                        alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alertDialog.show();
                        cSaldo = saldoactual;
                        tvrSaldo.setText(String.valueOf(cSaldo));
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

    private boolean verificar() {

        String resquest = "";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://eurekabank.webcindario.com/Retiro.php");
        List<NameValuePair> nameValuePais = new ArrayList<>(3);
        nameValuePais.add(new BasicNameValuePair("cuenta", cCodigo.toString()));
        nameValuePais.add(new BasicNameValuePair("cantidad", etrMonto.getText().toString()));
        nameValuePais.add(new BasicNameValuePair("codigo", eCodigo.toString()));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePais));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            resquest = httpClient.execute(httppost, responseHandler);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
