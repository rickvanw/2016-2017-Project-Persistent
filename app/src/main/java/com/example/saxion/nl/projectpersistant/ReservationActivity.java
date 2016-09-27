package com.example.saxion.nl.projectpersistant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.saxion.nl.projectpersistant.model.Reservation;

import java.security.Timestamp;

/**
 * Created by rubenassink on 20-09-16.
 */
public class ReservationActivity extends AppCompatActivity {

    EditText date, timeStart, timeEnd, description, persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        date = (EditText) findViewById(R.id.etDate);
        timeStart = (EditText) findViewById(R.id.etTimeStart);
        timeEnd = (EditText) findViewById(R.id.etTimeEnd);
        description = (EditText) findViewById(R.id.etDescription);
        persons = (EditText) findViewById(R.id.etPersons);

        Button buttonReserve = (Button) findViewById(R.id.buttonReserve);
        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create date formats
                String datum = date.getText().toString();
                String time1 = timeStart.getText().toString();
                String time2 = timeEnd.getText().toString();

                String beginTime = (datum + " " + time1);
                System.out.println(beginTime);
                // Create new reservation


            }
        });


    }






}
