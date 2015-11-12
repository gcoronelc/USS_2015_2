package edu.uss.edgar.practica10_httpclient;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Edgar on 27/10/2015.
 */
public class ItemView extends LinearLayout
{


    private TextView tvTipo;
    private TextView tvImporte;
    private TextView tvFecha;

    public ItemView(Context context)
    {
        super(context);
        inflate(context, R.layout.layout_item, this);
        //Controles

        tvTipo = (TextView) findViewById(R.id.item_tv_tipo);
        tvImporte = (TextView)findViewById(R.id.item_tv_importe);
        tvFecha = (TextView)findViewById(R.id.item_tv_fecha);
    }

    public void setItem(HashMap<String, String> item)
    {

        tvTipo.setText(item.get("vch_tipodescripcion").toString());
        tvImporte.setText(item.get("dec_moviimporte").toString());
        tvFecha.setText(item.get("dtt_movifecha").toString());
    }


}
