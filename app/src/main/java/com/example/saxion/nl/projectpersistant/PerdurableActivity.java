package com.example.saxion.nl.projectpersistant;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.saxion.nl.projectpersistant.Classes.Gebruiker;
import com.example.saxion.nl.projectpersistant.Networking.Get;
import com.example.saxion.nl.projectpersistant.fragments.AgendaFragment;
import com.example.saxion.nl.projectpersistant.fragments.BeschikbaarFragment;
import com.example.saxion.nl.projectpersistant.fragments.DetailsFragment;
import com.example.saxion.nl.projectpersistant.model.Afspraak;
import com.example.saxion.nl.projectpersistant.model.Reservation;
import com.example.saxion.nl.projectpersistant.model.Room;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Pendurable("eeuwigdurend) is de stand van de app zoals die aan de muur wordt opgehangen.
 * Landscape is enforced.
 */

public class PerdurableActivity extends FragmentActivity implements BeschikbaarFragment.OnMenuClickListener {
    private Singleton singleton;
    @Override
    public void goToMenu() {
        Intent i = new Intent(this, ReservationActivity.class);
        startActivity(i);
    }

    public enum STATUS {AVAILABLE, BUSY, ERROR}

    private BeschikbaarFragment fragment;
    private AgendaFragment agendaFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perdurable);

        singleton = Singleton.getInstance();
        Button reserveringen = (Button) findViewById(R.id.button);
        reserveringen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PerdurableActivity.this, FancyPerdurable.class);
                startActivity(i);
            }
        });

    }
    private void executeServerRequest() {

        try {
            Log.d("EXE1", "exe");

            String output = new Get().execute(
                    new URL(singleton.REST_URL + "/api/reservations/1")
            ).get();
            Log.d("exe",output);
            JSONObject object = new JSONObject(output);
            if (object.has("http_status")) {
                //HTTP status code ophalen
                int status = object.getInt("http_status");

                //Alles met status 200 is goed
                if (status >= 200 && status <= 200) {
                    //Haal hier de server response in JSON op
                    JSONArray server_response = new JSONArray(object.getString("server_response"));
                    //Log voor debug purposes
                    System.out.println(server_response.toString());
                    for(int i = 0; i<server_response.length();i++){
                        JSONObject jsonObject = server_response.getJSONObject(i);
                        String beschrijving = jsonObject.getString("description");
                        Afspraak tmp = new Afspraak("900", beschrijving);
                        //voeg afspraak aan model toe
                        Singleton.getInstance().addAfspraak(tmp);
                    }
                    //afspraken toegevoegd dus update view
                    agendaFragment.update();
                }


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}