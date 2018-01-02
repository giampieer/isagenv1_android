package layout.Relacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.ProyectoBean;
import principal.android.empresa.eduardo.dao.ProyectoDao;

/**
 * Created by Home on 09/04/2017.
 */

public class Mostrar extends DialogFragment  {
    String cod,nomb,dura,descrip,fases,inicio,fin,gastos,coord,tipo;

    Spinner spinner;
    TextView TXTINICIO,TXTFIN;
    TextView TXTNUMERO,TXTTITULO,TXTDURACION,TXTDESCRIPCION,TXTFASES,TXTGASTOS,TXTCOD;

    public Mostrar() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View obj=inflater.inflate(R.layout.fragment_mostrar_proy, container, false);
        TXTNUMERO=(TextView)obj.findViewById(R.id.TXTNUMEROM);
        TXTTITULO=(TextView)obj.findViewById(R.id.TXTTITULOM);
        TXTDURACION=(TextView)obj.findViewById(R.id.TXTDURACIONM);
        TXTDESCRIPCION=(TextView)obj.findViewById(R.id.TXTDESCRIPCIONM);

        TXTFASES=(TextView)obj.findViewById(R.id.TXTFASESM);
        TXTINICIO=(TextView) obj.findViewById(R.id.fechaini);
        TXTFIN=(TextView) obj.findViewById(R.id.fechafin);
        TXTGASTOS=(TextView)obj.findViewById(R.id.TXTGASTOSM);
        TXTCOD=(TextView)obj.findViewById(R.id.TXTCODM);

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
            TXTNUMERO.setText("NUMERO :"+cod);
            TXTTITULO.setText("TITULO :"+nomb);
            TXTDURACION.setText("DURACION :"+dura);
            TXTDESCRIPCION.setText("DESCRIPCION :"+descrip);
            TXTFASES.setText("FASES :"+fases);
            TXTINICIO.setText("FECHA INICIO :"+inicio);
            TXTFIN.setText("FECHA FIN :"+fin);
            TXTGASTOS.setText("GASTOS :"+gastos);
            TXTCOD.setText("COD COORDINADOR :"+coord);
            spinner.setEnabled(false);
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



}
