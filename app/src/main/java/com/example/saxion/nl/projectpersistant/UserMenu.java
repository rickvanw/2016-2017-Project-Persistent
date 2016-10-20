package com.example.saxion.nl.projectpersistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);


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
