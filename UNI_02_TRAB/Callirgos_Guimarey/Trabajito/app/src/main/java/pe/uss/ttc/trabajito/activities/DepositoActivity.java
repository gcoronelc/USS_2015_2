package pe.uss.ttc.trabajito.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
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

public class DepositoActivity extends Activity {

    String cCodigo,eCodigo;
    Double cSaldo;
    TextView tvdSaldo, tvdCuenta;
    EditText etdMonto;
    Button btnDeposito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);
        tvdSaldo = (TextView) findViewById(R.id.tvdSaldo);
        tvdCuenta = (TextView) findViewById(R.id.tvdCodigo);
        etdMonto = (EditText) findViewById(R.id.etdMonto);
        btnDeposito = (Button) findViewById(R.id.btnDeposito);

        Bundle bundle = getIntent().getExtras();
        cCodigo = (bundle.getString("cCodigo"));
        eCodigo = (bundle.getString("eCodigo"));
        cSaldo = (bundle.getDouble("cSaldo"));

        tvdCuenta.setText(cCodigo);
        tvdSaldo.setText(String.valueOf(cSaldo));

        btnDeposito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etdMonto.getText().toString().trim().equalsIgnoreCase("")) {
                    if (Double.parseDouble(String.valueOf(etdMonto.getText())) >0) {
                        new Traer(DepositoActivity.this).execute();
                    } else {
                        Toast.makeText(DepositoActivity.this, "Monto no valido", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DepositoActivity.this, "Hay informacion por rellenar", Toast.LENGTH_SHORT).show();
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
                        Double saldoactual = cSaldo + Double.parseDouble(String.valueOf(etdMonto.getText()));
                        AlertDialog alertDialog = new AlertDialog.Builder(DepositoActivity.this).create();
                        alertDialog.setTitle("Detalle de Retiro");
                        alertDialog.setMessage("****** EurekaBank ******\n\n" +
                                "Cuenta: " + cCodigo + "\n" +
                                "Saldo Anterior: " + cSaldo + "\n" +
                                "Monto Depositado: " + etdMonto.getText() + "\n" +
                                "Saldo Actual: " + saldoactual);
                        alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        alertDialog.show();
                        cSaldo = saldoactual;
                        tvdSaldo.setText(String.valueOf(cSaldo));
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
        HttpPost httppost = new HttpPost("http://eurekabank.webcindario.com/Deposito.php");
        List<NameValuePair> nameValuePais = new ArrayList<>(3);
        nameValuePais.add(new BasicNameValuePair("cuenta", cCodigo.toString()));
        nameValuePais.add(new BasicNameValuePair("cantidad", etdMonto.getText().toString()));
        nameValuePais.add(new BasicNameValuePair("codigo", eCodigo.toString()));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePais));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            resquest = httpClient.execute(httppost, responseHandler);
            System.out.println(resquest);
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
