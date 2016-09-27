package com.example.saxion.nl.projectpersistant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.saxion.nl.projectpersistant.model.Reservation;
import com.example.saxion.nl.projectpersistant.model.Room;

import java.security.Timestamp;

/**
 * Created by rubenassink on 20-09-16.
 */
public class ReservationActivity extends AppCompatActivity {

    EditText date, timeStart, timeEnd, description, persons;
    Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        date = (EditText) findViewById(R.id.etDate);
        timeStart = (EditText) findViewById(R.id.etTimeStart);
        timeEnd = (EditText) findViewById(R.id.etTimeEnd);
        description = (EditText) findViewById(R.id.etDescription);
        persons = (EditText) findViewById(R.id.etPersons);


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

                String beginTime = (datum + " " + time1);
                String endTime = (datum + " " + time2);

                // Description input
                String descr = description.getText().toString();

                // Persons input
                int personAmount = Integer.parseInt(persons.getText().toString());

                // Create reservation
                Reservation reservation = new Reservation(room, beginTime, endTime, descr, personAmount);
                System.out.println(reservation.toString());


            }
        });


    }






}
