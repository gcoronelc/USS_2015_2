package com.example.guevaracabrera.cuentabancario.Item_Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guevaracabrera.cuentabancario.Entidaes.Movimiento;
import com.example.guevaracabrera.cuentabancario.R;

/**
 * Created by Alehexis on 10/11/2015.
 */
public class ItemMovimients extends LinearLayout {

    private TextView Txt_NumC, Txt_Fecha, Txt_Tipo, Txt_Monto;

    public ItemMovimients(Context context) {
        super(context);
        inflate(context, R.layout.item_lista, this);
        Txt_NumC = (TextView) findViewById(R.id.Txt_NumC);
        Txt_Fecha = (TextView) findViewById(R.id.Txt_Fecha);
        Txt_Tipo = (TextView) findViewById(R.id.Txt_Tipo);
        Txt_Monto = (TextView) findViewById(R.id.Txt_Monto);


    }

    public void setItem(Movimiento item) {
        Txt_NumC.setText(item.getCuenta().getChrCuencodigo());
        Txt_Fecha.setText(item.getDttMovifecha());
        Txt_Tipo.setText(item.getTipomovimiento().getVchTipodescripcion());
        Txt_Monto.setText(Double.toString(item.getDecMoviimporte()));
    }
}