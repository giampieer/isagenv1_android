package layout.Relacion;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import principal.android.empresa.eduardo.OnScrollUpDownListener;
import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.ProyectoBean;
import principal.android.empresa.eduardo.dao.ProyectoDao;

import static android.support.v4.view.ViewCompat.animate;

public class BlankFragment extends Fragment {
 ArrayList<ProyectoBean> listado;
    ListView LSTRELACION;
     SwipeRefreshLayout swipeContainer;
    ProyectoDao objdao=null;
    int estado;
    FloatingActionButton fab;
    private static final int DURATION = 150;
    public BlankFragment() {

    }


    @SuppressLint("InlinedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       View obj=inflater.inflate(R.layout.fragment_blank, container, false);
        //swipeContainer = (SwipeRefreshLayout) obj.findViewById(R.id.swipe_proy);
        //swipeContainer.setOnRefreshListener(this);
        LSTRELACION=(ListView)obj.findViewById(R.id.LISTOBJETIVOPROY);

        listar();
        LSTRELACION.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //fragment al cual enviar los datos
                DialogFragment fragment = new Mostrar();
                //enviar datos a solo fragments
                Bundle parametro = new Bundle();
                parametro.putString("num",listado.get(position).getNumero());
                parametro.putString("nombre",listado.get(position).getTitulo());
                parametro.putString("duracion",listado.get(position).getDuracion());
                parametro.putString("descripcion",listado.get(position).getDescripcion());
                parametro.putString("fases",listado.get(position).getFases());
                parametro.putString("inicio",listado.get(position).getInicio());
                parametro.putString("fin",listado.get(position).getFin());
                parametro.putString("gastos",listado.get(position).getGastos());
                parametro.putString("tipo",listado.get(position).getTipo());
                parametro.putString("coordinador",listado.get(position).getCODJEFE());

                fragment.setArguments(parametro);

                fragment.show(getActivity().getSupportFragmentManager(), "etiqueta");

            }

        }
        );
        LSTRELACION.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {


        final CharSequence[] items = {"Modificar", "Eliminar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Opciones:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
               if(items[item]=="Modificar"){
                   modificar_proyecto fragment = new modificar_proyecto();
                   //enviar datos a solo fragments
                   Bundle parametro = new Bundle();
                   parametro.putString("num",listado.get(position).getNumero());
                   parametro.putString("nombre",listado.get(position).getTitulo());
                   parametro.putString("duracion",listado.get(position).getDuracion());
                   parametro.putString("descripcion",listado.get(position).getDescripcion());
                   parametro.putString("fases",listado.get(position).getFases());
                   parametro.putString("inicio",listado.get(position).getInicio());
                   parametro.putString("fin",listado.get(position).getFin());
                   parametro.putString("gastos",listado.get(position).getGastos());
                   parametro.putString("tipo",listado.get(position).getTipo());
                   parametro.putString("coordinador",listado.get(position).getCODJEFE());

                   fragment.setArguments(parametro);
                   //cambiar de fragments
                   final FragmentTransaction ft = getFragmentManager()
                           .beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                   ft.replace(R.id.contenedor_fragments, fragment);

                   //APARESCA BLANKFRAGMENT AL RETROCER
                   ft.addToBackStack("null");

                   ft.commit();
               }else{
                   android.app.AlertDialog.Builder dialogo1 = new android.app.AlertDialog.Builder(getActivity());
                   dialogo1.setTitle("Importante");
                   dialogo1.setMessage("¿Deseas eliminar el proyecto?");
                   dialogo1.setCancelable(false);
                   dialogo1.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialogo1, int id) {
                           String TXTNUM;
                           TXTNUM= listado.get(position).getNumero();
                           String parametros[]=new String[1];
                           parametros[0]=TXTNUM;
                           new async_eliminar_proy().execute(parametros);                       }
                   });
                   dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialogo1, int id) {

                       }
                   });
                   dialogo1.show();



               }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

        return true;
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

    class async_eliminar_proy extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Eliminando", true);

        }

        @Override
        protected String doInBackground(String... params) {
            objdao=new ProyectoDao();
            String mensaje="";
            String TXTNUM=params[0];
            estado=objdao.Eliminar_Proyecto(TXTNUM);

            return mensaje;
        }
        protected void onPostExecute(String result) {
            listar();
            progressDialog.dismiss();

        }

    }

    public void listar(){
        new async_listar_proy().execute();
    }
    public void listar_swipe(){
        new async_listar_proy_swipe().execute();
    }




    class async_listar_proy extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
           progressDialog = ProgressDialog.show(getActivity(), "", "Cargando Proyectos", true);

        }

        @Override
        protected String doInBackground(Void... obj) {
            String mensaje="";
            objdao=new ProyectoDao();
            listado=objdao.Listar_Proyecto();

            return mensaje;
        }
        protected void onPostExecute(String result) {

            LSTRELACION.setAdapter(new Personalizacion_relacion(getActivity(), listado));
            progressDialog.dismiss();
        }

    }
    class async_listar_proy_swipe extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... obj) {
            String mensaje="";
            objdao=new ProyectoDao();
            listado=objdao.Listar_Proyecto();

            return mensaje;
        }
        protected void onPostExecute(String result) {

            LSTRELACION.setAdapter(new Personalizacion_relacion(getActivity(), listado));
            swipeContainer.setRefreshing(false);
        }

    }


}
