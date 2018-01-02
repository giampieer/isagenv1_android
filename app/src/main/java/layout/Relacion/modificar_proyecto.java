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
import android.support.v4.app.FragmentTransaction;
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
import principal.android.empresa.eduardo.bean.ProyectoBean;
import principal.android.empresa.eduardo.dao.ProyectoDao;


public class modificar_proyecto extends Fragment implements View.OnClickListener {
    int estado;
    String cod,nomb,dura,descrip,fases,inicio,fin,gastos,coord,tipo;
    Spinner spinner;
    TextView TXTINICIO,TXTFIN;
    EditText TXTNUMERO,TXTTITULO,TXTDURACION,TXTDESCRIPCION,TXTFASES,TXTGASTOS,TXTCOD;
    Button BTNMODIFICAR,BTNPDF;
    ProyectoDao obj=null;
    ProyectoBean obj2=null;

    public modificar_proyecto() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View obj=inflater.inflate(R.layout.fragment_modificar_proyecto, container, false);
        TXTNUMERO=(EditText)obj.findViewById(R.id.TXTNUMEROM);
        TXTTITULO=(EditText)obj.findViewById(R.id.TXTTITULOM);
        TXTDURACION=(EditText)obj.findViewById(R.id.TXTDURACIONM);
        TXTDESCRIPCION=(EditText)obj.findViewById(R.id.TXTDESCRIPCIONM);

        TXTFASES=(EditText)obj.findViewById(R.id.TXTFASESM);
        TXTINICIO=(TextView) obj.findViewById(R.id.fechaini);
        TXTINICIO.setOnClickListener(this);
        TXTFIN=(TextView) obj.findViewById(R.id.fechafin);
        TXTFIN.setOnClickListener(this);
        TXTGASTOS=(EditText)obj.findViewById(R.id.TXTGASTOSM);
        TXTCOD=(EditText)obj.findViewById(R.id.TXTCODM);

        BTNMODIFICAR=(Button)obj.findViewById(R.id.BTNMODIFICAR);
        BTNMODIFICAR.setOnClickListener(this);

        BTNPDF=(Button)obj.findViewById(R.id.BTNPDF);

        BTNPDF.setOnClickListener(this);
        spinner = (Spinner) obj.findViewById(R.id.stipoM);
        String[] valores = {"Publicos","Privados","Experimentales","Normalizados","Productivos","Sociales", "Investigacion"};
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, valores));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
               // Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // vacio

            }
        });
        cargardatos();




        return obj;
    }


public void modificar() {
        String estado;
        String TXTNUM, TXTTIT, TXTTIP, TXTDUR, TXTDES, TXTFAS, TXTINI, TXTFI, TXTGAS, TXTCON;

        TXTCON=TXTCOD.getText().toString();
    TXTNUM = TXTNUMERO.getText().toString();
    TXTTIT = TXTTITULO.getText().toString();
    TXTTIP =spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
    //TXTCON =combo.getItemAtPosition(combo.getSelectedItemPosition()).toString();
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
            estado=obj.Modificar_Proyecto(obj2);
            if (estado==1){
                Snackbar.make(getView(), "Registro Modificado Satisfactoriamente", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }else{
                Snackbar.make(getView(), "Registro No Modificado", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return mensaje;
        }
        protected void onPostExecute(String result) {
            retroceder();
            progressDialog.dismiss();

        }

    }
    public void FechaInicio(View v) {
        DialogFragment newFragment = new FechaInicio();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public void FechaFin(View v) {
        DialogFragment newFragment = new FechaFin();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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
            nomb = getArguments().getString("nombre");
            dura= getArguments().getString("duracion");
            descrip = getArguments().getString("descripcion");
            fases= getArguments().getString("fases");
            inicio= getArguments().getString("inicio");
            fin= getArguments().getString("fin");
            gastos = getArguments().getString("gastos");
            coord= getArguments().getString("coordinador");
            return mensaje;
        }
        protected void onPostExecute(String result) {
            TXTNUMERO.setText(cod);
            TXTNUMERO.setEnabled(false);
            TXTTITULO.setText(nomb);
            TXTDURACION.setText(dura);
            TXTDESCRIPCION.setText(descrip);
            TXTFASES.setText(fases);
            TXTINICIO.setText(inicio);
            TXTFIN.setText(fin);
            TXTGASTOS.setText(gastos);
            TXTCOD.setText(coord);
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
        new async_cargar_proy().execute();
    }
 @Override
    public void onClick(View v) {
        if (v == BTNMODIFICAR) {
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
            dialogo1.setTitle("Importante");
            dialogo1.setMessage("¿Deseas modificar el proyecto?");
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
        if (v == BTNPDF) {

            //fragment al cual enviar los datos
            pdf_proyecto fragment = new pdf_proyecto();
            //enviar datos a solo fragments
            Bundle parametro = new Bundle();
            parametro.putString("num",cod);


            fragment.setArguments(parametro);
            //cambiar de fragments
            final FragmentTransaction ft = getFragmentManager()
                    .beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            ft.replace(R.id.contenedor_fragments, fragment);

            //APARESCA BLANKFRAGMENT AL RETROCER
            ft.addToBackStack("null");

            ft.commit();
        }

        if (v == TXTINICIO) {

            FechaInicio(getView());

        }

        if (v == TXTFIN) {

            FechaFin(getView());

        }


    }

}
