package pe.uss.egcc.wsimpl;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WSHello extends AsyncTask{

  private final String NAMESPACE = "http://ws.egcc.pe/";
  private final String URL = "http://192.168.1.36:8080/WSDemo02Server/WSDemo02?wsdl";
  private final String METHOD_NAME = "hello";
  private final String SOAP_ACTION = "http://ws.egcc.pe/hello";
  private final String TAG = "WSHello";

  private String nombre;
  private TextView tvSaludo;


  private String saludo;
  private boolean hayError;
  private String errorMessage;

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setTvSaludo(TextView tvSaludo) {
    this.tvSaludo = tvSaludo;
  }

  @Override
  protected Object doInBackground(Object[] params) {
    Log.i(TAG, "doInBackground");
    // Creamos el objeto SOAP
    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
    // Creamos el objeto para los datos
    PropertyInfo celsiusPI = new PropertyInfo();
    // Dato según el archivo asmx
    celsiusPI.setName("name");
    celsiusPI.setValue(nombre);
    //Asociamos lo anterior al objeto request
    request.addProperty(celsiusPI);
    //Creamos el objeto envelope
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    envelope.dotNet = false;
    //Definimos la respuesta
    envelope.setOutputSoapObject(request);
    //Creamos el objeto HTTP
    HttpTransportSE httpTransportSE  = new HttpTransportSE(URL);
    try {
      //Invoca a la web service
      httpTransportSE.call(SOAP_ACTION, envelope);
      //Obtiene la respuesta
      SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
      //Asigna la respuesta a una variable
      saludo = response.toString();
    } catch (Exception e) {
      hayError = true;
      errorMessage = "Error en el proceso.";
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected void onPreExecute() {
    Log.i(TAG, "onPreExecute");
    tvSaludo.setText("Procesando...");
  }

  @Override
  protected void onPostExecute(Object o) {
    Log.i(TAG, "onPostExecute");
    tvSaludo.setText(hayError ? errorMessage : saludo);
  }

  @Override
  protected void onProgressUpdate(Object[] values) {
    Log.i(TAG, "onProgressUpdate");
  }
}
