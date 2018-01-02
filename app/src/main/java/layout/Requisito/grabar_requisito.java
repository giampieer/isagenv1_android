package layout.Requisito;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.ProyectoBean;
import principal.android.empresa.eduardo.bean.RequisitoBean;
import principal.android.empresa.eduardo.dao.RequisitoDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class grabar_requisito extends Fragment implements View.OnClickListener{


    ArrayList<ProyectoBean> listado;


    private int año;
    private int mes;
    private int dia;
    int estado;
    String estado2;
    Spinner combo;
    RequisitoBean obj2=null;
    RequisitoDao obj=null;


    EditText TXTNUMERO,TXTALCANCE,TXTPERSONAL,TXTREUNION,TXTDESCRIPCION;
    Button BTNGRABAR;

    public grabar_requisito() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View obj=inflater.inflate(R.layout.fragment_grabar_requisito, container, false);
        TXTNUMERO=(EditText)obj.findViewById(R.id.TXTNUMERO);
        TXTALCANCE=(EditText)obj.findViewById(R.id.TXTALCANCE);
        TXTDESCRIPCION=(EditText)obj.findViewById(R.id.TXTDESCRIP);
        TXTPERSONAL=(EditText)obj.findViewById(R.id.TXTCANTPER);
        TXTREUNION=(EditText)obj.findViewById(R.id.TXTCANTREU);

        combo=(Spinner)obj.findViewById(R.id.sproy);
        cargar_datos();

        BTNGRABAR=(Button)obj.findViewById(R.id.BTNGRABAR);
        BTNGRABAR.setOnClickListener(this);

        return obj;
    }


    public void grabar(){
        String estado;
        String TXTNUM, TXTALC, TXTPER, TXTDES, TXTREU, TXTCOMBOPROY;
        TXTNUM = TXTNUMERO.getText().toString();
        TXTALC = TXTALCANCE.getText().toString();
        TXTPER = TXTPERSONAL.getText().toString();
        TXTREU= TXTREUNION.getText().toString();
        TXTDES = TXTDESCRIPCION.getText().toString();
        ProyectoBean obj=(ProyectoBean) combo.getSelectedItem();
        TXTCOMBOPROY=obj.getNumero();


        String parametros[]=new String[6];
        parametros[0]=TXTNUM;
        parametros[1]=TXTALC;
        parametros[2]=TXTPER;
        parametros[3]=TXTREU;
        parametros[4]=TXTDES;
        parametros[5]=TXTCOMBOPROY;


        //TXTCON = TXTCOD.getText().toString();
        // String ruta = "http://mariscal2424.whelastic.net/gestor1/ProyectoServlet?op=12&txtnum="+TXTNUM+"&txttit="+TXTTIT+"&txtdur="+TXTDUR+"&txtdes="+TXTDES+"&txttip="+TXTTIP+"&txtcan="+TXTFAS+"&txtini="+TXTINI+"&txtfin="+TXTFI+"&txtpre="+TXTGAS+"&txtcodjefe="+TXTCON;



        if(TXTNUMERO.length()==0){
            TXTNUMERO.setError("INGRESE EL NUMERO");
            TXTNUMERO.requestFocus();
        }else if(TXTALCANCE.length()==0){
            TXTALCANCE.setError("INGRESE EL TITULO DEL PROYECTO");
            TXTALCANCE.requestFocus();

        }else if(TXTPERSONAL.length()==0){
            TXTPERSONAL.setError("INGRESE LA DURACION");
            TXTPERSONAL.requestFocus();
        }else if(TXTDESCRIPCION.length()==0){
            TXTDESCRIPCION.setError("INGRESE LA DESCRIPCION");
            TXTDESCRIPCION.requestFocus();
        }else if(TXTREUNION.length()==0){
            TXTREUNION.setError("INGRESE LA CANT DE FASES");
            TXTREUNION.requestFocus();


        }else if(combo.getSelectedItemPosition()==0){
            ((TextView)combo.getSelectedView()).setError("");

        }else{
            new async_grabar_proy().execute(parametros);


        }



    }





    public void cargar_datos(){
        new async_cargar_datos().execute();

    }







    public void retroceder(){
        FragmentManager fragmentManager = getFragmentManager();
        //retroceder al grabar
        if (fragmentManager.getBackStackEntryCount() > 1){
            fragmentManager.popBackStackImmediate();
            fragmentManager.beginTransaction().commit();
        }
    }
    @Override
    public void onClick(View v) {
        if (v == BTNGRABAR) {

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
            dialogo1.setTitle("Importante");
            dialogo1.setMessage("¿Deseas grabar el nuevo proyecto?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Grabar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    grabar();
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    grabar();
                }
            });
            dialogo1.show();

        }


    }


    class async_grabar_proy extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Grabando", true);

        }

        @Override
        protected String doInBackground(String... params) {
            obj=new RequisitoDao();
            obj2=new RequisitoBean();
            String mensaje="";
            obj2.setNumero(params[0]);
            obj2.setAlcance(params[1]);
            obj2.setPersonal(params[2]);
            obj2.setReunion(params[3]);
            obj2.setDescripcion(params[4]);
            obj2.setNUMPROY(params[5]);
            estado=obj.Grabar_Requisito(obj2);
            return mensaje;
        }
        protected void onPostExecute(String result) {
            retroceder();

            progressDialog.dismiss();

        }

    }


    class async_cargar_datos extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando", true);

        }

        @Override
        protected String doInBackground(Void... objt) {
            String mensaje="";
            //generar codigo
            obj=new RequisitoDao();
            estado2=obj.generar_cod();

            //cargar combo de jefes
            listado=obj.combo();

            return mensaje;
        }
        protected void onPostExecute(String result) {
            //siempre se guarda los datos en editetxt en string
            TXTNUMERO.setText(estado2);
            TXTNUMERO.setEnabled(false);
            //guardar datos en combo
            combo.setAdapter(new ArrayAdapter<ProyectoBean>(getActivity(), android.R.layout.simple_spinner_item, listado));

            progressDialog.dismiss();

        }

    }

}
