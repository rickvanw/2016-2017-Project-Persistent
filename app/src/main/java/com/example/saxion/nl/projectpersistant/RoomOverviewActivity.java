package com.example.saxion.nl.projectpersistant;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.example.saxion.nl.projectpersistant.adapter.RoomInOverviewAdapter;
import com.example.saxion.nl.projectpersistant.model.Room;

import java.util.ArrayList;

public class RoomOverviewActivity extends AppCompatActivity {

    private GridView gridView;
    private RoomInOverviewAdapter roomInOverviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_overview);
        getSupportActionBar().hide();

        Window window = this.getWindow();

        // different status bar color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(Color.parseColor("#B1A893"));

        ArrayList<Room> rooms = new ArrayList<>();

        // DUMMY DATA
        for (int i = 0; i < 10; i++) {
            Room room = new Room(i, 9, "Room: "+(i+1));
            if(room.getRoomId()==2 || room.getRoomId()==6){
                room.setAvailable(false);
            }
            rooms.add(room);
        }

        roomInOverviewAdapter = new RoomInOverviewAdapter(this,rooms);
        gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(roomInOverviewAdapter);
    }
}
