package com.example.saxion.nl.projectpersistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.saxion.nl.projectpersistant.Classes.Gebruiker;
import com.example.saxion.nl.projectpersistant.Networking.ErrorHandler;
import com.example.saxion.nl.projectpersistant.Networking.Get;
import com.example.saxion.nl.projectpersistant.adapter.ReservationInOverviewAdapter;
import com.example.saxion.nl.projectpersistant.model.Reservation;
import com.example.saxion.nl.projectpersistant.model.Room;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class ReservationOverviewActivity extends AppCompatActivity {

    private ListView lvReservationOverview;
    private ReservationInOverviewAdapter reservationInOverviewAdapter;
    private Singleton singleton;
    public static final String EXTRA_POSITION = "position";
    ArrayList<Reservation> reservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_overview);
        getSupportActionBar().hide();

        Window window = this.getWindow();

        // different status bar color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(Color.parseColor("#F9CA6B"));

        singleton = Singleton.getInstance();
        Gebruiker user = singleton.getLoggedInUser();
        Room room = new Room(1,8,"room");

        // Get the reservations for the specific user
        try {
            String output = new Get().execute(
                    new URL(singleton.REST_URL + "/api/reservations/user")
            ).get();

            JSONObject object = new JSONObject(output);
            if(object.has("http_status")) {
                //HTTP status code ophalen
                int status = object.getInt("http_status");

                //Alles met status 200 is goed
                if(status >= 200 && status <= 200) {
                    //Haal hier de server response in JSON op
                    JSONArray server_response = new JSONArray( object.getString("server_response") );
                    System.out.println(server_response.toString());

                    reservations = new ArrayList<>();
                    for(int i = 0; i < server_response.length(); i++){
                        reservations.add(new Reservation(room,server_response.getJSONObject(i).getString("start_date").substring(11,16),        //start time
                                                            "12:30",server_response.getJSONObject(i).getString("end_date").substring(11,16),    // end time
                                                            server_response.getJSONObject(i).getString("description").substring(0,35) + "...",  // description
                                                            0));    // amount of people
                    }
                }
                else {
                    //Vang de fouten af
                    HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
                    String titel = error_map.get("titel");
                    String bericht = error_map.get("bericht");
                    showAlert(titel,bericht);
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }


        reservationInOverviewAdapter = new ReservationInOverviewAdapter(this, reservations);
        lvReservationOverview = (ListView) findViewById(R.id.lvReservationOverview);
        lvReservationOverview.setAdapter(reservationInOverviewAdapter);
        lvReservationOverview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ReservationOverviewActivity.this, ReservationActivity.class);
                intent.putExtra(EXTRA_POSITION, position);
                startActivity(intent);
            }
        });
    }

    public void showAlert(String titel, String bericht) {
        new AlertDialog.Builder(ReservationOverviewActivity.this)
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
