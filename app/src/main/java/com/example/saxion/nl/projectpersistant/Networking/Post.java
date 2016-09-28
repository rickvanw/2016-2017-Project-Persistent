package com.example.saxion.nl.projectpersistant.Networking;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Niels Laptop on 28-9-2016.
 *
 * Class voor het sturen van POST requests
 * Geeft het volgende JSON object terug:
 *      { http_status = 123, server_response = [ { SERVER RESPONSE JSON OBJECT } ] }
 *
 * HTTP FOUTCODES
 * Kan de volgende foutcodes teruggeven voor INTERN gebruik
 * 901  =   Server timeout, geen verbinding met REST API kunnen maken
 *
 * Indien je NULL terugkrijgt heb je een probleem
 */

public class Post extends AsyncTask<URL, Void, String> {
    @Override
    protected String doInBackground(URL... urls) {
        return getURL(urls[0]);
    }

    public String getURL(URL url) {
        StringBuilder sb = new StringBuilder();
        int response = -1;
        JSONObject post_response = null;

        try {
            //Verbinding maken
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1500);
            connection.setReadTimeout(1500);
            connection.setRequestMethod("POST");
            connection.setDoOutput(false);
            connection.connect();

            //Response ophalen
            response = connection.getResponseCode();

            BufferedReader in;
            if(response >= 200 && response <= 299) in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            else in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

            //Body ophalen
            String line = null;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();

//            Log.d("RESPONSE", sb.toString());
//            Log.d("CODE", connection.getResponseCode() + "");

            post_response = new JSONObject()
                             .put("http_status", response)
                             .put("server_response", sb.toString());
        }
        catch (SocketTimeoutException | ConnectException e) {
            //Vang hier de timeout af, en geef een JSON object terug in hetzelfde formaat
            try {
                return new JSONObject()
                        .put("http_status", 901)
                        .put("server_response",
                                new JSONObject().put("error_message", "Kan niet verbinden met de server (timeout)!"))
                        .toString();
            }
            catch (JSONException je) {
                je.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return post_response == null ? null : post_response.toString();
    }
}
