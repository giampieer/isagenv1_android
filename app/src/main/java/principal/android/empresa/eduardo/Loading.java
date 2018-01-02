package principal.android.empresa.eduardo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONObject;

import principal.android.empresa.eduardo.bean.JefeBean;
import principal.android.empresa.eduardo.dao.ProyectoDao;

/**
 * Created by Home on 21/03/2017.
 */

public class Loading extends Activity {
    JefeBean obj=null;
    ProyectoDao obj2=null;

    int estado=0;
    String nomb,contra;
    ProyectoDao objdao=null;
    JefeBean objbean=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
        //SESION AUTOMATICO
        afirma_conexion();


    }


    public void sesion() {

        objdao = new ProyectoDao();
        objbean = new JefeBean();
        //captura los datos almacenados para iniciar seison automatico
        SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        //comillas vacia para si hay error en el id o pass
        String usuario = prefs.getString("ID", "");
        String pass = prefs.getString("PASS", "");
        String param[]=new  String[2];
        param[0]=usuario;
        param[1]=pass;
        new sesion_automatico().execute(param);

    }


    //COMPRUEBA SI ESTA ACTIVADO RED WIFI O MOVIL
    public static boolean compruebaConexion(Context context) {

        boolean connected = false;

        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }

        return connected;
    }


    //SESION AUTOMATICO
    public void afirma_conexion(){

        if (compruebaConexion(this)) {
            sesion();
        }else {
            Intent objIntent = new Intent(Loading.this, login.class);
            //EVITAR APARESCA TECLADO
            objIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(objIntent);
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            finish();
            Toast.makeText(getBaseContext(),"No hay conexión a internet ", Toast.LENGTH_SHORT).show();
        }
    }


    class sesion_automatico extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        JSONObject objeto;


        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Loading.this, "","Cargando ...", true);

        }

        @Override
        protected String doInBackground(String... params) {
            obj=new JefeBean();
            obj2=new ProyectoDao();
            String mensaje="";
            obj.setID(params[0]);
            obj.setPASS(params[1]);
            nomb=params[0];
            contra=params[1];
            estado=obj2.login_jefe(obj);


            return mensaje;
        }
        protected void onPostExecute(String result) {

            if (estado == 1) {
                Intent objIntent = new Intent(Loading.this, MainActivity.class);
                //EVITAR APARESCA TECLADO
                objIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(objIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                finish();
                Toast.makeText(getApplicationContext(), "BIENVENIDO :" + nomb.toUpperCase() + "", Toast.LENGTH_LONG).show();

            } else {
                Intent objIntent = new Intent(Loading.this, login.class);
                //EVITAR APARESCA TECLADO
                objIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(objIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                finish();
                Toast.makeText(getApplicationContext(), "INICIE SESION", Toast.LENGTH_LONG).show();


            }
            progressDialog.dismiss();

        }

    }

}
