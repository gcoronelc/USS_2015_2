package com.example.guevaracabrera.productosventas;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guevaracabrera.productosventas.Modelo.Producto;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
       // new HttpRequestTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
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
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_principal, container, false);
            return rootView;
        }
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, Producto> {
        @Override
        protected Producto doInBackground(Void... params) {
            try {
                final String url = "http://192.168.1.34:8080/Prubea/webresources/producto/1";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Producto greeting = restTemplate.getForObject(url, Producto.class);
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Producto greeting) {
            TextView greetingIdText = (TextView) findViewById(R.id.TxtID);
            TextView greetingContentText = (TextView) findViewById(R.id.TxtNombre);
            TextView greetingContentText1 = (TextView) findViewById(R.id.TxtPrecio);
            TextView greetingContentText2 = (TextView) findViewById(R.id.TxtCantidad);

            greetingIdText.setText(Integer.toString(greeting.getIdprod()));
            greetingContentText.setText(greeting.getNombre());
            greetingContentText1.setText(Double.toString(greeting.getPrecio()));
            greetingContentText2.setText(Integer.toString(greeting.getStock()));
        }

    }
    public void Consultar(View v) {
        new HttpRequestTask().execute();
    }
}
