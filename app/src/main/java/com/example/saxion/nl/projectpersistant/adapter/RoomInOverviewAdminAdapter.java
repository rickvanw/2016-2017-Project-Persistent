package com.example.saxion.nl.projectpersistant.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.saxion.nl.projectpersistant.AdminActivitys.AddRoomActivity;
import com.example.saxion.nl.projectpersistant.LoginActivity;
import com.example.saxion.nl.projectpersistant.Networking.Delete;
import com.example.saxion.nl.projectpersistant.Networking.ErrorHandler;
import com.example.saxion.nl.projectpersistant.R;
import com.example.saxion.nl.projectpersistant.model.Room;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rickv on 22-10-2016.
 */
public class RoomInOverviewAdminAdapter extends ArrayAdapter<Room>{

    private ArrayList<Room> rooms;
    private Context context;
    private Singleton singleton = Singleton.getInstance();
    public static final String EXTRA_ROOM_DATABASE_ID = "database_id";
    public static final int RESULT_NOTIFY = 1111;



    public RoomInOverviewAdminAdapter(Context context, ArrayList<Room> rooms) {
        super(context, 0, rooms);
        this.rooms = rooms;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.room_in_overview_admin, parent, false);
        }

        TextView tvRoomName = (TextView)convertView.findViewById(R.id.tvRoomNameInOverviewAdmin);
        TextView tvRoomAmountOfPeople = (TextView)convertView.findViewById(R.id.tvRoomAmountOfPeopleInOverviewAdmin);

        Button buttonChange = (Button)convertView.findViewById(R.id.buttonRoomChange);
        Button buttonRemove = (Button)convertView.findViewById(R.id.buttonRoomRemove);

        tvRoomName.setText(rooms.get(position).getRoomName());
        tvRoomAmountOfPeople.setText("Aantal personen: "+rooms.get(position).getAmountOfPeople());

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddRoomActivity.class);
                intent.putExtra(EXTRA_ROOM_DATABASE_ID, rooms.get(position).getDatabaseId());
                ((Activity) context).startActivityForResult(intent, RESULT_NOTIFY);
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert("Let op!", "Weet u zeker dat u deze ruimte wilt verwijderen? Deze actie kan niet ongedaan worden gemaakt.", rooms.get(position).getDatabaseId());
            }
        });

        return convertView;
    }

    public void deleteRoom(int databaseId){

        try{

            String output =
                    new Delete().execute(
                            new URL(singleton.REST_URL + "/api/rooms/"+databaseId)
                    ).get();

            JSONObject object = new JSONObject(output);

            if(object.has("http_status")) {
                int status = object.getInt("http_status");

                System.out.println(""+status);

                if (status >= 200 && status <= 299) {
                    //Server response ophalen

                    succesAlert("Ruimte verwijderd");
                    for(Room room: rooms){
                        if(room.getDatabaseId()==databaseId){
                            rooms.remove(room);
                            this.notifyDataSetChanged();
                        }
                    }
                }else{
                    HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
                    showAlert(error_map.get("titel"), error_map.get("bericht"),status);}
            }

        }catch (Exception e){}

    }

    public void deleteAlert(String titel, String bericht, final int databaseId) {
        new AlertDialog.Builder(context)
                .setTitle(titel)
                .setMessage(bericht)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRoom(databaseId);
                    }
                })
                .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public void succesAlert(String titel) {
        new AlertDialog.Builder(context)
                .setTitle(titel)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public void showAlert(String titel, String bericht, final int status) {
        new AlertDialog.Builder(context)
                .setTitle(titel)
                .setMessage(bericht)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(status == 401){
                            Intent intent = new Intent(context, LoginActivity.class);
                            ((Activity) context).startActivityForResult(intent, RESULT_NOTIFY);
                        }
                    }
                })
                .show();
    }

}
