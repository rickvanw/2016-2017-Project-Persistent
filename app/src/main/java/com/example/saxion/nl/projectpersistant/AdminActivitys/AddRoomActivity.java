package com.example.saxion.nl.projectpersistant.AdminActivitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.saxion.nl.projectpersistant.Networking.ErrorHandler;
import com.example.saxion.nl.projectpersistant.Networking.Get;
import com.example.saxion.nl.projectpersistant.Networking.Post;
import com.example.saxion.nl.projectpersistant.Networking.Put;
import com.example.saxion.nl.projectpersistant.R;
import com.example.saxion.nl.projectpersistant.model.Room;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.HashMap;

public class AddRoomActivity extends AppCompatActivity {

    private EditText etRoomName, etRoomMaxPeople;
    private TextView tvNewRoomTitle;
    private Button buttonNewRoom;
    private Singleton singleton = Singleton.getInstance();
    public static final String EXTRA_ROOM_DATABASE_ID = "database_id";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        getSupportActionBar().hide();

        Window window = this.getWindow();

        // different status bar color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(Color.parseColor("#F9CA6B"));

        etRoomName = (EditText)findViewById(R.id.etNewRoomName);
        etRoomMaxPeople = (EditText)findViewById(R.id.etNewRoomMaxPeople);
        buttonNewRoom =  (Button)findViewById(R.id.buttonNewRoom);

        tvNewRoomTitle = (TextView)findViewById(R.id.tvNewRoomTitle);

        if(getIntent().getExtras() != null) {
            // change Room

            tvNewRoomTitle.setText("Administrator: ruimte wijzigen");

            Intent intent = getIntent();
            final int databaseId = intent.getIntExtra(EXTRA_ROOM_DATABASE_ID, -1);
            final Room room = getRoom(databaseId);

            etRoomName.setText(room.getRoomName());
            etRoomMaxPeople.setText(room.getAmountOfPeople());

            buttonNewRoom.setText("Wijzig");

            buttonNewRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Check if all fields are filled in
                    if(etRoomName == null || etRoomName.getText().toString().isEmpty() || etRoomMaxPeople == null || etRoomMaxPeople.getText().toString().isEmpty()){
                        showAlert("Foutmelding", "Vul alle velden in.");
                    }else{

                        boolean newRoomname = false;
                        boolean newRoomMaxPeople = false;

                        String changeURL = "";

                        if(!etRoomName.getText().toString().equals(room.getRoomName())){
                            newRoomname = true;
                            changeURL+="room_name=" + etRoomName.getText().toString();
                        }

                        if(Integer.parseInt(etRoomMaxPeople.getText().toString()) != room.getAmountOfPeople()){
                            newRoomMaxPeople = true;
                            if(newRoomname){
                                changeURL+="&no_of_people=" + Integer.parseInt(etRoomMaxPeople.getText().toString());
                            }else{
                                changeURL+="no_of_people=" + Integer.parseInt(etRoomMaxPeople.getText().toString());
                            }
                        }

                        System.out.println("CHANGE URL = "+changeURL);

                        if(!newRoomname && !newRoomMaxPeople) {
                            // no changes
                            showAlert("Geen wijzigingen gevonden","Om een wijziging door te voeren, moeten er nieuwe gegevens ingevuld worden");
                        }else {
                            try {

                                String output =
                                        new Put().execute(
                                                new URL(singleton.REST_URL + "/api/rooms/" + databaseId + "?" + changeURL)
                                        ).get();

                                JSONObject object = new JSONObject(output);

                                if (object.has("http_status")) {
                                    int status = object.getInt("http_status");

                                    System.out.println("" + status);

                                    // TODO status 409 moet hier weg, dit is een tijdelijke oplossing
                                    if ((status >= 200 && status <= 299)|| status == 409) {
                                        //Server response ophalen

                                        changeSuccesAlert("Ruimte gewijzigt");
                                    } else {
                                        HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
                                        showAlert(error_map.get("titel"), error_map.get("bericht"));
                                    }
                                }

                            } catch (Exception e) {
                            }
                        }

                    }
                }
            });

        }else {
            // Add room

            buttonNewRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Check if all fields are filled in
                    if (etRoomName == null || etRoomName.getText().toString().isEmpty() || etRoomMaxPeople == null || etRoomMaxPeople.getText().toString().isEmpty()) {
                        // Not all fields are filled in
                        showAlert("Foutmelding", "Vul alle velden in.");
                    } else {
                        // All fields are filled in

                        try {

                            String output =
                                    new Post().execute(
                                            new URL(singleton.REST_URL + "/api/rooms/?naam=" + etRoomName.getText().toString() + "&no_of_people=" + etRoomMaxPeople.getText().toString())
                                    ).get();

                            JSONObject object = new JSONObject(output);

                            if (object.has("http_status")) {
                                int status = object.getInt("http_status");

                                System.out.println("" + status);

                                if (status >= 200 && status <= 299) {
                                    //Server response ophalen

                                    succesAlert("Ruimte aangemaakt", "Nog een ruimte aanmaken?");
                                } else {
                                    HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
                                    showAlert(error_map.get("titel"), error_map.get("bericht"));
                                }
                            }

                        } catch (Exception e) {
                        }

                    }

                }
            });
        }

    }

    public Room getRoom (int databaseId){

        Room room = null;

        try{

            System.out.println("DATABASE_ID = " + databaseId);
            String output =
                    new Get().execute(
                            new URL(singleton.REST_URL + "/api/rooms/"+databaseId)
                    ).get();

            JSONObject object = new JSONObject(output);

            if(object.has("http_status")) {
                int status = object.getInt("http_status");

                System.out.println(""+status);

                if (status >= 200 && status <= 299) {
                    //Server response ophalen

                    JSONArray array_response = new JSONArray(object.getString("server_response"));

                    JSONObject server_response = array_response.getJSONObject(0);

                    room = new Room(server_response.getInt("room_id"),server_response.getInt("no_of_people"), server_response.getString("naam"));

                }else{
                    HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
                    showAlert(error_map.get("titel"), error_map.get("bericht"));

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return room;

    }

    public void showAlert(String titel, String bericht) {
        new AlertDialog.Builder(AddRoomActivity.this)
                .setTitle(titel)
                .setMessage(bericht)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /* niks doen */
                    }
                })
                .show();
    }

    public void succesAlert(String titel, String bericht) {
        new AlertDialog.Builder(AddRoomActivity.this)
                .setTitle(titel)
                .setMessage(bericht)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        etRoomName.setText("");
                        etRoomName.requestFocus();
                        etRoomMaxPeople.setText("");
                    }
                })
                .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    public void changeSuccesAlert(String titel) {
        new AlertDialog.Builder(AddRoomActivity.this)
                .setTitle(titel)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(1111);
                        finish();
                    }
                })

                .show();
    }
}
