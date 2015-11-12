package pe.uss.egcc.webserviceimpl;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Gustavo Coronel on 10/11/2015.
 */
public class WSCelsiusToFahrenheit extends AsyncTask {


  private final String NAMESPACE = "http://www.w3schools.com/webservices/";
  private final String URL = "http://www.w3schools.com/webservices/tempconvert.asmx";
  private final String SOAP_ACTION = "http://www.w3schools.com/webservices/CelsiusToFahrenheit";
  private final String METHOD_NAME = "CelsiusToFahrenheit";
  private String TAG = "WSCelsius";
  private static String celsius;
  private static String fahren = "";
  private TextView tvResult;
  private boolean hayError;
  private String errorMessage;

  public void setTvResult(TextView tvResult) {
    this.tvResult = tvResult;
  }

  public static void setCelcius(String celsius) {
    WSCelsiusToFahrenheit.celsius = celsius;
  }


  @Override
  protected Object doInBackground(Object[] params) {
    Log.i(TAG, "doInBackground");
    // Creamos el objeto SOAP
    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
    // Creamos el objeto para los datos
    PropertyInfo celsiusPI = new PropertyInfo();
    // Dato según el archivo asmx
    celsiusPI.setName("Celsius");
    celsiusPI.setValue(celsius);
    celsiusPI.setType(double.class);
    //Asociamos lo anterior al objeto request
    request.addProperty(celsiusPI);
    //Creamos el objeto envelope
    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    envelope.dotNet = true;
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
      fahren = response.toString() + "° F";
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
    tvResult.setText("Calculating...");

  }

  @Override
  protected void onPostExecute(Object o) {
    Log.i(TAG, "onPostExecute");
    tvResult.setText(hayError?errorMessage:fahren);
  }

  @Override
  protected void onProgressUpdate(Object[] values) {
    Log.i(TAG, "onProgressUpdate");
  }
}
