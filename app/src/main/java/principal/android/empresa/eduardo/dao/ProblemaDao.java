package principal.android.empresa.eduardo.dao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import principal.android.empresa.eduardo.Conexion.conexion_webservice;
import principal.android.empresa.eduardo.bean.ProblemaBean;
import principal.android.empresa.eduardo.bean.ProyectoBean;

/**
 * Created by Home on 28/03/2017.
 */

public class ProblemaDao {

    public String generar_cod(){
        String i="";
        //String ruta = "http://mariscal2424.whelastic.net/gestor1/ProyectoServlet?op=13&cod="+TXTNUM;

        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProblemaServlet?op=14";
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
        String ruta="http://mariscal24.jl.serv.net.mx/gestion/ProblemaServlet?op=13";
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



    public int Grabar_problema(ProblemaBean obj){
        int i=0;

        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProblemaServlet?op=10&txtnum="+obj.getNumero()+"&txtnivel="+obj.getNivel()+"&txtdes="+obj.getDescripcion()+"&txtper="+obj.getPerjudicado()+"&txtnumproy="+obj.getNUMPROY();
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

    public int Modificar_Problema(ProblemaBean obj){
        int i=0;

        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProblemaServlet?op=11&txtnum="+obj.getNumero()+"&txtnivel="+obj.getNivel()+"&txtdes="+obj.getDescripcion()+"&txtper="+obj.getPerjudicado()+"&txtnumproy="+obj.getNUMPROY();
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



    public int Eliminar_Problema(String txtnum){
        int i=0;

        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProblemaServlet?op=12&cod="+txtnum;
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

    public ArrayList<ProblemaBean> Listar_Problema(){
        ArrayList<ProblemaBean> lista=new ArrayList<ProblemaBean>();

        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProblemaServlet?op=8";
        //String ruta="http://mariscal.j.facilcloud.com/mariscal/ProyectoServlet?op=8";
        try {
            conexion_webservice conexion=new conexion_webservice();
            JSONObject obj = conexion.InvocarHttpClient(ruta);
            JSONArray arreglo=obj.getJSONArray("problema");
            for(int i=0;i<arreglo.length();i++){
                JSONObject objeto=arreglo.getJSONObject(i);
                ProblemaBean relacion=new ProblemaBean();
                relacion.setNumero(objeto.getString("NUMERO"));
                relacion.setNivel(objeto.getString("NIVEL"));
                relacion.setDescripcion(objeto.getString("DESCRIPCION"));
                relacion.setPerjudicado(objeto.getString("PERJUDICADO"));
                relacion.setNUMPROY(objeto.getString("NUMPROY"));
                lista.add(relacion);

            }

        }catch (Exception e){
        }
        return lista;
    }



    public ProblemaBean Listar_Problema_pdf(int i){
        ProblemaBean objproy=null;
        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProblemaServlet?op=15&cod="+i+"";
        //String ruta="http://mariscal.j.facilcloud.com/mariscal/ProyectoServlet?op=8";
        try {
            conexion_webservice conexion=new conexion_webservice();
            JSONObject obj = conexion.InvocarHttpClient(ruta);
            objproy=new ProblemaBean();
            objproy.setNumero(obj.getString("NUMERO"));
            objproy.setNivel(obj.getString("NIVEL"));
            objproy.setDescripcion(obj.getString("DESCRIPCION"));
            objproy.setPerjudicado(obj.getString("PERJUDICADO"));
            objproy.setNUMPROY(obj.getString("NUMPROY"));


        }catch (Exception e){
        }
        return objproy;
    }

}
