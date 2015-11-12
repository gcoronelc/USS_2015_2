package pe.uss.egcc.wsdemo02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pe.uss.egcc.wsimpl.WSHello;
import pe.uss.egcc.wsimpl.WSSumar;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void btnSaludarClick(View view){
    // Controles
    EditText etNombre = (EditText) findViewById(R.id.etNombre);
    TextView tvSaludo = (TextView) findViewById(R.id.tvSaludo);
    // Proceso
    WSHello wsHello = new WSHello();
    wsHello.setNombre(etNombre.getText().toString());
    wsHello.setTvSaludo(tvSaludo);
    wsHello.execute();
  }

  public void btnProcesarSumaClick(View view){
    // Controles
    EditText etNum1 = (EditText) findViewById(R.id.etNum1);
    EditText etNum2 = (EditText) findViewById(R.id.etNum2);
    TextView tvResultado = (TextView) findViewById(R.id.tvResultado);
    // Proceso
    WSSumar wsSumar = new WSSumar();
    wsSumar.setNum1(etNum1.getText().toString());
    wsSumar.setNum2(etNum2.getText().toString());
    wsSumar.setTvResultado(tvResultado);
    wsSumar.execute();
  }
}
