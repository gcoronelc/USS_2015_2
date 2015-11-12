package com.example.guevaracabrera.cuentabancario.Item_Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.guevaracabrera.cuentabancario.Entidaes.Movimiento;

import java.util.List;

/**
 * Created by Alehexis on 10/11/2015.
 */
public class ItemAdapterMovi extends BaseAdapter {

    private List<Movimiento> datos;

    public ItemAdapterMovi(List<Movimiento> datos) {
        this.datos = datos;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int i) {
        return datos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemMovimients i_view;
        if (view == null) {//NO existe, creamos uno
            i_view = new ItemMovimients(viewGroup.getContext());
        } else
            i_view = (ItemMovimients) view;
        i_view.setItem(datos.get(i));
        return i_view;
    }
}
