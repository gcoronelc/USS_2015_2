package pe.uss.ttc.trabajito.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pe.uss.ttc.trabajito.R;
import pe.uss.ttc.trabajito.model.Movimiento;

/**
 * Created by Bryan on 11/11/2015.
 */
public class AdapterMovimiento extends BaseAdapter {

    private List<Movimiento> list;
    private Activity activity;


    public AdapterMovimiento(Activity activity, List<Movimiento> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.detalle_movimiento, null);

        }
        Movimiento movimiento = list.get(position);
        TextView tvMovFecha = (TextView) v.findViewById(R.id.tvMovFecha);
        tvMovFecha.setText(movimiento.getMovFecha());
        TextView tvMovTipo = (TextView) v.findViewById(R.id.tvMovTipo);
        tvMovTipo.setText(movimiento.getMovTipo());
        TextView tvMovImporte = (TextView) v.findViewById(R.id.tvMovImporte);
        tvMovImporte.setText(movimiento.getMovImporte());
        return v;
    }
}
