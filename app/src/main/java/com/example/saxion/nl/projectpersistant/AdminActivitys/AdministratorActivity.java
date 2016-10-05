package com.example.saxion.nl.projectpersistant.AdminActivitys;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.saxion.nl.projectpersistant.R;
import com.example.saxion.nl.projectpersistant.ReservationActivity;
import com.example.saxion.nl.projectpersistant.ReservationOverviewActivity;
import com.example.saxion.nl.projectpersistant.RoomOverviewActivity;

public class AdministratorActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        getSupportActionBar().hide();

        Window window = this.getWindow();

        // different status bar color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(Color.parseColor("#F9CA6B"));

        Button buttonAdminReserve = (Button)findViewById(R.id.buttonAdminReserve);
        Button buttonAdminMyReservations = (Button)findViewById(R.id.buttonAdminMyReservations);
        Button buttonAdminRoomOverview = (Button)findViewById(R.id.buttonAdminRoomOverview);
        Button buttonAdminNotifications = (Button)findViewById(R.id.buttonAdminNotifications);
        Button buttonAdminUserControl = (Button)findViewById(R.id.buttonAdminUserControl);
        Button buttonAdminRoomControl = (Button)findViewById(R.id.buttonAdminRoomControl);

        buttonAdminReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this, ReservationActivity.class);
                startActivity(intent);
            }
        });

        buttonAdminMyReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this, ReservationOverviewActivity.class);
                startActivity(intent);
            }
        });

        buttonAdminRoomOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this, RoomOverviewActivity.class);
                startActivity(intent);
            }
        });

        // TODO notification button needs to be linked (Activity not yet existing)

        buttonAdminUserControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this, UserControlActivity.class);
                startActivity(intent);
            }
        });

        buttonAdminRoomControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this, RoomControlActivity.class);
                startActivity(intent);
            }
        });

    }
}
