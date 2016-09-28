package com.example.saxion.nl.projectpersistant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.saxion.nl.projectpersistant.Networking.Post;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {
    private Singleton singleton = Singleton.getInstance();
    private EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
    }

    public void doLogin(View view) {
        try {
            //Gebruikersnaam en wachtwoord ophalen
            String username = edtUsername.getText().toString();
            String password = singleton.hashPassword( edtPassword.getText().toString() );

            if(username.isEmpty() || edtPassword.getText().toString().isEmpty()) {
                showAlert("Foutmelding", "Vul zowel een gebruikersnaam en wachtwoord in.");
            }
            else {
                String output =
                        new Post().execute(
                                new URL(singleton.REST_URL + "/api/auth?username=" + username + "&password=" + password)
                        ).get();

                JSONObject object = new JSONObject(output);

                if(object.has("http_status")) {
                    int status = object.getInt("http_status");

                    if(status == 901) showAlert("Foutmelding", object.getJSONObject("server_response").getString("error_message"));
                    if(status == 400) showAlert("Server fout", "Interne server fout, neem contact op met de beheerder.\n\nCode 400");
                    if(status == 401) showAlert("Foutmelding", "Ongeldige gebruikersgegevens!");
                    if(status == 500) showAlert("Server fout", "Interne server fout, neem contact op met de beheerder.\n\nCode 500");
                    if(status >= 200 && status <= 200) {
                        Log.d("INGELOGD", "JA");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String titel, String bericht) {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle(titel)
                .setMessage(bericht)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /* niks doen */
                    }
                })
                .show();
    }
}
