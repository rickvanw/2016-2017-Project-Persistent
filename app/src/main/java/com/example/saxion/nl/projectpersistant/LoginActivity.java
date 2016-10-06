package com.example.saxion.nl.projectpersistant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.DialogPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.saxion.nl.projectpersistant.AdminActivitys.AdministratorActivity;
import com.example.saxion.nl.projectpersistant.Classes.AdminGebruiker;
import com.example.saxion.nl.projectpersistant.Classes.Gebruiker;
import com.example.saxion.nl.projectpersistant.Classes.NormaleGebruiker;
import com.example.saxion.nl.projectpersistant.Classes.PowerGebruiker;
import com.example.saxion.nl.projectpersistant.Networking.ErrorHandler;
import com.example.saxion.nl.projectpersistant.Networking.Post;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;

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

                    if(status >= 200 && status <= 299) {
                        //Server response ophalen
                        JSONObject server_response = new JSONObject( object.getString("server_response") );

                        //Nieuw gebruiker aanmaken voor Singleton
                        String session = server_response.getString("session_status");
                        int user_type = server_response.getInt("user_type");
                        String server_username = server_response.getString("username");

                        //Dump gebruiker in Singleton
                        Gebruiker g;
                        switch (user_type) {
                            case 0: { //Normale gebruiker
                                g = new NormaleGebruiker(server_username, password, user_type, session);
                                singleton.addGebruiker(g);

                                Intent intent = new Intent(this, UserMenu.class);
                                startActivity(intent);
                                break;
                            }
                            case 1: { //Power gebruiker
                                g = new PowerGebruiker(server_username, password, user_type, session);
                                singleton.addGebruiker(g);

                                Intent intent = new Intent(this, UserMenu.class);
                                startActivity(intent);
                                break;
                            }
                            case 2: { //Administrator
                                g = new AdminGebruiker(server_username, password, user_type, session);
                                singleton.addGebruiker(g);

                                Intent intent = new Intent(this, AdministratorActivity.class);
                                startActivity(intent);
                                break;
                            }

                            //Onbekende waarde is laagste gebruiker
                            default: {
                                g = new NormaleGebruiker(server_username, password, user_type, session);
                                singleton.addGebruiker(g);

                                Intent intent = new Intent(this, UserMenu.class);
                                startActivity(intent);
                                break;
                            }
                        }

                        //Vervolgens naar andere Activity gaan (nog doen)
                        Log.d("LOGIN_ACTIVITY", "Gebruiker is ingelogd op app");
                    }
                    else {
                        HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
                        showAlert(error_map.get("titel"), error_map.get("bericht"));
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
