package com.example.saxion.nl.projectpersistant;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

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

    }
}
