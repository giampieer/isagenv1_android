package layout.Problema;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import layout.Relacion.Personalizacion_relacion;
import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.ProblemaBean;
import principal.android.empresa.eduardo.bean.ProyectoBean;

/**
 * Created by Home on 28/03/2017.
 */

public class Personalizacion_Problema extends BaseAdapter {


    private static ArrayList<ProblemaBean> lista;
    private LayoutInflater minflater;


    static class ViewHolder {
        TextView LBLNUMERO;
        TextView LBLPROY;
        TextView LBLNIVEL;
        TextView LBLDESCRIPCION;
        TextView LBLPERJUDICADOS;
    }


    public Personalizacion_Problema(Context context, ArrayList<ProblemaBean> lista) {

        this.minflater = minflater.from(context);
        this.lista = lista;


    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Personalizacion_Problema.ViewHolder holder;
        if (convertView == null) {

            convertView = minflater.inflate(R.layout.grilla_problema, null);
            holder = new Personalizacion_Problema.ViewHolder();
            holder.LBLNUMERO = (TextView) convertView.findViewById(R.id.LBLNUM);
            holder.LBLPROY = (TextView) convertView.findViewById(R.id.LBLPROY);
            holder.LBLNIVEL = (TextView) convertView.findViewById(R.id.LBLNIVEL);
            holder.LBLDESCRIPCION = (TextView) convertView.findViewById(R.id.LBLDESCRIPCION);
            holder.LBLPERJUDICADOS = (TextView) convertView.findViewById(R.id.LBLPERJUDICADOS);

            convertView.setTag(holder);


        } else {
            holder = (Personalizacion_Problema.ViewHolder) convertView.getTag();


        }

        holder.LBLNUMERO.setText("NUMERO DEL PROBLEMA : " + lista.get(position).getNumero());
        holder.LBLPROY.setText("NUMERO PROYECTO : " + lista.get(position).getNUMPROY());
        holder.LBLNIVEL.setText("NIVEL : " + lista.get(position).getNivel());
        holder.LBLDESCRIPCION.setText("DESCRIPCION : " + lista.get(position).getDescripcion());
        holder.LBLPERJUDICADOS.setText("PERJUDICADOS : " + lista.get(position).getPerjudicado());
        return convertView;

    }
}
