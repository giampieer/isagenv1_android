package layout.Requisito;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.RequisitoBean;

/**
 * Created by Home on 21/11/2016.
 */
public class Personalizacion_requisito extends BaseAdapter {


    private  static ArrayList<RequisitoBean> lista;
    private LayoutInflater minflater;


    static  class ViewHolder{
        TextView LBLNUMERO;
        TextView LBLALCANCE;
        TextView LBLPERSONAL;
        TextView LBLREUNIONES;
        TextView LBLDESCRIPCION;
        TextView LBLNUMPROY;





    }


    public Personalizacion_requisito(Context context, ArrayList<RequisitoBean> lista){

        this.minflater=minflater.from(context);
        this.lista=lista;


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


        ViewHolder holder;
        if(convertView==null){

            convertView=minflater.inflate(R.layout.grilla_requisito,null);
            holder=new ViewHolder();
            /*holder.LBLNUMERO=(TextView)convertView.findViewById(R.id.LBLNUMERO);
            holder.LBLALCANCE=(TextView)convertView.findViewById(R.id.LBLALCANCE);
            holder.LBLPERSONAL=(TextView)convertView.findViewById(R.id.LBLPERSONAL);
            holder.LBLREUNIONES=(TextView)convertView.findViewById(R.id.LBLREUNIONES);*/
            holder.LBLDESCRIPCION=(TextView)convertView.findViewById(R.id.LBLDESCRIPCION);
            holder.LBLNUMPROY=(TextView)convertView.findViewById(R.id.LBLNUMPROY);


            convertView.setTag(holder);




        }else{
            holder=(ViewHolder)convertView.getTag();


        }
/*
        holder.LBLNUMERO.setText("NUMERO DEL REQUISITO: "+lista.get(position).getNumero());
        holder.LBLALCANCE.setText("ALCANCE : "+lista.get(position).getAlcance());
        holder.LBLPERSONAL.setText("PERSONAL : "+lista.get(position).getPersonal());
        holder.LBLREUNIONES.setText("REUNION : "+lista.get(position).getReunion());*/
        holder.LBLDESCRIPCION.setText("DESCRIPCION : "+lista.get(position).getDescripcion());
        holder.LBLNUMPROY.setText("NUMERO DEL PROYECTO : "+lista.get(position).getNUMPROY());


        return  convertView;

    }



}
