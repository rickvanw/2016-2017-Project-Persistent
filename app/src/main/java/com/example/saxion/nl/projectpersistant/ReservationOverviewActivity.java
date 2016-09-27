package com.example.saxion.nl.projectpersistant;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.saxion.nl.projectpersistant.adapter.ReservationInOverviewAdapter;
import com.example.saxion.nl.projectpersistant.adapter.RoomInOverviewAdapter;
import com.example.saxion.nl.projectpersistant.model.Reservation;
import com.example.saxion.nl.projectpersistant.model.Room;

import java.util.ArrayList;

public class ReservationOverviewActivity extends AppCompatActivity {

    private ListView lvReservationOverview;
    private ReservationInOverviewAdapter reservationInOverviewAdapter;

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

        ArrayList<Reservation> reservations = new ArrayList<>();

        // DUMMY DATA
        for (int i = 0; i < 10; i++) {
            Room room = new Room(i, 9, "Room: "+(i+1));

            Reservation reservation = new Reservation(room,"11:15","11:45", "Sprint Planning", 7);
            reservations.add(reservation);
        }

        reservationInOverviewAdapter = new ReservationInOverviewAdapter(this, reservations);
        lvReservationOverview = (ListView) findViewById(R.id.lvReservationOverview);
        lvReservationOverview.setAdapter(reservationInOverviewAdapter);
    }
}
