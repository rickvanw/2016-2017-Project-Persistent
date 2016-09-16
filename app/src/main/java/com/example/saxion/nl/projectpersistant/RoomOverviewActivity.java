package com.example.saxion.nl.projectpersistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

        ArrayList<Room> rooms = new ArrayList<>();

        // DUMMY DATA
        for (int i = 0; i < 10; i++) {
            Room room = new Room(i, 9, "name");
            rooms.add(room);
        }

        roomInOverviewAdapter = new RoomInOverviewAdapter(this,rooms);
        gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(roomInOverviewAdapter);
    }
}
