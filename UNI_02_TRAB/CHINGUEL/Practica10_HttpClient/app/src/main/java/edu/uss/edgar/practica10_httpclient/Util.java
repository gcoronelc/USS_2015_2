package edu.uss.edgar.practica10_httpclient;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class Util
{
    public static final String URL_APP =
            "http://192.168.1.111/EurekaServices/service/";
    public static String codigo_empleado="";
    public static String nombre_empleado="";

    private Util()
    {
    }
    public static String execJsonGetRequest(String url) throws ClientProtocolException, IOException
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("content-type","application/json");
        HttpResponse response = httpclient.execute(httpGet);
        String jsonResult = EntityUtils.toString(response.getEntity());
        return jsonResult;
    }


}
