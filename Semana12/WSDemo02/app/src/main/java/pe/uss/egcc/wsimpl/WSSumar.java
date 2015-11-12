package pe.uss.egcc.wsimpl;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Gustavo Coronel on 12/11/2015.
 */
public class WSSumar extends AsyncTask {

  private final String NAMESPACE = "http://ws.egcc.pe/";
  private final String URL = "http://192.168.1.36:8080/WSDemo02Server/WSDemo02?wsdl";
  private final String METHOD_NAME = "sumar";
  private final String SOAP_ACTION = "http://ws.egcc.pe/sumar";
  private final String TAG = "WSSumar";

  private String num1;
  private String num2;
  private TextView tvResultado;

  private String resultado;
  private boolean hayError;
  private String errorMessage;

  public void setNum1(String num1) {
    this.num1 = num1;
  }

  public void setNum2(String num2) {
    this.num2 = num2;
  }

  public void setTvResultado(TextView tvResultado) {
    this.tvResultado = tvResultado;
  }


  @Override
  protected Object doInBackground(Object[] params) {
    Log.i(TAG, "doInBackground");
    // Creamos el objeto SOAP
    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
    // Creamos el objeto para los datos
    PropertyInfo piNum1 = new PropertyInfo();
    PropertyInfo piNum2 = new PropertyInfo();
    // Dato según el archivo WS
    piNum1.setName("n1");
    piNum1.setValue(num1);
    piNum2.setName("n2");
    piNum2.setValue(num2);
    //Asociamos lo anterior al objeto request
    request.addProperty(piNum1);
    request.addProperty(piNum2);
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
      resultado = response.toString();
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
    tvResultado.setText("Procesando...");
  }

  @Override
  protected void onPostExecute(Object o) {
    Log.i(TAG, "onPostExecute");
    tvResultado.setText(hayError ? errorMessage : resultado);
  }
}
