package pe.uss.egcc.appbd.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import pe.uss.egcc.appbd.database.ContactoHelper;
import pe.uss.egcc.appbd.model.ContactoBean;

/**
 * Created by Gustavo Coronel on 26/11/2015.
 */
public class ContactoDAO {

  // Base de datos
  private final String DB_NAME = "DBContactos";
  private final int DB_VERSION = 1;
  private ContactoHelper dbHelper = null;
  private SQLiteDatabase db = null;

  public ContactoDAO(Context context) {
    dbHelper = new ContactoHelper(context, DB_NAME, null, DB_VERSION);
  }

  public void open() {
    try {
      // Abrimos la base de datos en modo escritura
      db = dbHelper.getWritableDatabase();
    } catch (Exception e) {
      throw new RuntimeException("Error al abrir la base de datos.");
    }
  }

  public void close() {
    try {
      dbHelper.close();
    } catch (Exception e) {
    }
  }

  public void insert(ContactoBean contactoBean) {
    try {
      String sql = "INSERT INTO contacto(nombre,telefono,email) "
          + "values('" + contactoBean.getNombre() + "','"
          + contactoBean.getTelefono() + "','"
          + contactoBean.getEmail() + "')";
      db.execSQL(sql);
    } catch (Exception e) {
      // e.printStackTrace();
      throw new RuntimeException("Error en el proceso de inserción. ");
    }
  }

}
