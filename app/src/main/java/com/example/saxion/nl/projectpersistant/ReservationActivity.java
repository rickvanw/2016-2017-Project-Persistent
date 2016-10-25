package com.example.saxion.nl.projectpersistant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.saxion.nl.projectpersistant.Classes.Gebruiker;
import com.example.saxion.nl.projectpersistant.Networking.ErrorHandler;
import com.example.saxion.nl.projectpersistant.Networking.Post;
import com.example.saxion.nl.projectpersistant.model.Reservation;
import com.example.saxion.nl.projectpersistant.model.Room;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rubenassink on 20-09-16.
 */
public class ReservationActivity extends AppCompatActivity {

    public static final String EXTRA_ROOM_ID = "roomid";
    EditText date, timeStart, timeEnd, description, persons;
    Button buttonReserve;
    Room room;
    Reservation reservation;
    int room_id;

    boolean editReservation = false;
    int personAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        getSupportActionBar().hide();

        Window window = this.getWindow();

        // different status bar color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(Color.parseColor("#F9CA6B"));

        final Singleton singleton = Singleton.getInstance();


        date = (EditText) findViewById(R.id.etDate);
        timeStart = (EditText) findViewById(R.id.etTimeStart);
        timeEnd = (EditText) findViewById(R.id.etTimeEnd);
        description = (EditText) findViewById(R.id.etDescription);
        persons = (EditText) findViewById(R.id.etPersons);

        // If intentExtra != null, get the data, else move on
        if(getIntent().getExtras() != null) {

            editReservation = true;
            Intent intent = getIntent();
            room_id = intent.getIntExtra(EXTRA_ROOM_ID, -1);
            System.out.println("roomId: " + room_id );
        }

        Gebruiker user = Singleton.getInstance().getLoggedInUser();
        final int user_id = user.getNormalUserId();


            buttonReserve = (Button) findViewById(R.id.buttonReserve);
            buttonReserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Date-time input
                    String dateInput = date.getText().toString();
                    String beginTime = timeStart.getText().toString();
                        String start_date = URLEncoder.encode(dateInput + " " + beginTime + ":00");
                    String endTime = timeEnd.getText().toString();
                        String end_date = URLEncoder.encode(dateInput + " " + endTime + ":00");

                    // Description input
                    String descr = description.getText().toString();

                    // Persons input
                    if(persons.getText() != null || persons.getText().toString() != "") {
                        personAmount = Integer.parseInt(persons.getText().toString());
                    }

                        // Post reservation to server
                        try {

                            String output =
                                    new Post().execute(
                                            new URL(singleton.REST_URL + "/api/reservations/" + room_id + "?user_id=" + user_id + "&start_date=" + start_date + "&end_date=" + end_date + "&description=" + descr)
                                    ).get();

                            JSONObject object = new JSONObject(output);
                            System.out.println("reservation: " + object);

                            if (object.has("http_status")) {
                                int status = object.getInt("http_status");

                                System.out.println("" + status);

                                if (status >= 200 && status <= 299) {
                                    //Server response ophalen

//                                    succesAlert("Gebruiker aangemaakt", "Nog een gebruiker aanmaken?");
                                } else {
                                    HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
//                                    showAlert(error_map.get("titel"), error_map.get("bericht"));
                                }
                            }

                        } catch (Exception e) {
                        }

                    Intent intent = new Intent(ReservationActivity.this, ReservationOverviewActivity.class);
                    startActivity(intent);

                }

            });
    }
}
