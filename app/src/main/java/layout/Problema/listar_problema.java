package layout.Problema;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;

import layout.Relacion.grabar_proyecto;
import layout.Relacion.modificar_proyecto;
import principal.android.empresa.eduardo.OnScrollUpDownListener;
import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.ProblemaBean;
import principal.android.empresa.eduardo.bean.ProyectoBean;
import principal.android.empresa.eduardo.dao.ProblemaDao;
import principal.android.empresa.eduardo.dao.ProyectoDao;
import static android.support.v4.view.ViewCompat.animate;


public class listar_problema extends Fragment   {

    ArrayList<ProblemaBean> listado;
    ListView LSTRELACION;
    SwipeRefreshLayout swipeContainer;

    ProblemaDao objdao=null;
    FloatingActionButton fab;
    private static final int DURATION = 150;
    public listar_problema() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View obj=inflater.inflate(R.layout.fragment_listar_problema, container, false);
       // swipeContainer = (SwipeRefreshLayout) obj.findViewById(R.id.swipe_prob);
        //swipeContainer.setOnRefreshListener(this);
        LSTRELACION=(ListView)obj.findViewById(R.id.LISTOBJETIVOSPROB);
        listar();
        LSTRELACION.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //fragment al cual enviar los datos
                modificar_proyecto  fragment = new modificar_proyecto();
                //enviar datos a solo fragments
                Bundle parametro = new Bundle();

                parametro.putString("num",listado.get(position).getNumero());
                parametro.putString("numproy",listado.get(position).getNUMPROY());
                parametro.putString("nivel",listado.get(position).getNivel());
                parametro.putString("descripcion",listado.get(position).getDescripcion());
                parametro.putString("perjudicados",listado.get(position).getPerjudicado());

                fragment.setArguments(parametro);
                //cambiar de fragments
                final FragmentTransaction ft = getFragmentManager()
                        .beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);;
                ft.replace(R.id.contenedor_fragments, fragment);

                //APARESCA BLANKFRAGMENT AL RETROCER
                ft.addToBackStack("null");

                ft.commit();

            }
        });


        //BOTON AGREGAR PROYECTO
        fab = (FloatingActionButton) obj.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                //cambiar de fragments
                //addToBackStack=null para que  se guarde en fila
                grabar_proyecto container1Fragment = new grabar_proyecto();
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).replace(R.id.contenedor_fragments, container1Fragment).addToBackStack(null).commit();
            }
        });

        //boton flotante dinamico
        OnScrollUpDownListener.Action scrollAction = new OnScrollUpDownListener.Action() {

            private boolean hidden = true;

            @Override
            public void up() {
                if (hidden) {
                    hidden = false;
                    animate(fab)
                            .translationY(fab.getHeight() +
                                    getResources().getDimension(R.dimen.fab_margin))
                            .setInterpolator(new LinearInterpolator())
                            .setDuration(DURATION);
                }
            }

            @Override
            public void down() {
                if (!hidden) {
                    hidden = true;
                    animate(fab)
                            .translationY(0)
                            .setInterpolator(new LinearInterpolator())
                            .setDuration(DURATION);

                }
            }

        };

        LSTRELACION.setOnScrollListener(new OnScrollUpDownListener(LSTRELACION, 8, scrollAction));


        return obj;
    }
    public void listar(){
        new async_listar_prob().execute();
    }
    public void listar_swipe(){
        new async_listar_prob_swipe().execute();
    }




    class async_listar_prob extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando Problemas", true);

        }

        @Override
        protected String doInBackground(Void... obj) {
            String mensaje="";
            objdao=new ProblemaDao();
            listado=objdao.Listar_Problema();

            return mensaje;
        }
        protected void onPostExecute(String result) {

            LSTRELACION.setAdapter(new Personalizacion_Problema(getActivity(), listado));
            progressDialog.dismiss();
        }

    }
    class async_listar_prob_swipe extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... obj) {
            String mensaje="";
            objdao=new ProblemaDao();
            listado=objdao.Listar_Problema();

            return mensaje;
        }
        protected void onPostExecute(String result) {

            LSTRELACION.setAdapter(new Personalizacion_Problema(getActivity(), listado));
            swipeContainer.setRefreshing(false);
        }

    }
}
