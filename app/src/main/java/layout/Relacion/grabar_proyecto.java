package layout.Relacion;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.JefeBean;
import principal.android.empresa.eduardo.bean.ProyectoBean;
import principal.android.empresa.eduardo.dao.ProyectoDao;


public class grabar_proyecto extends Fragment implements View.OnClickListener {


    ArrayList<JefeBean> listado1;

    int estado;
    String estado2;
    Spinner spinner,combo;
    ProyectoBean obj2=null;
    ProyectoDao obj=null;
    TextView TXTINICIO,TXTFIN;
    EditText TXTNUMERO,TXTTITULO,TXTDURACION,TXTDESCRIPCION,TXTFASES,TXTGASTOS;
    Button BTNGRABAR;

    public grabar_proyecto() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View obj=inflater.inflate(R.layout.fragment_grab, container, false);
 TXTNUMERO=(EditText)obj.findViewById(R.id.TXTNUMERO);
        TXTTITULO=(EditText)obj.findViewById(R.id.TXTTITULO);
        TXTDURACION=(EditText)obj.findViewById(R.id.TXTDURACION);
        TXTDESCRIPCION=(EditText)obj.findViewById(R.id.TXTDESCRIPCION);

        TXTFASES=(EditText)obj.findViewById(R.id.TXTFASES);
        TXTINICIO=(TextView) obj.findViewById(R.id.fechaini);
        TXTINICIO.setOnClickListener(this);
        TXTFIN=(TextView) obj.findViewById(R.id.fechafin);
        TXTFIN.setOnClickListener(this);
        TXTGASTOS=(EditText)obj.findViewById(R.id.TXTGASTOS);
        combo=(Spinner)obj.findViewById(R.id.combojefe);
        cargar_datos();

       BTNGRABAR=(Button)obj.findViewById(R.id.BTNGRABAR);
        BTNGRABAR.setOnClickListener(this);

        spinner = (Spinner)obj. findViewById(R.id.stipo);
        String[] valores = {"Publicos","Privados","Experimentales","Normalizados","Productivos","Sociales", "Investigacion"};
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, valores));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
              //  Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio

            }
        });



        return obj;
    }


    public void FechaInicio(View v) {
        DialogFragment newFragment = new FechaInicio();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public void FechaFin(View v) {
        DialogFragment newFragment = new FechaFin();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }


    public void grabar(){
        String estado;
        String TXTNUM, TXTTIT, TXTTIP, TXTDUR, TXTDES, TXTFAS, TXTINI, TXTFI, TXTGAS, TXTCON,TXTJEFE;
        TXTNUM = TXTNUMERO.getText().toString();
        TXTTIT = TXTTITULO.getText().toString();
        TXTTIP =spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
        //TXTCON =combo.getItemAtPosition(combo.getSelectedItemPosition()).toString();

        JefeBean obj=(JefeBean)combo.getSelectedItem();
        TXTCON=String.valueOf(obj.getCODJEFE());

        TXTDUR = TXTDURACION.getText().toString();
        TXTDES = TXTDESCRIPCION.getText().toString();
        TXTFAS = TXTFASES.getText().toString();
        TXTINI = TXTINICIO.getText().toString();
        TXTFI = TXTFIN.getText().toString();
        TXTGAS = TXTGASTOS.getText().toString();
        String parametros[]=new String[10];
        parametros[0]=TXTNUM;
        parametros[1]=TXTTIT;
        parametros[2]=TXTTIP;
        parametros[3]=TXTDUR;
        parametros[4]=TXTDES;
        parametros[5]=TXTFAS;
        parametros[6]=TXTINI;
        parametros[7]=TXTFI;
        parametros[8]=TXTGAS;
        parametros[9]=TXTCON;


        //TXTCON = TXTCOD.getText().toString();
        // String ruta = "http://mariscal2424.whelastic.net/gestor1/ProyectoServlet?op=12&txtnum="+TXTNUM+"&txttit="+TXTTIT+"&txtdur="+TXTDUR+"&txtdes="+TXTDES+"&txttip="+TXTTIP+"&txtcan="+TXTFAS+"&txtini="+TXTINI+"&txtfin="+TXTFI+"&txtpre="+TXTGAS+"&txtcodjefe="+TXTCON;



            if(TXTNUMERO.length()==0){
                TXTNUMERO.setError("INGRESE EL NUMERO");
                TXTNUMERO.requestFocus();
            }else if(TXTTITULO.length()==0){
                TXTTITULO.setError("INGRESE EL TITULO DEL PROYECTO");
                TXTTITULO.requestFocus();

            }else if(TXTDURACION.length()==0){
                TXTDURACION.setError("INGRESE LA DURACION");
                TXTDURACION.requestFocus();
            }else if(TXTDESCRIPCION.length()==0){
                TXTDESCRIPCION.setError("INGRESE LA DESCRIPCION");
                TXTDESCRIPCION.requestFocus();
            }else if(TXTFASES.length()==0){
                TXTFASES.setError("INGRESE LA CANT DE FASES");
                TXTFASES.requestFocus();
            }else if(TXTINICIO.length()==0){
                TXTINICIO.setError("INGRESE LA FECHA DE INICIO");
                TXTINICIO.requestFocus();
            }else if(TXTFIN.length()==0){
                TXTFIN.setError("INGRESE LA FECHA DE ENTREGA");
                TXTFIN.requestFocus();
            }else if(TXTGASTOS.length()==0){
                TXTGASTOS.setError("INGRESE EL PRESUPUESTO");
                TXTGASTOS.requestFocus();

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
        if (v == TXTINICIO) {

            FechaInicio(getView());

        }

        if (v == TXTFIN) {

            FechaFin(getView());

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
            obj=new ProyectoDao();
            obj2=new ProyectoBean();
            String mensaje="";
            obj2.setNumero(params[0]);
            obj2.setTitulo(params[1]);
            obj2.setTipo(params[2]);
            obj2.setDuracion(params[3]);
            obj2.setDescripcion(params[4]);
            obj2.setFases(params[5]);
            obj2.setInicio(params[6]);
            obj2.setFin(params[7]);
            obj2.setGastos(params[8]);
            obj2.setCODJEFE(params[9]);
             estado=obj.Grabar_Proyecto(obj2);
            if (estado==1){
                Snackbar.make(getView(), "Registro Grabado Satisfactoriamente", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else{
                Snackbar.make(getView(), "Registro No Grabado", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
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
            obj=new ProyectoDao();
            estado2=obj.generar_cod();

            //cargar combo de jefes
            listado1=obj.combo();

            return mensaje;
        }
        protected void onPostExecute(String result) {
            //siempre se guarda los datos en editetxt en string
            TXTNUMERO.setText(estado2);
            TXTNUMERO.setEnabled(false);
            //guardar datos en combo
            combo.setAdapter(new ArrayAdapter<JefeBean>(getActivity(), android.R.layout.simple_spinner_item, listado1));

            progressDialog.dismiss();

        }

    }
}
