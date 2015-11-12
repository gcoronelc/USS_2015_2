package com.example.guevaracabrera.cuentabancario;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Conexion {
    public static final String URL_APP = "http://192.168.1.33/PracticaUss/Services/";

    Conexion() {
    }

    public static String getConexion(String url) throws ClientProtocolException, IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("content-type", "application/json");
        HttpResponse response = httpclient.execute(httpGet);
        String jsonResult = EntityUtils.toString(response.getEntity());
        return jsonResult;
    }
}
