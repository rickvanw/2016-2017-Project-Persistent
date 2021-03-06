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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.saxion.nl.projectpersistant.Classes.AdminGebruiker;
import com.example.saxion.nl.projectpersistant.Classes.Gebruiker;
import com.example.saxion.nl.projectpersistant.Classes.NormaleGebruiker;
import com.example.saxion.nl.projectpersistant.Classes.PowerGebruiker;
import com.example.saxion.nl.projectpersistant.LoginActivity;
import com.example.saxion.nl.projectpersistant.Networking.ErrorHandler;
import com.example.saxion.nl.projectpersistant.Networking.Get;
import com.example.saxion.nl.projectpersistant.R;
import com.example.saxion.nl.projectpersistant.ReservationActivity;
import com.example.saxion.nl.projectpersistant.adapter.ReservationInOverviewAdapter;
import com.example.saxion.nl.projectpersistant.model.Reservation;
import com.example.saxion.nl.projectpersistant.model.Room;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ReservationOverviewAdminActivity extends AppCompatActivity {

    private ListView lvReservationOverview;
    private ReservationInOverviewAdapter reservationInOverviewAdapter;
    private TextView tvTitle;
    private Singleton singleton;
    public static final String EXTRA_POSITION = "position";
    ArrayList<Reservation> reservations;
    ArrayList<Gebruiker> userList;

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

        tvTitle = (TextView) findViewById(R.id.tvReservationOverviewTitle);
        tvTitle.setText("Alle afspraken");

        singleton = Singleton.getInstance();
        Room room = new Room(1,8,"room");

        // Get the reservations for the specific user
        try {
            getUsers();

            reservations = new ArrayList<>();

            for (Gebruiker userInList: userList) {

                String output = new Get().execute(
                        new URL(singleton.REST_URL + "/api/reservations/user/"+userInList.getDatabaseUserId())
                ).get();

                JSONObject object = new JSONObject(output);
                if(object.has("http_status")) {
                    //HTTP status code ophalen
                    int status = object.getInt("http_status");

                    //Alles met status 200 is goed
                    if(status >= 200 && status <= 200) {
                        //Haal hier de server response in JSON op
                        JSONArray server_response = new JSONArray( object.getString("server_response") );
                        System.out.println(server_response.toString());

                        for(int i = 0; i < server_response.length(); i++){
                            reservations.add(new Reservation(room,server_response.getJSONObject(i).getString("start_date").substring(11,16),
                                    server_response.getJSONObject(i).getString("end_date").substring(11,16),
                                    server_response.getJSONObject(i).getString("end_date").substring(0,10),
                                    server_response.getJSONObject(i).getString("description"),
                                    0));
                        }
                    }
                    else {
                        //Vang de fouten af
                        HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
                        String titel = error_map.get("titel");
                        String bericht = error_map.get("bericht");
                        showAlert(titel,bericht, status);
                    }
                }

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        reservationInOverviewAdapter = new ReservationInOverviewAdapter(this, reservations);
        lvReservationOverview = (ListView) findViewById(R.id.lvReservationOverview);
        lvReservationOverview.setAdapter(reservationInOverviewAdapter);
        lvReservationOverview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ReservationOverviewAdminActivity.this, ReservationActivity.class);
                intent.putExtra(EXTRA_POSITION, position);
                startActivity(intent);
            }
        });
    }

    public void showAlert(String titel, String bericht, final int status) {
        new AlertDialog.Builder(ReservationOverviewAdminActivity.this)
                .setTitle(titel)
                .setMessage(bericht)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(status == 401){
                            Intent intent = new Intent(ReservationOverviewAdminActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .show();
    }

    public void getUsers() {

        try {

            String output = new Get().execute(
                    new URL(singleton.REST_URL + "/api/users")
            ).get();

            JSONObject object = new JSONObject(output);
            if (object.has("http_status")) {
                //HTTP status code ophalen
                int status = object.getInt("http_status");

                userList = new ArrayList<>();

                //Alles met status 200 is goed
                if (status >= 200 && status <= 200) {
                    //Haal hier de server response in JSON op
                    JSONArray server_response = new JSONArray(object.getString("server_response"));

                    for (int i = 0; i < server_response.length(); i++) {

                        int type = server_response.getJSONObject(i).getInt("type");
                        Gebruiker user;
                        switch (type) {
                            case 0:
                                user = new NormaleGebruiker(server_response.getJSONObject(i).getString("username"), "nopassword", server_response.getJSONObject(i).getInt("type"), "nosession");
                                break;
                            case 1:
                                user = new PowerGebruiker(server_response.getJSONObject(i).getString("username"), "nopassword", server_response.getJSONObject(i).getInt("type"), "nosession");
                                break;
                            case 2:
                                user = new AdminGebruiker(server_response.getJSONObject(i).getString("username"), "nopassword", server_response.getJSONObject(i).getInt("type"), "nosession");
                                break;
                            default:
                                user = new NormaleGebruiker(server_response.getJSONObject(i).getString("username"), "nopassword", server_response.getJSONObject(i).getInt("type"), "nosession");
                                break;
                        }

                        user.setDatabaseUserId(server_response.getJSONObject(i).getInt("user_id"));
                        userList.add(user);
                    }
                } else {
                    //Vang de fouten af
                    HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
                    String titel = error_map.get("titel");
                    String bericht = error_map.get("bericht");
                    showAlert(titel, bericht, status);
                }
            }

        } catch (Exception e) {
        }
    }
}
