package pe.uss.monsalve.trabajo_unidad_2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class ActividadEmpleado extends AppCompatActivity implements View.OnClickListener{
    private TextView etNombre;
    private Button botonDepositar, botonRetirar, botonVerMovimientos;
    private ImageButton botonCerrarSesion;

    //Actividades
    private final int DEPOSITO = 1;
    private final int RETIRO = 2;
    private final int MOVIMIENTOS = 3;

    private String codEmpleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_empleado);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String nombreCompleto = bundle.getString("nombreCompleto");
        codEmpleado = bundle.getString("codEmpleado");

        etNombre = (TextView) findViewById(R.id.etNombre);
        etNombre.setText(nombreCompleto);

        botonDepositar = (Button) findViewById(R.id.botonDepositar);
        botonRetirar = (Button) findViewById(R.id.botonRetirar);
        botonVerMovimientos = (Button) findViewById(R.id.botonVerMovimientos);
        botonCerrarSesion = (ImageButton) findViewById(R.id.botonCerrarSesion);

        botonDepositar.setOnClickListener(this);
        botonRetirar.setOnClickListener(this);
        botonVerMovimientos.setOnClickListener(this);
        botonCerrarSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.botonDepositar:
                realizarMovimiento(DEPOSITO);
                break;
            case R.id.botonRetirar:
                realizarMovimiento(RETIRO);
                break;
            case R.id.botonVerMovimientos:
                consultarMovimientos();
                break;
            case R.id.botonCerrarSesion:
                Intent intent = new Intent(this, ActividadLogin.class);
                startActivity(intent);
                break;
        }
    }

    private void realizarMovimiento(final int codigoActividad){
        LayoutInflater activador = LayoutInflater.from(this);
        final View entrada = activador.inflate(R.layout.actividad_cuenta_dialogo, null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Cuenta Bancaria");
        dialog.setView(entrada);
        dialog.setPositiveButton("Acceder", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText campoCuenta = (EditText) entrada.findViewById(R.id.campoCuenta);
                EditText campoPassword = (EditText) entrada.findViewById(R.id.campoPassword);
                String numeroCuenta = campoCuenta.getText().toString();
                String clave = campoPassword.getText().toString();
                if(numeroCuenta.isEmpty()){
                    imprimir("Digite un número de cuenta");
                }else if(numeroCuenta.length() != 8){
                    imprimir("El número de cuenta no es válido");
                }else if (clave.isEmpty()){
                    imprimir("Digite la contraseña");
                }else{
                    obtenerAccessoCuenta(numeroCuenta, clave, codigoActividad);
                }
            }
        });
        dialog.setNegativeButton("Cancelar", null);
        dialog.show();
    }

    private void consultarMovimientos(){
        Intent intent = new Intent(this, ActividadListaMovimientos.class);
        startActivityForResult(intent, MOVIMIENTOS);
    }

    private void obtenerAccessoCuenta(String cuenta, String password, final int codigoActividad){
        try{
            String url = Util.URL_APP + "consultarCuenta.php?cuenta=" + cuenta + "&password=" + password;
            String resultadoJson = Util.execJsonGetRequest(url);
            JSONObject objetoJson = new JSONObject(resultadoJson);
            String estado = objetoJson.getString("estado");
            String estadoCuenta = objetoJson.getString("vch_cuenestado");
            double saldo = objetoJson.getDouble("dec_cuensaldo");

            if(estado.equals("0")){
                throw new Exception("Numero de cuenta o contraseña incorrectos");
            }else if(!estadoCuenta.equals("ACTIVO")){
                throw new Exception("La cuenta no se encuentra activa");
            }

            Intent intent = new Intent(this, ActividadMovimiento.class);
            intent.putExtra("saldo", saldo);
            intent.putExtra("cuenta", cuenta);
            intent.putExtra("codEmpleado", codEmpleado);

            String strActividad = "";
            switch(codigoActividad){
                case DEPOSITO:
                    strActividad = "DEPOSITO";
                    break;
                case RETIRO:
                    strActividad = "RETIRO";
                    break;
            }

            intent.putExtra("movimiento", strActividad);
            startActivityForResult(intent, codigoActividad);

        }catch(Exception e){
            imprimir(e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int codigoActividad, int codigoResultado, Intent intent) {
        switch (codigoActividad) {
            case DEPOSITO:
                if (codigoResultado == RESULT_OK) {
                    imprimir("Deposito realizado con exito");
                }else {
                    imprimir("Movimiento de deposito cancelado");
                }
                break;
            case RETIRO:
                if (codigoResultado == RESULT_OK) {
                    imprimir("Retiro realizado con exito");
                }else {
                    imprimir("Movimiento de retiro cancelado");
                }
                break;
            case MOVIMIENTOS:
                break;
        }
    }

    private void imprimir(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}
