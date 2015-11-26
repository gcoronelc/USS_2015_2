package pe.uss.egcc.appbd.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

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

  public void update(ContactoBean contactoBean){
    try {
      String sql = "UPDATE contacto SET nombre='" + contactoBean.getNombre()
          + "', telefono='" + contactoBean.getTelefono()
          + "', email='" + contactoBean.getEmail()
          + "'  WHERE id=" + contactoBean.getId();
      db.execSQL(sql);
    } catch (Exception e) {
      // e.printStackTrace();
      throw new RuntimeException("Error en el proceso de inserción. ");
    }
  }

  public void delete( int id){

  }

  public List<ContactoBean> traerTodos(){
   return null;
  }

  public List<ContactoBean> traerTodos(String nombre){
    return null;
  }

  public ContactoBean traerContacto(int id){
    ContactoBean contactoBean = null;
    try {
      String sql = "SELECT nombre, telefono, email FROM contacto  WHERE id=?";
      String[] args = {"" + id};
      Cursor fila = db.rawQuery(sql,args);
      if(fila.moveToFirst()){
        contactoBean = new ContactoBean();
        contactoBean.setId(id);
        contactoBean.setNombre(fila.getString(0));
        contactoBean.setTelefono(fila.getString(1));
        contactoBean.setEmail(fila.getString(2));
      }
      fila.close();
    } catch (Exception e) {
      // e.printStackTrace();
      throw new RuntimeException("Error en la consulta. " + e.getMessage());
    }
    return contactoBean;
  }
}
