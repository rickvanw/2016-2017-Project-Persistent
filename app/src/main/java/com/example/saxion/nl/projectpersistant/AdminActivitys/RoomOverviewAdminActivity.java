package com.example.saxion.nl.projectpersistant.AdminActivitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.example.saxion.nl.projectpersistant.Networking.ErrorHandler;
import com.example.saxion.nl.projectpersistant.Networking.Get;
import com.example.saxion.nl.projectpersistant.R;
import com.example.saxion.nl.projectpersistant.adapter.RoomInOverviewAdminAdapter;
import com.example.saxion.nl.projectpersistant.model.Room;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class RoomOverviewAdminActivity extends AppCompatActivity {

    private GridView gridView;
    private RoomInOverviewAdminAdapter roomInOverviewAdminAdapter;
    private Singleton singleton = Singleton.getInstance();
    private ArrayList<Room> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_in_overview_admin);
        getSupportActionBar().hide();

        Window window = this.getWindow();

        // different status bar color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(Color.parseColor("#F9CA6B"));

        // get the rooms
        try {
            String output = new Get().execute(
                    new URL(singleton.REST_URL + "/api/rooms")
            ).get();

            JSONObject object = new JSONObject(output);
            if(object.has("http_status")) {
                //HTTP status code ophalen
                int status = object.getInt("http_status");

                //Alles met status 200 is goed
                if(status >= 200 && status <= 200) {
                    //Haal hier de server response in JSON op
                    JSONArray server_response = new JSONArray( object.getString("server_response") );

                    roomList = new ArrayList<>();
                    for(int i = 0; i < server_response.length(); i++){
                        Room room = new Room(server_response.getJSONObject(i).getInt("room_id"), server_response.getJSONObject(i).getInt("no_of_people"),server_response.getJSONObject(i).getString("room_name"));
                        room.setDatabaseId(server_response.getJSONObject(i).getInt("room_id"));

                        roomList.add(room);
                    }
                }
                else {
                    //Vang de fouten af
                    HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
                    String titel = error_map.get("titel");
                    String bericht = error_map.get("bericht");
                    showAlert(titel, bericht);
                }

            }

        } catch (Exception e) {
        }

        roomInOverviewAdminAdapter = new RoomInOverviewAdminAdapter(this,roomList);
        gridView = (GridView)findViewById(R.id.gridViewRoomOverviewAdmin);
        gridView.setAdapter(roomInOverviewAdminAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent refresh = new Intent(this, RoomOverviewAdminActivity.class);
        startActivity(refresh);
        this.finish();
    }

    public void showAlert(String titel, String bericht) {
        new AlertDialog.Builder(RoomOverviewAdminActivity.this)
                .setTitle(titel)
                .setMessage(bericht)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /* niks doen */
                    }
                })
                .show();
    }
}
