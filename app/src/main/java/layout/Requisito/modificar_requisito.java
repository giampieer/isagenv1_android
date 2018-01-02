package layout.Requisito;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.ProyectoBean;
import principal.android.empresa.eduardo.bean.RequisitoBean;
import principal.android.empresa.eduardo.dao.ProyectoDao;
import principal.android.empresa.eduardo.dao.RequisitoDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class modificar_requisito extends Fragment implements View.OnClickListener {
    ArrayList<ProyectoBean> listado;
    int estado;
    String cod,alcance,numeroproy,descrip,cantperso,cantreu;

    Spinner spinner;

    private static final int TIPO_DIALOGO=0;
    private  static DatePickerDialog.OnDateSetListener oyenteSelectorFecha;
    EditText TXTNUMERO,TXTALCANCE,TXTDESCRIPCION,TXTPERSONAL,TXTREUNIONES,TXTNUMPROYECTO;
    Button BTNMODIFICAR,BTNELIMINAR;
    RequisitoDao obj=null;
    RequisitoBean obj2=null;



    public modificar_requisito() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View obj=inflater.inflate(R.layout.fragment_modificar_requisito, container, false);

        TXTNUMERO=(EditText)obj.findViewById(R.id.TXTNUMEROMR);
        TXTALCANCE=(EditText)obj.findViewById(R.id.TXTALCANCEMR);
        TXTDESCRIPCION=(EditText)obj.findViewById(R.id.TXTDESCRIPMR);
        TXTNUMPROYECTO=(EditText)obj.findViewById(R.id.TXTNUMERPROYECTMR);
        TXTPERSONAL=(EditText)obj.findViewById(R.id.TXTCANTPERMR);
        TXTREUNIONES=(EditText)obj.findViewById(R.id.TXTCANTREUMR);


        BTNMODIFICAR=(Button)obj.findViewById(R.id.BTNMODIFICAR);
        BTNMODIFICAR.setOnClickListener(this);
        BTNELIMINAR=(Button)obj.findViewById(R.id.BTNELIMINAR);
        BTNELIMINAR.setOnClickListener(this);





        cargardatos();



        return obj;
    }

    public void eliminar2(){

        String TXTNUM;
        TXTNUM= TXTNUMERO.getText().toString();
        String parametros[]=new String[1];
        parametros[0]=TXTNUM;
        new async_eliminar_proy().execute(parametros);
    }



    class async_eliminar_proy extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Eliminando", true);

        }

        @Override
        protected String doInBackground(String... params) {
            obj=new RequisitoDao();
            String mensaje="";
            String TXTNUM=params[0];
            estado=obj.Eliminar_Requisito(TXTNUM);
            return mensaje;
        }
        protected void onPostExecute(String result) {
            retroceder();
            progressDialog.dismiss();

        }

    }
    public void modificar() {
        String estado;
        String TXTNUM, TXTALC, TXTDESCRIP, TXTPERSO, TXTREU,TXTNUMPROY;

        TXTNUM = TXTNUMERO.getText().toString();
        TXTALC= TXTALCANCE.getText().toString();
        TXTDESCRIP = TXTDESCRIPCION.getText().toString();
        TXTPERSO= TXTPERSONAL.getText().toString();
        TXTREU = TXTREUNIONES.getText().toString();
        TXTNUMPROY = TXTNUMPROYECTO.getText().toString();
        String parametros[]=new String[6];
        parametros[0]=TXTNUM;
        parametros[1]=TXTALC;
        parametros[2]=TXTDESCRIP;
        parametros[3]=TXTPERSO;
        parametros[4]=TXTREU;
        parametros[5]=TXTNUMPROY;



        if(TXTNUMERO.length()==0){
            TXTNUMERO.setError("INGRESE EL NUMERO");
            TXTNUMERO.requestFocus();
        }else if(TXTALCANCE.length()==0){
            TXTALCANCE.setError("INGRESE EL TITULO DEL PROYECTO");
            TXTALCANCE.requestFocus();

        }else if(TXTDESCRIPCION.length()==0){
            TXTDESCRIPCION.setError("INGRESE LA DURACION");
            TXTDESCRIPCION.requestFocus();
        }else if(TXTPERSONAL.length()==0){
            TXTPERSONAL.setError("INGRESE LA DESCRIPCION");
            TXTPERSONAL.requestFocus();
        }else if(TXTREUNIONES.length()==0){
            TXTREUNIONES.setError("INGRESE LA CANT DE FASES");
            TXTREUNIONES.requestFocus();

        }else {

            new async_modificar_proy().execute(parametros);

        }


    }
    class async_modificar_proy extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Modificando", true);

        }

        @Override
        protected String doInBackground(String... params) {
            obj=new RequisitoDao();
            obj2=new RequisitoBean();
            String mensaje="";
            obj2.setNumero(params[0]);
            obj2.setAlcance(params[1]);
            obj2.setDescripcion(params[2]);
            obj2.setPersonal(params[3]);
            obj2.setReunion(params[4]);
            obj2.setNUMPROY(params[5]);
            estado=obj.Modificar_Requisito(obj2);
            return mensaje;
        }
        protected void onPostExecute(String result) {
            retroceder();
            progressDialog.dismiss();

        }

    }

    class async_cargar_proy extends AsyncTask<Void, Void, String> {


        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando", true);

        }

        @Override
        protected String doInBackground(Void... obj) {
            String mensaje="";
            cod = getArguments().getString("num");
            alcance = getArguments().getString("alcance");
            cantperso= getArguments().getString("personal");
            descrip = getArguments().getString("descripcion");
            cantreu= getArguments().getString("reunion");
            numeroproy= getArguments().getString("numproy");

            return mensaje;
        }
        protected void onPostExecute(String result) {
            TXTNUMERO.setText(cod);
            TXTNUMERO.setEnabled(false);
            TXTALCANCE.setText(alcance);
            TXTNUMPROYECTO.setText(numeroproy);
            TXTDESCRIPCION.setText(descrip);
            TXTREUNIONES.setText(cantreu);
            TXTPERSONAL.setText(cantperso);

            progressDialog.dismiss();

        }

    }

    public void retroceder(){
        FragmentManager fragmentManager = getFragmentManager();
        //retroceder al modificar
        if (fragmentManager.getBackStackEntryCount() > 1){
            fragmentManager.popBackStackImmediate();
            fragmentManager.beginTransaction().commit();
        }
    }
    public void cargardatos(){
        new async_cargar_proy2().execute();
    }



    class async_cargar_proy2 extends AsyncTask<Void, Void, String> {


        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando", true);

        }

        @Override
        protected String doInBackground(Void... obje) {
            String mensaje="";
            cod = getArguments().getString("num");
            int codi=Integer.parseInt(cod);
           obj2=new RequisitoBean();
            obj=new RequisitoDao();
            obj2=obj.Listar_Requisito_pdf(codi);

            return mensaje;
        }
        protected void onPostExecute(String result) {
            TXTNUMERO.setText(obj2.getNumero());
            TXTNUMERO.setEnabled(false);
            TXTALCANCE.setText(obj2.getAlcance());
            TXTNUMPROYECTO.setText(obj2.getNUMPROY());
            TXTDESCRIPCION.setText(obj2.getDescripcion());
            TXTREUNIONES.setText(obj2.getReunion());
            TXTPERSONAL.setText(obj2.getPersonal());

            progressDialog.dismiss();

        }

    }






    @Override
    public void onClick(View v) {
        if (v == BTNMODIFICAR) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
            dialogo1.setTitle("Importante");
            dialogo1.setMessage("¿Deseas modificar el requisito?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    modificar();
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                }
            });
            dialogo1.show();

        }

        if (v == BTNELIMINAR) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
            dialogo1.setTitle("Importante");
            dialogo1.setMessage("¿Deseas eliminar el requisito?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    eliminar2();
                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                }
            });
            dialogo1.show();
        }


    }

}
