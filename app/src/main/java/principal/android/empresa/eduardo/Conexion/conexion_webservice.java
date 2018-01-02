package principal.android.empresa.eduardo.Conexion;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Home on 08/03/2017.
 */

public class conexion_webservice {
    public JSONObject InvocarHttpClient(String url) throws IOException, JSONException {
        JSONObject jobj = null;
        InputStream is = null;
        String aux = "", linea;

        try {
            HttpClient hclient = new DefaultHttpClient();
            HttpGet hget = new HttpGet(url);
            HttpResponse response = (HttpResponse) hclient.execute(hget);
            HttpEntity entidad = response.getEntity();
            BufferedHttpEntity buffer = new BufferedHttpEntity(entidad);
            is = buffer.getContent();


            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while ((linea = reader.readLine()) != null)
            {
                aux += linea;
            }

            jobj=new JSONObject(aux);//parsear
            is.close();

        }catch(Exception e){

        }
        return jobj;
    }

}
