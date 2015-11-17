package pe.uss.monsalve.trabajo_unidad_2;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ItemView extends LinearLayout {
    private TextView etFecha, etTipo, etImporte;

    public ItemView(Context context) {
        super(context);
        inflate(context, R.layout.layout_item_view, this);

        etFecha = (TextView) findViewById(R.id.etFecha);
        etTipo = (TextView) findViewById(R.id.etTipo);
        etImporte = (TextView) findViewById(R.id.etImporte);
    }

    public void setItem(HashMap<String, String> item){
        etFecha.setText(item.get("fecha").toString());
        etTipo.setText(item.get("tipo").toString());
        etImporte.setText(item.get("importe").toString());
    }
}
