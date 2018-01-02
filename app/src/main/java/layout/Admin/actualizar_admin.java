package layout.Admin;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import principal.android.empresa.eduardo.Loading;
import principal.android.empresa.eduardo.R;
import principal.android.empresa.eduardo.bean.JefeBean;
import principal.android.empresa.eduardo.dao.AdminDao;


/**
 * A simple {@link Fragment} subclass.
 */
public class actualizar_admin extends Fragment implements View.OnClickListener {
    String cod,nomb,correo,tel,area,id,pass;
    Button btncorreo,btnmodificar;
    JefeBean objjefebean=null;
    JefeBean obj=null;
    AdminDao objjefedao=null;
    EditText txtnombre,txtcorreo,txttel;
    Spinner stipo;
    int estado;
    Session session;
    String correos,contraseña,email,mensaje;
    public actualizar_admin() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View obj=inflater.inflate(R.layout.fragment_actualizar_admin, container, false);
        btncorreo=(Button)obj.findViewById(R.id.btncorreos);
        btncorreo.setOnClickListener(this);
        btnmodificar=(Button)obj.findViewById(R.id.btnmod);
        btnmodificar.setOnClickListener(this);

        txtnombre=(EditText)obj.findViewById(R.id.txtnombre);
        txtcorreo=(EditText)obj.findViewById(R.id.txtcorreo);
        txttel=(EditText)obj.findViewById(R.id.txttelefono);
        cargardatos();
        return obj;
    }

    class async_actualizar_admin extends AsyncTask<String, Void, String> {


        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Cargando", true);

        }

        @Override
        protected String doInBackground(String... params) {
            String mensaje="";
            objjefebean=new JefeBean();
            objjefedao=new AdminDao();
            obj=new JefeBean();
            objjefebean.setID(params[0]);
            objjefebean.setPASS(params[1]);
            obj=objjefedao.cargar_admin(objjefebean);

            cod = String.valueOf(obj.getCODJEFE());
            nomb = obj.getNOMBJEFE();
            correo= obj.getEMAJEFE();
            tel= obj.getTELFJEFE();
            area= obj.getAREAJEFE();
            id= obj.getID();
            pass= obj.getPASS();

            return mensaje;
        }
        protected void onPostExecute(String result) {
            txtnombre.setText(nomb);
            txtcorreo.setText(correo);
            txttel.setText(tel);

            progressDialog.dismiss();

        }

    }

    public void cargardatos(){

        SharedPreferences prefs = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        //comillas vacia para si hay error en el id o pass
        String usuario = prefs.getString("ID", "");
        String pass = prefs.getString("PASS", "");
        String param[]=new  String[2];
        param[0]=usuario;
        param[1]=pass;
        new async_actualizar_admin().execute(param);
    }


    public void modificar() {
        String estado;
        String TXTNOMBRE, TXTCORREO, TXTTEL,TXTCOD,TXTAREA,TXTID,TXTPASS;


        SharedPreferences prefs = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        //comillas vacia para si hay error en el id o pass
        String usuario = prefs.getString("ID", "");
        String pass = prefs.getString("PASS", "");
        objjefebean=new JefeBean();
        objjefedao=new AdminDao();
        obj=new JefeBean();
        objjefebean.setID(usuario);
        objjefebean.setPASS(pass);
        obj=objjefedao.cargar_admin(objjefebean);



        TXTNOMBRE = txtnombre.getText().toString();
        TXTCORREO= txtcorreo.getText().toString();
        TXTTEL = txttel.getText().toString();
        TXTCOD= String.valueOf(obj.getCODJEFE());
        TXTAREA = obj.getAREAJEFE();
        TXTID=obj.getID();
        TXTPASS=obj.getPASS();

        String parametros[]=new String[7];
        parametros[0]=TXTNOMBRE;
        parametros[1]=TXTCORREO;
        parametros[2]=TXTTEL;
        parametros[3]=TXTCOD;
        parametros[4]=TXTAREA;
        parametros[5]=TXTID;
        parametros[6]=TXTPASS;


        if(txtnombre.length()==0){
            txtnombre.setError("INGRESE EL NOMBRE DEL ADMINISTRADOR");
            txtnombre.requestFocus();
        }else if(txtcorreo.length()==0){
            txtcorreo.setError("INGRESE EL CORREO DEL ADMINISTRADOR");
            txtcorreo.requestFocus();

        }else if(txttel.length()==0){
            txttel.setError("INGRESE EL TELEFONO DEL ADMINISTRADOR");
            txttel.requestFocus();


        }else {

            new async_modificar_admin().execute(parametros);

        }


    }
    class async_modificar_admin extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Modificando", true);

        }

        @Override
        protected String doInBackground(String... params) {
            obj=new JefeBean();
            objjefedao=new AdminDao();
            String mensaje="";
            obj.setNOMBJEFE(params[0]);
            obj.setEMAJEFE(params[1]);
            obj.setTELFJEFE(params[2]);
            obj.setCODJEFE(Integer.parseInt(params[3]));
            obj.setAREAJEFE(params[4]);
            obj.setID(params[5]);
            obj.setPASS(params[6]);
            //estado=
                    estado=objjefedao.Modificar_Admin(obj);
            return mensaje;
        }
        protected void onPostExecute(String result) {
            cargardatos();
            progressDialog.dismiss();

        }

    }


    public void enviar_email(){
        email=txtcorreo.getText().toString();//correo del quien va recibir el correo
        correos="isagen24@gmail.com";
        contraseña="isagen2424";
        mensaje="RECORDAR QUE SU CONTRASEÑA DE ADMINISTRADOR ES :"+obj.getPASS();
        String param[]=new String[4];
        param[0]=email;
        param[1]=correos;
        param[2]=contraseña;
        param[3]=mensaje;
        new async_enviar_correo().execute(param);




    }

    class async_enviar_correo extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        JSONObject objeto;

        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(), "", "Enviando Correo", true);

        }

        @Override
        protected String doInBackground(String... params) {
            String mensaje="";
            final String ema,con,cor,men;
            ema=params[0];
            cor=params[1];
            con=params[2];
            men=params[3];

            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            try {
                session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(cor,con);
                    }
                });

                if(session!=null){
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(cor));
                    //Adding receiver
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(ema));
                    //Adding subject
                    message.setSubject("Gestion de Proyectos");
                    //Adding message
                    message.setText(men);

                    //Sending email
                    Transport.send(message);
                    Snackbar.make(getView(), "Correo Enviado Satisfactoriamente", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    Snackbar.make(getView(), "Correo No Enviado", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return mensaje;
        }
        protected void onPostExecute(String result) {
            cargardatos();
            progressDialog.dismiss();

        }

    }


    @Override
    public void onClick(View v) {
        if(v==btnmodificar){
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
            dialogo1.setTitle("Importante");
            dialogo1.setMessage("¿Deseas modificar datos del Administrador?");
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
        if(v==btncorreo){

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getActivity());
            dialogo1.setTitle("Importante");
            dialogo1.setMessage("¿Deseas enviar correo con tu contraseña actual?");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    enviar_email();                }
            });
            dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                }
            });
            dialogo1.show();
        }
    }




}
