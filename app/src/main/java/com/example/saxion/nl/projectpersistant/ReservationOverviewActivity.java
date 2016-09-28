package com.example.saxion.nl.projectpersistant;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.saxion.nl.projectpersistant.adapter.ReservationInOverviewAdapter;
import com.example.saxion.nl.projectpersistant.adapter.RoomInOverviewAdapter;
import com.example.saxion.nl.projectpersistant.model.Reservation;
import com.example.saxion.nl.projectpersistant.model.Room;

import java.util.ArrayList;

public class ReservationOverviewActivity extends AppCompatActivity {

    private ListView lvReservationOverview;
    private ReservationInOverviewAdapter reservationInOverviewAdapter;

    public static final String EXTRA_POSITION = "position";

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

        reservationInOverviewAdapter = new ReservationInOverviewAdapter(this);
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
}
