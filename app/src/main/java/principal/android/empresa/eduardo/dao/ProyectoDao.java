package principal.android.empresa.eduardo.dao;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import principal.android.empresa.eduardo.Conexion.conexion_webservice;
import principal.android.empresa.eduardo.bean.JefeBean;
import principal.android.empresa.eduardo.bean.ProyectoBean;

/**
 * Created by Home on 08/03/2017.
 */

public class ProyectoDao {
    public String generar_cod(){
        String i="";
        //String ruta = "http://mariscal.j.facilcloud.com/mariscal1/ProyectoServlet?op=18";
        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProyectoServlet?op=18";
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

    public ArrayList<JefeBean> combo(){
        ArrayList<JefeBean> listado1=new ArrayList<JefeBean>();
        //String ruta="http://mariscal.j.facilcloud.com/mariscal1/ProyectoServlet?op=19";
        String ruta="http://mariscal24.jl.serv.net.mx/gestion/ProyectoServlet?op=19";
        try {
            conexion_webservice conexion=new conexion_webservice();
            JSONObject obj = conexion.InvocarHttpClient(ruta);
            JSONArray arreglo=obj.getJSONArray("proy");
            listado1.add(new JefeBean(0,"SELECCIONAR ADMINISTRADOR"));
            for(int i=0;i<arreglo.length();i++){
                JSONObject objeto=arreglo.getJSONObject(i);
                listado1.add(new JefeBean(objeto.getInt("CODJEFE"),objeto.getString("NOMBJEFE")));
            }
        }catch (Exception e){
        }return  listado1;
    }

    public int Grabar_Proyecto(ProyectoBean obj){
        int i=0;

       // String ruta = "http://mariscal.j.facilcloud.com/mariscal1/ProyectoServlet?op=13&txtnum="+obj.getNumero()+"&txttit="+obj.getTitulo()+"&txtdur="+obj.getDuracion()+"&txtdes="+obj.getDescripcion()+"&txttip="+obj.getTipo()+"&txtcan="+obj.getFases()+"&txtini="+obj.getInicio()+"&txtfin="+obj.getFin()+"&txtpre="+obj.getGastos()+"&txtcodjefe="+obj.getCODJEFE();
        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProyectoServlet?op=13&txtnum="+obj.getNumero()+"&txttit="+obj.getTitulo()+"&txtdur="+obj.getDuracion()+"&txtdes="+obj.getDescripcion()+"&txttip="+obj.getTipo()+"&txtcan="+obj.getFases()+"&txtini="+obj.getInicio()+"&txtfin="+obj.getFin()+"&txtpre="+obj.getGastos()+"&txtcodjefe="+obj.getCODJEFE();
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

    public int Modificar_Proyecto(ProyectoBean obj){
        int i=0;

        //String ruta = "http://mariscal.j.facilcloud.com/mariscal1/ProyectoServlet?op=14&txtnum="+obj.getNumero()+"&txttit="+obj.getTitulo()+"&txtdur="+obj.getDuracion()+"&txtdes="+obj.getDescripcion()+"&txttip="+obj.getTipo()+"&txtcan="+obj.getFases()+"&txtini="+obj.getInicio()+"&txtfin="+obj.getFin()+"&txtpre="+obj.getGastos()+"&txtcodjefe="+obj.getCODJEFE();
        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProyectoServlet?op=14&txtnum="+obj.getNumero()+"&txttit="+obj.getTitulo()+"&txtdur="+obj.getDuracion()+"&txtdes="+obj.getDescripcion()+"&txttip="+obj.getTipo()+"&txtcan="+obj.getFases()+"&txtini="+obj.getInicio()+"&txtfin="+obj.getFin()+"&txtpre="+obj.getGastos()+"&txtcodjefe="+obj.getCODJEFE();

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


    public int login_jefe(JefeBean obj){
        int i=0;

        //String ruta="http://mariscal.j.facilcloud.com/mariscal1/JefeServlet?op=16&txtnombre="+obj.getID()+"&txtcontra="+obj.getPASS();
        String ruta="http://mariscal24.jl.serv.net.mx/gestion/JefeServlet?op=16&txtnombre="+obj.getID()+"&txtcontra="+obj.getPASS();

        JSONObject objeto;
        try {
            conexion_webservice conexion=new conexion_webservice();
            objeto = conexion.InvocarHttpClient(ruta);
            i= Integer.parseInt(objeto.getString("jefe"));


        } catch (Exception e) {
            i=0;
        }



        return i;

    }
    public int Eliminar_Proyecto(String txtnum){
        int i=0;
        //String ruta = "http://mariscal.j.facilcloud.com/mariscal1/ProyectoServlet?op=15&cod="+txtnum;
        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProyectoServlet?op=15&cod="+txtnum;

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

    public ArrayList<ProyectoBean> Listar_Proyecto(){
        ArrayList<ProyectoBean> lista=new ArrayList<ProyectoBean>();

        //String ruta = "http://mariscal.j.facilcloud.com/mariscal1/ProyectoServlet?op=8";
        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProyectoServlet?op=8";

        try {
            conexion_webservice conexion=new conexion_webservice();
            JSONObject obj = conexion.InvocarHttpClient(ruta);
            JSONArray arreglo=obj.getJSONArray("proy");
            for(int i=0;i<arreglo.length();i++){
                JSONObject objeto=arreglo.getJSONObject(i);
                ProyectoBean relacion=new ProyectoBean();
                relacion.setNumero(objeto.getString("NUMERO"));
                relacion.setTitulo(objeto.getString("TITULO"));
                relacion.setDuracion(objeto.getString("DURACION"));
                relacion.setDescripcion(objeto.getString("DESCRIPCION"));
                relacion.setTipo(objeto.getString("TIPO"));
                relacion.setFases(objeto.getString("FASES"));
                relacion.setInicio(objeto.getString("INICIO"));
                relacion.setFin(objeto.getString("FIN"));
                relacion.setGastos(objeto.getString("GASTOS"));
                relacion.setCODJEFE(objeto.getString("CODJEFE"));
                lista.add(relacion);

            }

        }catch (Exception e){
        }
        return lista;
    }




    public ProyectoBean Listar_Proyecto_pdf(int i){
        ProyectoBean objproy=null;
        //String ruta = "http://mariscal.j.facilcloud.com/mariscal1/ProyectoServlet?op=20&cod="+i+"";
        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProyectoServlet?op=20&cod="+i+"";

        try {
            conexion_webservice conexion=new conexion_webservice();
            JSONObject obj = conexion.InvocarHttpClient(ruta);
            objproy=new ProyectoBean();
            objproy.setNumero(obj.getString("NUMERO"));
            objproy.setTitulo(obj.getString("TITULO"));
            objproy.setDuracion(obj.getString("DURACION"));
            objproy.setDescripcion(obj.getString("DESCRIPCION"));
            objproy.setTipo(obj.getString("TIPO"));
            objproy.setFases(obj.getString("FASES"));
            objproy.setInicio(obj.getString("INICIO"));
            objproy.setFin(obj.getString("FIN"));
            objproy.setGastos(obj.getString("GASTOS"));
            objproy.setCODJEFE(obj.getString("CODJEFE"));


        }catch (Exception e){
        }
        return objproy;
    }
    public ProyectoBean Buscar(String nomb){
        ProyectoBean objproy=null;
        //String ruta = "http://mariscal.j.facilcloud.com/mariscal1/ProyectoServlet?op=20&cod="+i+"";
        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/ProyectoServlet?op=21&proynomb="+nomb+"";

        try {
            conexion_webservice conexion=new conexion_webservice();
            JSONObject obj = conexion.InvocarHttpClient(ruta);
            objproy=new ProyectoBean();
            objproy.setNumero(obj.getString("NUMERO"));
            objproy.setTitulo(obj.getString("TITULO"));
            objproy.setDuracion(obj.getString("DURACION"));
            objproy.setDescripcion(obj.getString("DESCRIPCION"));
            objproy.setTipo(obj.getString("TIPO"));
            objproy.setFases(obj.getString("FASES"));
            objproy.setInicio(obj.getString("INICIO"));
            objproy.setFin(obj.getString("FIN"));
            objproy.setGastos(obj.getString("GASTOS"));
            objproy.setCODJEFE(obj.getString("CODJEFE"));


        }catch (Exception e){
        }
        return objproy;
    }


}
