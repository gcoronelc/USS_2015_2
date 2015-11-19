package pe.uss.egcc.demowsrest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import pe.uss.egcc.model.Greeting;
import pe.uss.egcc.model.ProductoBean;

public class MainActivity extends AppCompatActivity {

  private TextView tvId;
  private TextView tvContent;
  private TextView tvProducto;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Controles
    tvId = (TextView) findViewById(R.id.tvId);
    tvContent = (TextView) findViewById(R.id.tvContent);
    tvProducto = (TextView) findViewById(R.id.tvProducto);
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

  public void btnConsultarClick(View view){
    (new HttpRequestTask()).execute();
    new HttpProductoRequest() .execute();
  }

  private class HttpRequestTask extends AsyncTask<Void, Void, Greeting> {
    @Override
    protected Greeting doInBackground(Void... params) {
      try {
        final String url = "http://rest-service.guides.spring.io/greeting";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Greeting greeting = restTemplate.getForObject(url, Greeting.class);
        return greeting;
      } catch (Exception e) {
        Log.e("MainActivity", e.getMessage(), e);
      }
      return null;
    }

    @Override
    protected void onPostExecute(Greeting greeting) {
      tvId.setText(greeting.getId());
      tvContent.setText(greeting.getContent());
    }

  }

  private class HttpProductoRequest extends AsyncTask<Void, Void, ProductoBean> {

    @Override
    protected ProductoBean doInBackground(Void... params) {
      try {
        final String url = "http://192.168.1.36:8087/uss/egcc/producto/2";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        ProductoBean productoBean  = restTemplate.getForObject(url, ProductoBean.class);
        return productoBean;
      } catch (Exception e) {
        Log.e("MainActivity", e.getMessage(), e);
      }
      return null;
    }

    @Override
    protected void onPostExecute(ProductoBean bean) {
      tvProducto.setText(bean.getCodigo() + " - " + bean.getNombre());
    }
  }
}
