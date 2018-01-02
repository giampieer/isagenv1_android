package layout.Requisito;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;

import principal.android.empresa.eduardo.OnScrollUpDownListener;
import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.RequisitoBean;
import principal.android.empresa.eduardo.dao.RequisitoDao;

import static android.support.v4.view.ViewCompat.animate;

/**
 * A simple {@link Fragment} subclass.
 */
public class listar_requisito extends Fragment {
    ArrayList<RequisitoBean> listado;
    ListView LSTRELACION;
    int estado;
    SwipeRefreshLayout swipeContainer;
    RequisitoDao objdao=null;
    private Personalizacion_requisito mExpandableListItemAdapter;
    FloatingActionButton fab;
    private static final int DURATION = 150;

    public listar_requisito() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View obj=inflater.inflate(R.layout.fragment_listar_requisito, container, false);
        LSTRELACION=(ListView)obj.findViewById(R.id.LISTOBJETIVOREQUI);
        //swipeContainer = (SwipeRefreshLayout) obj.findViewById(R.id.swipe_requi);
        //swipeContainer.setOnRefreshListener(this);
        listar();
        LSTRELACION.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //fragment al cual enviar los datos
                DialogFragment fragment = new mostrar_req();
                //enviar datos a solo fragments
                Bundle parametro = new Bundle();

                parametro.putString("cod",listado.get(position).getNumero());
                parametro.putString("alcance",listado.get(position).getAlcance());
                parametro.putString("personal",listado.get(position).getPersonal());
                parametro.putString("descripcion",listado.get(position).getDescripcion());
                parametro.putString("reuniones",listado.get(position).getReunion());
                parametro.putString("numproy",listado.get(position).getNUMPROY());

                fragment.setArguments(parametro);

                fragment.show(getActivity().getSupportFragmentManager(), "etiqueta");

            }
        });

        LSTRELACION.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {


                final CharSequence[] items = {"Modificar", "Eliminar"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Opciones:");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if(items[item]=="Modificar"){
                            //fragment al cual enviar los datos
                            modificar_requisito fragment = new modificar_requisito();
                            //enviar datos a solo fragments
                            Bundle parametro = new Bundle();

                            parametro.putString("num",listado.get(position).getNumero());
                            parametro.putString("alcance",listado.get(position).getAlcance());
                            parametro.putString("personal",listado.get(position).getPersonal());
                            parametro.putString("descripcion",listado.get(position).getDescripcion());
                            parametro.putString("reunion",listado.get(position).getReunion());
                            parametro.putString("numproy",listado.get(position).getNUMPROY());

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
                            dialogo1.setMessage("¿Deseas eliminar el requisito?");
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
        final FloatingActionButton fab = (FloatingActionButton) obj.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                //cambiar de fragments
                //addToBackStack=null para que  se guarde en fila
                grabar_requisito container1Fragment = new grabar_requisito();
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
            objdao=new RequisitoDao();
            String mensaje="";
            String TXTNUM=params[0];
            estado=objdao.Eliminar_Requisito(TXTNUM);
            return mensaje;
        }
        protected void onPostExecute(String result) {
            listar();
            progressDialog.dismiss();

        }

    }
    public void listar(){
        new async_listar_req().execute();
    }
    public void listar_swipe(){
        new async_listar_req_swipe().execute();
    }


    class async_listar_req extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando Requisitos", true);

        }

        @Override
        protected String doInBackground(Void... obj) {
            String mensaje="";
            objdao=new RequisitoDao();
            listado=objdao.Listar_Requisito();

            return mensaje;
        }
        protected void onPostExecute(String result) {

            LSTRELACION.setAdapter(new Personalizacion_requisito(getActivity(), listado));
            progressDialog.dismiss();

        }

    }
    class async_listar_req_swipe extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... obj) {
            String mensaje="";
            objdao=new RequisitoDao();
            listado=objdao.Listar_Requisito();

            return mensaje;
        }
        protected void onPostExecute(String result) {

            LSTRELACION.setAdapter(new Personalizacion_requisito(getActivity(), listado));
            swipeContainer.setRefreshing(false);

        }

    }
}