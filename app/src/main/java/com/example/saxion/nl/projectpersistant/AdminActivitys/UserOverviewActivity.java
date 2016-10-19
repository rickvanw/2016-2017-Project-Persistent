package com.example.saxion.nl.projectpersistant.AdminActivitys;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import com.example.saxion.nl.projectpersistant.Classes.AdminGebruiker;
import com.example.saxion.nl.projectpersistant.Classes.Gebruiker;
import com.example.saxion.nl.projectpersistant.Classes.NormaleGebruiker;
import com.example.saxion.nl.projectpersistant.Classes.PowerGebruiker;
import com.example.saxion.nl.projectpersistant.Networking.ErrorHandler;
import com.example.saxion.nl.projectpersistant.Networking.Get;
import com.example.saxion.nl.projectpersistant.R;
import com.example.saxion.nl.projectpersistant.adapter.UserInOverviewAdapter;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class UserOverviewActivity extends AppCompatActivity {

    private GridView gridView;
    private UserInOverviewAdapter userInOverviewAdapter;
    private ArrayList<Gebruiker> userList;
    private Singleton singleton = Singleton.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_overview);
        getSupportActionBar().hide();

        Window window = this.getWindow();

        // different status bar color
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(Color.parseColor("#F9CA6B"));

        try {

            String output = new Get().execute(
                    new URL(singleton.REST_URL + "/api/users")
            ).get();

            JSONObject object = new JSONObject(output);
            if(object.has("http_status")) {
                //HTTP status code ophalen
                int status = object.getInt("http_status");

                userList = new ArrayList<>();

                //Alles met status 200 is goed
                if(status >= 200 && status <= 200) {
                    //Haal hier de server response in JSON op
                    JSONArray server_response = new JSONArray( object.getString("server_response") );

                    for(int i = 0; i < server_response.length(); i++){

                        int type = server_response.getJSONObject(i).getInt("type");
                        Gebruiker user;
                        switch(type){
                            case 0:
                                user = new NormaleGebruiker(server_response.getJSONObject(i).getString("username"),"nopassword", server_response.getJSONObject(i).getInt("type"),"nosession");
                                break;
                            case 1:
                                user = new PowerGebruiker(server_response.getJSONObject(i).getString("username"),"nopassword", server_response.getJSONObject(i).getInt("type"),"nosession");
                                break;
                            case 2:
                                user = new AdminGebruiker(server_response.getJSONObject(i).getString("username"),"nopassword", server_response.getJSONObject(i).getInt("type"),"nosession");
                                break;
                            default:
                                user = new NormaleGebruiker(server_response.getJSONObject(i).getString("username"),"nopassword", server_response.getJSONObject(i).getInt("type"),"nosession");
                                break;
                        }

                        user.setDatabaseUserId(server_response.getJSONObject(i).getInt("user_id"));
                        userList.add(user);
                    }
                }
                else {
                    //Vang de fouten af
                    HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
                    String titel = error_map.get("titel");
                    String bericht = error_map.get("bericht");
                }
            }


        }catch(Exception e){}

        userInOverviewAdapter = new UserInOverviewAdapter(this, userList);
        gridView = (GridView) findViewById(R.id.gridViewUserOverview);
        gridView.setAdapter(userInOverviewAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent refresh = new Intent(this, UserOverviewActivity.class);
        startActivity(refresh);
        this.finish();
    }

}
