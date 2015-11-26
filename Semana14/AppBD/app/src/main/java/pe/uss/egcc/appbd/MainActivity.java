package pe.uss.egcc.appbd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import pe.uss.egcc.appbd.dao.ContactoDAO;
import pe.uss.egcc.appbd.model.ContactoBean;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    consultar(5);
  }

  private void consultar(int id) {
    ContactoBean contactoBean;
    ContactoDAO contactoDAO = new ContactoDAO(this);
    contactoDAO.open();
    contactoBean = contactoDAO.traerContacto(id);

    TextView tvMensaje = (TextView) findViewById(R.id.tvMensaje);
    tvMensaje.setText(contactoBean.getNombre());
  }

  private void modificar() {
    TextView tvMensaje = (TextView) findViewById(R.id.tvMensaje);
    ContactoDAO dao = null;
    try {
      dao = new ContactoDAO(this);
      dao.open();
      ContactoBean contactoBean = new ContactoBean("Torres Jose", "23456712", "jtorres@gmail");
      contactoBean.setId(3);
      dao.update(contactoBean);
      tvMensaje.setText("Registro actualizado.");
    } catch (Exception e) {
      tvMensaje.setText(e.getMessage());
    } finally {
      try {
        dao.close();
      } catch (Exception e2) {
      }
    }
  }

  private void crearBaseDeDatos() {
    TextView tvMensaje = (TextView) findViewById(R.id.tvMensaje);
    ContactoDAO dao = null;
    try {
      dao = new ContactoDAO(this);
      dao.open();
      dao.insert(new ContactoBean("Gustavo Coronel", "996664457", "gcoronelc@gmail.com"));
      dao.insert(new ContactoBean("Ricardo Marcelo", "974567234", "rmarcelo@gmail.com"));
      dao.insert(new ContactoBean("Hugo Valencia", "674523678", "hvalencia@gmail.com"));
      dao.insert(new ContactoBean("Sergio Matsukawa", "878567873", "smatsukawa@gmail.com"));
      dao.insert(new ContactoBean("Cesar Bustamante", "985678345", "cbustamante@gmail.com"));
      tvMensaje.setText("Base de datos creada satisfactoriamente.");
    } catch (Exception e) {
      tvMensaje.setText(e.getMessage());
    } finally {
      try {
        dao.close();
      } catch (Exception e2) {
      }
    }
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
}
