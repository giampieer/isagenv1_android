package layout.Relacion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.ProyectoBean;



public class Personalizacion_relacion extends BaseAdapter {


    private  static ArrayList<ProyectoBean> lista;
    private LayoutInflater minflater;



    static  class ViewHolder{
        TextView LBLNUMERO;
        TextView LBLTITULO;
        TextView LBLDURACION;
        TextView LBLDESCRIPCION;
        TextView LBLTIPO;
        TextView LBLFASES;
        TextView LBLINICIO;
        TextView LBLFIN;
        TextView LBLGASTOS;
        TextView LBLCODJEFE;



    }


    public Personalizacion_relacion(Context context, ArrayList<ProyectoBean> lista){

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

            convertView=minflater.inflate(R.layout.grilla_relacion,null);
            holder=new ViewHolder();
            holder.LBLNUMERO=(TextView)convertView.findViewById(R.id.LBLNUMERO);
            holder.LBLTITULO=(TextView)convertView.findViewById(R.id.LBLNIVEL);
           /* holder.LBLDURACION=(TextView)convertView.findViewById(R.id.LBLDESCRIPCION);
            holder.LBLDESCRIPCION=(TextView)convertView.findViewById(R.id.LBLFINALIDAD);
            holder.LBLTIPO=(TextView)convertView.findViewById(R.id.LBLTIPO);
            holder.LBLFASES=(TextView)convertView.findViewById(R.id.LBLNUMPROY);

            holder.LBLINICIO=(TextView)convertView.findViewById(R.id.LBLINICIO);
            holder.LBLFIN=(TextView)convertView.findViewById(R.id.LBLFIN);
            holder.LBLGASTOS=(TextView)convertView.findViewById(R.id.LBLGASTOS);
            holder.LBLCODJEFE=(TextView)convertView.findViewById(R.id.LBLCODJEFE);*/

            convertView.setTag(holder);




        }else{
            holder=(ViewHolder)convertView.getTag();


        }

        holder.LBLNUMERO.setText("NUMERO : "+lista.get(position).getNumero());
        holder.LBLTITULO.setText("NOMBRE : "+lista.get(position).getTitulo());
      /*  holder.LBLDURACION.setText("DURACION : "+lista.get(position).getDuracion());
        holder.LBLDESCRIPCION.setText("DESCRIPCION : "+lista.get(position).getDescripcion());
        holder.LBLTIPO.setText("TIPO : "+lista.get(position).getTipo());
        holder.LBLFASES.setText("FASES : "+lista.get(position).getFases());
        holder.LBLINICIO.setText("INICIO : "+lista.get(position).getInicio());
        holder.LBLFIN.setText("FIN : "+lista.get(position).getFin());
        holder.LBLGASTOS.setText("GASTOS : "+lista.get(position).getGastos());
        holder.LBLCODJEFE.setText("CODIGO DEL JEFE : "+lista.get(position).getCODJEFE());*/
        return  convertView;

    }


}
