package pe.uss.monsalve.trabajo_unidad_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActividadMovimiento extends AppCompatActivity implements View.OnClickListener{
    private TextView etMovimiento, etNumeroCuenta, etSaldo;
    private Button botonContinuar, botonCancelar;
    private EditText campoMonto;

    private String cuenta, codEmpleado, movimiento;
    private double saldoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_movimiento);

        etMovimiento = (TextView) findViewById(R.id.etMovimiento);
        etNumeroCuenta = (TextView) findViewById(R.id.etNumeroCuenta);
        etSaldo = (TextView) findViewById(R.id.etSaldo);
        campoMonto = (EditText) findViewById(R.id.campoMonto);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        cuenta = bundle.getString("cuenta");
        codEmpleado = bundle.getString("codEmpleado");
        movimiento = bundle.getString("movimiento");
        saldoActual = bundle.getDouble("saldo");

        etMovimiento.append(" "+movimiento);
        etSaldo.setText("" + saldoActual);
        etNumeroCuenta.setText(cuenta);

        botonContinuar = (Button) findViewById(R.id.botonContinuar);
        botonCancelar = (Button) findViewById(R.id.botonCancelar);

        botonContinuar.setOnClickListener(this);
        botonCancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.botonContinuar:
                if(validarImporte()){
                    realizarMovimiento();
                }
                break;
            case R.id.botonCancelar:
                Intent intent = new Intent(this, ActividadEmpleado.class);
                setResult(RESULT_CANCELED, intent);
                this.finish();
                break;
        }
    }

    private boolean validarImporte(){
        boolean valido = false;

        double importe;
        String strImporte = campoMonto.getText().toString();
        if(!strImporte.isEmpty()){
            try {
                importe = Double.parseDouble(strImporte);
                if(importe > 0) {
                    if (movimiento.equalsIgnoreCase("retiro")) {
                        if (saldoActual >= importe) {
                            valido = true;
                        } else {
                            imprimir("No tiene saldo suficiente");
                        }
                    } else {
                        valido = true;
                    }
                }else{
                    imprimir("El monto digitado no es válido");
                }
            }catch(Exception e){
                imprimir("El monto digitado no es válido");
            }
        }else{
            imprimir("Digite el monto para realizar el "+movimiento.toLowerCase());
        }
        return valido;
    }

    private void realizarMovimiento(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        String fecha = dateFormat.format(date);

        String tipoMov = "";
        if(movimiento.equalsIgnoreCase("deposito")){
            tipoMov = "003";
        }else if(movimiento.equalsIgnoreCase("retiro")){
            tipoMov = "004";
        }

        String strImporte = campoMonto.getText().toString();

        try {
            String url = Util.URL_APP + "insertarMovimiento.php?cta=" + cuenta
                    + "&fm=" + fecha
                    + "&empl=" + codEmpleado
                    + "&tm=" + tipoMov
                    + "&imp=" + strImporte;

            String resultadoJson = Util.execJsonGetRequest(url);
            JSONObject objetoJson = new JSONObject(resultadoJson);
            String estado = objetoJson.getString("estado");

            if (estado.equals("0")) {
                throw new Exception("No se pudo realizar el "+movimiento.toLowerCase());
            }else{
                Intent intent = new Intent(this, ActividadEmpleado.class);
                setResult(RESULT_OK, intent);
                this.finish();
            }
        }catch(Exception e){
            imprimir(e.getMessage());
        }
    }

    private void imprimir(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}
