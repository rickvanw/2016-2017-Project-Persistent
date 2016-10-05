package com.example.saxion.nl.projectpersistant.AdminActivitys;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.saxion.nl.projectpersistant.R;

public class RoomControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_control);
        getSupportActionBar().hide();

        Window window = this.getWindow();

        // different status bar color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(Color.parseColor("#F9CA6B"));

        Button buttonAdminReservations = (Button)findViewById(R.id.buttonAdminReservations);
        Button buttonAdminAddRoom = (Button)findViewById(R.id.buttonAdminAddRoom);
        Button buttonAdminRemoveRoom = (Button)findViewById(R.id.buttonAdminRemoveRoom);
        Button buttonAdminChangeRoom = (Button)findViewById(R.id.buttonAdminChangeRoom);
        Button buttonAdminRoomMonitoring = (Button)findViewById(R.id.buttonAdminRoomMonitoring);

    }
}
