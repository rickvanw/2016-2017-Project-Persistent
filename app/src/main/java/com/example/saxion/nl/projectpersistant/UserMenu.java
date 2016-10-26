package com.example.saxion.nl.projectpersistant;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class UserMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);
        getSupportActionBar().hide();

        Window window = this.getWindow();

        // different status bar color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(Color.parseColor("#F9CA6B"));


    }

    public void plaatsReservering(View view){
        Intent i = new Intent(UserMenu.this, ReservationActivity.class);
        startActivity(i);
    }


    public void zoekRuimte(View view){
        Intent i = new Intent(UserMenu.this, RoomOverviewActivity.class);
        startActivity(i);
    }
    //TOODO activity bestaat nog niet
    public void gaNaarMeldingen(View View){
        Intent i = new Intent(UserMenu.this, PerdurableActivity.class);
        startActivity(i);
    }

    public void gaNaarOverzicht(View View){
        Intent i = new Intent(UserMenu.this, ReservationOverviewActivity.class);
        startActivity(i);
    }

}
