package layout.Requisito;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import principal.android.empresa.eduardo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class mostrar_req extends DialogFragment {
    String cod,alcance,personal,reuniones,descripcion,numproy;
    TextView TXTNUMERO,TXTALCANCE,TXTPERSONAL,TXTREUNIONES,TXTDESCRIPCION,TXTNUMPROY;
    public mostrar_req() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View obj=inflater.inflate(R.layout.fragment_mostrar_req, container, false);
        TXTNUMERO=(TextView)obj.findViewById(R.id.TXTNUMEROM);
        TXTALCANCE=(TextView)obj.findViewById(R.id.TXTALCANCEM);
        TXTPERSONAL=(TextView)obj.findViewById(R.id.TXTPERSONALM);
        TXTREUNIONES=(TextView)obj.findViewById(R.id.TXTREUNIONESM);
        TXTDESCRIPCION=(TextView)obj.findViewById(R.id.TXTDESCRIPCIONM);
        TXTNUMPROY=(TextView) obj.findViewById(R.id.TXTNUMPROYM);
        cargardatos();
        return obj;
    }

    class async_cargar_req extends AsyncTask<Void, Void, String> {


        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando", true);

        }

        @Override
        protected String doInBackground(Void... obj) {
            String mensaje="";
            cod = getArguments().getString("cod");
            alcance = getArguments().getString("alcance");
            personal= getArguments().getString("personal");
            reuniones = getArguments().getString("reuniones");
            descripcion= getArguments().getString("descripcion");
            numproy= getArguments().getString("numproy");
            return mensaje;
        }
        protected void onPostExecute(String result) {
            TXTNUMERO.setText("NUMERO :"+cod);
            TXTALCANCE.setText("ALCANCE :"+alcance);
            TXTPERSONAL.setText("PERSONAL :"+personal);
            TXTREUNIONES.setText("REUNIONES :"+reuniones);
            TXTDESCRIPCION.setText("DESCRIPCION :"+descripcion);
            TXTNUMPROY.setText("NUM PROY :"+numproy);
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
        new async_cargar_req().execute();
    }



}
