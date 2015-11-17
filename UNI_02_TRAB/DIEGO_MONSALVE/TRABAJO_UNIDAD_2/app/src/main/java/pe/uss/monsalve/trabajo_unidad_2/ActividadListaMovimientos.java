package pe.uss.monsalve.trabajo_unidad_2;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActividadListaMovimientos extends AppCompatActivity implements View.OnClickListener{
    private TextView etSaldo, etCuenta, etNombres, etApellidos, etDNI;
    private EditText campoCuenta;
    private ListView listaMovimientos;
    private Button botonRegresar, botonVerCliente, botonConsultar;

    private JSONObject objetoCliente;
    private boolean consultado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_lista_movimientos);

        campoCuenta = (EditText) findViewById(R.id.campoCuenta);
        etSaldo = (TextView) findViewById(R.id.etSaldo);
        etNombres = (TextView) findViewById(R.id.etNombres);
        etApellidos = (TextView) findViewById(R.id.etApellidos);
        etDNI = (TextView) findViewById(R.id.etDNI);
        listaMovimientos = (ListView) findViewById(R.id.listaMovimientos);
        botonRegresar = (Button) findViewById(R.id.botonRegresar);
        botonVerCliente = (Button)findViewById(R.id.botonVerCliente);
        botonConsultar = (Button) findViewById(R.id.botonConsultar);
        botonConsultar.setOnClickListener(this);
        botonVerCliente.setOnClickListener(this);
        botonRegresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.botonConsultar:
                String cuenta = campoCuenta.getText().toString();
                if(cuenta.isEmpty()){
                    imprimir("Digite un número de cuenta");
                }else if(cuenta.length() != 8){
                    imprimir("Digite un número de cuenta válido");
                }else{
                    consultarMovimientos(cuenta);
                }
                break;
            case R.id.botonVerCliente:
                if(consultado){
                    mostrarCliente();
                }else{
                    imprimir("Se necesita un numero de cuenta para consultar los datos del cliente");
                }
                break;
            case R.id.botonRegresar:
                Intent intent = new Intent(this, ActividadEmpleado.class);
                setResult(RESULT_OK, intent);
                this.finish();
                break;
        }
    }

    private void mostrarCliente(){
        LayoutInflater activador = LayoutInflater.from(this);
        final View entrada = activador.inflate(R.layout.actividad_cliente_dialogo, null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Datos del cliente");
        dialog.setView(entrada);

        TextView etDNI, etCodigo, etNombre, etPaterno, etMaterno, etDireccion, etTelefono, etCiudad;
        etDNI = (TextView) entrada.findViewById(R.id.etDNI);
        etCodigo = (TextView) entrada.findViewById(R.id.etCodigo);
        etNombre = (TextView) entrada.findViewById(R.id.etNombre);
        etPaterno = (TextView) entrada.findViewById(R.id.etPaterno);
        etMaterno = (TextView) entrada.findViewById(R.id.etMaterno);
        etDireccion = (TextView) entrada.findViewById(R.id.etDireccion);
        etTelefono = (TextView) entrada.findViewById(R.id.etTelefono);
        etCiudad = (TextView) entrada.findViewById(R.id.etCiudad);

        String DNI = "", cod = "", nom = "", pat = "", mat = "", dir = "", tel = "", ciu = "";
        try{
            DNI = objetoCliente.getString("dni");
            cod = objetoCliente.getString("codigo");
            nom = objetoCliente.getString("nombre");
            pat = objetoCliente.getString("paterno");
            mat = objetoCliente.getString("materno");
            dir = objetoCliente.getString("direccion");
            tel = objetoCliente.getString("telefono");
            ciu = objetoCliente.getString("ciudad");
        }catch(Exception e){
            imprimir("Error al cargar los datos del cliente");
        }

        etDNI.setText(DNI);
        etCodigo.setText(cod);
        etNombre.setText(nom);
        etPaterno.setText(pat);
        etMaterno.setText(mat);
        etDireccion.setText(dir);
        etTelefono.setText(tel);
        etCiudad.setText(ciu);

        dialog.setNegativeButton("Ok", null);
        dialog.show();
    }

    private void consultarMovimientos(String cuenta){
        try{
            String url = Util.URL_APP + "consultarMovimientos.php?cuenta=" + cuenta;
            String resultadoJson = Util.execJsonGetRequest(url);
            if(resultadoJson.isEmpty()){
                throw new Exception("La cuenta no existe o no se encuentra activa");
            }
            JSONObject objetoJson = new JSONObject(resultadoJson);
            JSONArray arreglo_movimientos = objetoJson.getJSONArray("movimientos");
            ArrayList<HashMap<String, String>> movimientos = new ArrayList<HashMap<String, String>>();

            for(int i =0; i < arreglo_movimientos.length(); i++){
                HashMap<String, String> map = new HashMap<String, String>();
                JSONObject objeto_movimiento = arreglo_movimientos.getJSONObject(i);
                map.put("fecha", objeto_movimiento.getString("fecha"));
                map.put("tipo", objeto_movimiento.getString("tipo"));
                map.put("importe", String.valueOf(objeto_movimiento.getDouble("importe")));
                movimientos.add(map);
            }

            JSONObject objeto_movimiento = arreglo_movimientos.getJSONObject(0);
            String codCliente = objeto_movimiento.getString("cliente");
            String saldo = ""+objeto_movimiento.getDouble("saldo");

            consultarCliente(codCliente);
            etSaldo.setText(saldo);

            listaMovimientos.setAdapter(new ItemAdapter(movimientos));
            consultado = true;
        }catch(Exception e){
            imprimir(e.getMessage());
        }
    }

    private void consultarCliente(String codCliente){
        imprimir("codigo cliente: "+codCliente);
        try{
            String url = Util.URL_APP + "consultarCliente.php?codigo=" + codCliente;
            String resultadoJson = Util.execJsonGetRequest(url);
            objetoCliente = new JSONObject(resultadoJson);
            String estado = objetoCliente.getString("estado");

            if(estado.equals("0")){
                throw new Exception("Los datos del cliente no pudieron ser consultados");
            }

            String DNI, nombres, apellidos;

            DNI = objetoCliente.getString("dni");
            nombres = objetoCliente.getString("nombre");
            apellidos = objetoCliente.getString("paterno")+" "+objetoCliente.getString("materno");

            etDNI.setText(DNI);
            etNombres.setText(nombres);
            etApellidos.setText(apellidos);
        }catch(Exception e){
            imprimir(e.getMessage());
        }
    }

    private void imprimir(String mensaje){
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
}
