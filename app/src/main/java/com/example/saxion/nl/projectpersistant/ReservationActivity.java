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
    Room room;
    Reservation reservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        final Singleton singleton = Singleton.getInstance();

        date = (EditText) findViewById(R.id.etDate);
        timeStart = (EditText) findViewById(R.id.etTimeStart);
        timeEnd = (EditText) findViewById(R.id.etTimeEnd);
        description = (EditText) findViewById(R.id.etDescription);
        persons = (EditText) findViewById(R.id.etPersons);

        // If intentExtra != null, get the data, else move on
        if( getIntent().getExtras() != null)
        {
            Intent intent = getIntent();
            int position = intent.getIntExtra(EXTRA_POSITION, -1);
            reservation = Singleton.getInstance().getReservations().get(position);

            description.setText(reservation.getDescription());
        }


        //DUMMY ROOM
        room = new Room(10,6,"G10");

        Button buttonReserve = (Button) findViewById(R.id.buttonReserve);
        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Date-time input
                String datum = date.getText().toString();
                String time1 = timeStart.getText().toString();
                String time2 = timeEnd.getText().toString();

                String beginTime = (datum + " " + time1 + ":00");
                String endTime = (datum + " " + time2 + ":00");

                // Description input
                String descr = description.getText().toString();

                // Persons input
                int personAmount = Integer.parseInt(persons.getText().toString());

                // Create reservation, and add to list
                Reservation reservation = new Reservation(room, beginTime, endTime, descr, personAmount);
                singleton.addReservation(reservation);
                System.out.println(reservation.toString());

                Intent intent = new Intent(ReservationActivity.this, ReservationOverviewActivity.class);
                startActivity(intent);


            }
        });


    }






}
