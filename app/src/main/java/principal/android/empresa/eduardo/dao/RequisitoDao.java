package principal.android.empresa.eduardo.dao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import principal.android.empresa.eduardo.Conexion.conexion_webservice;
import principal.android.empresa.eduardo.bean.ProyectoBean;
import principal.android.empresa.eduardo.bean.RequisitoBean;

/**
 * Created by Home on 14/03/2017.
 */

public class RequisitoDao {


    public String generar_cod(){
        String i="";
        //String ruta = "http://mariscal2424.whelastic.net/gestor1/ProyectoServlet?op=13&cod="+TXTNUM;

        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/RequisitoServlet?op=13";
        JSONObject objeto;
        try {
            conexion_webservice conexion=new conexion_webservice();
            objeto = conexion.InvocarHttpClient(ruta);

            i = objeto.getString("NUMERO");//recuperarmos el datos del json

        } catch (Exception e) {
            i="";
        }
        return i;

    }

    public ArrayList<ProyectoBean> combo(){
        ArrayList<ProyectoBean> listado1=new ArrayList<ProyectoBean>();
        //String ruta = "http://mariscal2424.whelastic.net/gestor1/ProyectoServlet?op=8";
        String ruta="http://mariscal24.jl.serv.net.mx/gestion/RequisitoServlet?op=14";
        try {
            conexion_webservice conexion=new conexion_webservice();
            JSONObject obj = conexion.InvocarHttpClient(ruta);
            JSONArray arreglo=obj.getJSONArray("proy");
            listado1.add(new ProyectoBean("0","SELECCIONAR PROYECTO"));
            for(int i=0;i<arreglo.length();i++){
                JSONObject objeto=arreglo.getJSONObject(i);
                listado1.add(new ProyectoBean(objeto.getString("NUMERO"),objeto.getString("TITULO")));
            }
        }catch (Exception e){
        }return  listado1;
    }



    public int Grabar_Requisito(RequisitoBean obj){
        int i=0;

        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/RequisitoServlet?op=10&txtnum="+obj.getNumero()+"&txtalc="+obj.getAlcance()+"&txtper="+obj.getPersonal()+"&txtreu="+obj.getReunion()+"&txtdes="+obj.getDescripcion()+"&txtnumproy="+obj.getNUMPROY();
        JSONObject objeto;
        try {
            conexion_webservice conexion=new conexion_webservice();
            objeto = conexion.InvocarHttpClient(ruta);
            i= Integer.parseInt(objeto.getString("NUMERO"));


        } catch (Exception e) {
            i=0;
        }



        return i;

    }

    public int Modificar_Requisito(RequisitoBean obj){
        int i=0;

        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/RequisitoServlet?op=11&txtnum="+obj.getNumero()+"&txtalc="+obj.getAlcance()+"&txtper="+obj.getPersonal()+"&txtreu="+obj.getReunion()+"&txtdes="+obj.getDescripcion()+"&txtnumproy="+obj.getNUMPROY();
        JSONObject objeto;

        try {
            conexion_webservice conexion=new conexion_webservice();
            objeto = conexion.InvocarHttpClient(ruta);
            i= Integer.parseInt(objeto.getString("NUMERO"));


        } catch (Exception e) {
            i=0;
        }



        return i;

    }



    public int Eliminar_Requisito(String txtnum){
        int i=0;

        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/RequisitoServlet?op=12&cod="+txtnum;
        JSONObject objeto;
        try {
            conexion_webservice conexion=new conexion_webservice();
            objeto = conexion.InvocarHttpClient(ruta);
            i= Integer.parseInt(objeto.getString("NUMERO"));


        } catch (Exception e) {
            i=0;
        }


        return i;
    }

    public ArrayList<RequisitoBean> Listar_Requisito(){
        ArrayList<RequisitoBean> lista=new ArrayList<RequisitoBean>();

        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/RequisitoServlet?op=8";
        //String ruta="http://mariscal.j.facilcloud.com/mariscal/ProyectoServlet?op=8";
        try {
            conexion_webservice conexion=new conexion_webservice();
            JSONObject obj = conexion.InvocarHttpClient(ruta);
            JSONArray arreglo=obj.getJSONArray("requisito");
            for(int i=0;i<arreglo.length();i++){
                JSONObject objeto=arreglo.getJSONObject(i);
                RequisitoBean relacion=new RequisitoBean();
                relacion.setNumero(objeto.getString("NUMERO"));
                    relacion.setAlcance(objeto.getString("ALCANCE"));
                relacion.setPersonal(objeto.getString("PERSONAL"));
                relacion.setReunion(objeto.getString("REUNIONES"));
                relacion.setDescripcion(objeto.getString("DESCRIPCION"));
                relacion.setNUMPROY(objeto.getString("NUMPROY"));
                lista.add(relacion);

            }

        }catch (Exception e){
        }
        return lista;
    }



    public RequisitoBean Listar_Requisito_pdf(int i){
        RequisitoBean objproy=null;
        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/RequisitoServlet?op=15&cod="+i+"";
        //String ruta="http://mariscal.j.facilcloud.com/mariscal/ProyectoServlet?op=8";
        try {
            conexion_webservice conexion=new conexion_webservice();
            JSONObject obj = conexion.InvocarHttpClient(ruta);
            objproy=new RequisitoBean();
            objproy.setNumero(obj.getString("NUMERO"));
            objproy.setAlcance(obj.getString("ALCANCE"));
            objproy.setPersonal(obj.getString("PERSONAL"));
            objproy.setReunion(obj.getString("REUNIONES"));
            objproy.setDescripcion(obj.getString("DESCRIPCION"));
            objproy.setNUMPROY(obj.getString("NUMPROY"));


        }catch (Exception e){
        }
        return objproy;
    }












}
