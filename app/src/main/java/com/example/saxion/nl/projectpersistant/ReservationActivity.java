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

import com.example.saxion.nl.projectpersistant.model.Reservation;
import com.example.saxion.nl.projectpersistant.model.Room;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import java.security.Timestamp;
import java.util.ArrayList;

/**
 * Created by rubenassink on 20-09-16.
 */
public class ReservationActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    EditText date, timeStart, timeEnd, description, persons;
    Button buttonReserve, buttonDelete;
    Room room;
    Reservation reservation;

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
            final int position = intent.getIntExtra(EXTRA_POSITION, -1);
            reservation = Singleton.getInstance().getReservations().get(position);

            description.setText(reservation.getDescription());
            date.setText(reservation.getDate());
            timeStart.setText(reservation.getStartTime());
            timeEnd.setText(reservation.getEndTime());
            persons.setText(String.valueOf(reservation.getAmountOfPersons()));

        }

            //DUMMY ROOM
            room = new Room(10,6,"G10");

            buttonReserve = (Button) findViewById(R.id.buttonReserve);
            buttonReserve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Date-time input
                    String datum = date.getText().toString();
                    String beginTime = timeStart.getText().toString();
                    String endTime = timeEnd.getText().toString();

                    // Description input
                    String descr = description.getText().toString();

                    // Persons input
                    if(persons.getText() != null || persons.getText().toString() != "") {
                        personAmount = Integer.parseInt(persons.getText().toString());
                    }

                    if(!editReservation) {
                        // Create reservation, and add to list
                        Reservation reservation = new Reservation(room, beginTime, endTime, datum, descr, personAmount);
                        reservation.setDate(datum);
                        singleton.addReservation(reservation);
                    }
                    else{
                        // Set the new data
                        reservation.setStartTime(beginTime);
                        reservation.setEndTime(endTime);
                        reservation.setDescription(descr);
                        reservation.setAmountOfPersons(personAmount);
                        reservation.setDate(datum);
                    }

                    Intent intent = new Intent(ReservationActivity.this, ReservationOverviewActivity.class);
                    startActivity(intent);
                }
            });
    }
}
