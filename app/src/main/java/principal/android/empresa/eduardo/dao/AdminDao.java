package principal.android.empresa.eduardo.dao;

import org.json.JSONObject;

import principal.android.empresa.eduardo.Conexion.conexion_webservice;
import principal.android.empresa.eduardo.bean.JefeBean;

/**
 * Created by Home on 22/03/2017.
 */

public class AdminDao {

    public JefeBean cargar_admin(JefeBean obj1){
        JefeBean objbean=null;
        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/JefeServlet?op=24&txtcontra="+obj1.getPASS()+"&txtnombre="+obj1.getID();

        try {
            conexion_webservice conexion=new conexion_webservice();
            JSONObject obj = conexion.InvocarHttpClient(ruta);
            objbean=new JefeBean();
            objbean.setCODJEFE(obj.getInt("CODJEFE"));
            objbean.setNOMBJEFE(obj.getString("NOMBJEFE"));
            objbean.setEMAJEFE(obj.getString("EMAJEFE"));
            objbean.setTELFJEFE(obj.getString("TELFJEFE"));
            objbean.setAREAJEFE(obj.getString("AREAJEFE"));
            objbean.setID(obj.getString("ID"));
            objbean.setPASS(obj.getString("PASS"));


        }catch (Exception e){
        }






        return objbean;
    }



    public int Modificar_Admin(JefeBean obj){
        int i=0;

        //String ruta = "http://mariscal.j.facilcloud.com/mariscal1/ProyectoServlet?op=14&txtnum="+obj.getNumero()+"&txttit="+obj.getTitulo()+"&txtdur="+obj.getDuracion()+"&txtdes="+obj.getDescripcion()+"&txttip="+obj.getTipo()+"&txtcan="+obj.getFases()+"&txtini="+obj.getInicio()+"&txtfin="+obj.getFin()+"&txtpre="+obj.getGastos()+"&txtcodjefe="+obj.getCODJEFE();
        String ruta = "http://mariscal24.jl.serv.net.mx/gestion/JefeServlet?op=21&txtcod="+obj.getCODJEFE()+"&txtnom="+obj.getNOMBJEFE()+"&txtema="+obj.getEMAJEFE()+"&txttel="+obj.getTELFJEFE()+"&txtare="+obj.getAREAJEFE()+"&txtid="+obj.getID()+"&txtpass="+obj.getPASS();

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


}
