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
import android.view.View;
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
        Gebruiker user = singleton.getLoggedInUser();
        Room room = new Room(1,8,"room");
//       // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            agendaFragment = new AgendaFragment();

            getFragmentManager().beginTransaction().add(R.id.fragment_container, agendaFragment).commit();
            // Add the fragment to the 'fragment_container' FrameLayout


        }


    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        // activity logica
        executeServerRequest();
    }

    public void onCClick(View view) {
        DetailsFragment newFragment = new DetailsFragment();
        Bundle args = new Bundle();


        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();

    }

    private void executeServerRequest() {

        try {
            Log.d("EXE1", "exe");

            String output = new Get().execute(
                    new URL(singleton.REST_URL + "/api/reservations/1")
            ).get();
            Log.d("exe","exe");
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