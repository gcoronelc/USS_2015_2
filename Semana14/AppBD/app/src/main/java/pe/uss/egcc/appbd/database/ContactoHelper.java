package pe.uss.egcc.appbd.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gustavo Coronel on 26/11/2015.
 */
public class ContactoHelper extends SQLiteOpenHelper {

  // Sentencia SQL para crear la tabla de Contactos
  private String sqlCreateTable = "CREATE TABLE IF NOT EXISTS contacto ( "
      + "id integer primary key autoincrement, "
      + "nombre TEXT, telefono TEXT, email TEXT )";
  private String sqlDropTable = "DROP TABLE IF EXISTS contacto";



  public ContactoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    // Se ejecuta la sentencia SQL de creación de la tabla
    db.execSQL(sqlCreateTable);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

}
