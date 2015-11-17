package pe.uss.monsalve.trabajo_unidad_2;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by Diego on 10/11/2015.
 */
public class Util {

    public static final String URL_APP = "http://192.168.1.36:80/EurekaServices/service/";

    public Util(){

    }

    public static String execJsonGetRequest(String url) throws ClientProtocolException, IOException {
        HttpClient cliente = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        get.setHeader("content-type", "application/json");
        HttpResponse respuesta = cliente.execute(get);
        String resultadoJson = EntityUtils.toString(respuesta.getEntity());
        return resultadoJson;
    }

}
