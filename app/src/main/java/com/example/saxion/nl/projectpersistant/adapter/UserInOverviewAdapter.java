package com.example.saxion.nl.projectpersistant.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saxion.nl.projectpersistant.AdminActivitys.AddUserActivity;
import com.example.saxion.nl.projectpersistant.Classes.Gebruiker;
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
 * Created by rickv on 12-10-2016.
 */
public class UserInOverviewAdapter extends ArrayAdapter {

    private ArrayList<Gebruiker> userList;
    private Context context;
    private Singleton singleton = Singleton.getInstance();
    public static final String EXTRA_USER_DATABASE_ID = "database_id";
    public static final int RESULT_NOTIFY = 1111;



    public UserInOverviewAdapter(Context context, ArrayList<Gebruiker> userList) {
        super(context, 0 , userList);
        this.userList = userList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_in_overview, parent, false);
        }

        TextView tvUsername = (TextView)convertView.findViewById(R.id.tvUsername);
        TextView tvType = (TextView)convertView.findViewById(R.id.tvType);
        Button buttonRemove = (Button)convertView.findViewById(R.id.buttonRemoveUser);
        Button buttonChange = (Button)convertView.findViewById(R.id.buttonChangeUser);
        LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.LinearLayoutUserInOverview);

        String username = userList.get(position).getUsername();
        int type = userList.get(position).getType();
        String typeName;

        switch(type){
            case 0: typeName="Normale gebruiker";
                linearLayout.setBackgroundColor(Color.parseColor("#5077CF"));
                break;
            case 1: typeName="Power gebruiker";
                linearLayout.setBackgroundColor(Color.parseColor("#6A6B96"));
                break;
            case 2: typeName="Administrator";
                linearLayout.setBackgroundColor(Color.parseColor("#EB7363"));
                break;
            default: typeName="fout";
                break;
        }

        tvUsername.setText(username);
        tvType.setText(typeName);

        buttonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddUserActivity.class);
                intent.putExtra(EXTRA_USER_DATABASE_ID, userList.get(position).getDatabaseUserId());
                ((Activity) context).startActivityForResult(intent, RESULT_NOTIFY);
            }
        });

        buttonRemove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    System.out.println("database ID = " + userList.get(position).getDatabaseUserId());
                    deleteAlert("Let op!", "Weet u zeker dat u deze gebruiker wilt verwijderen? Deze actie kan niet ongedaan worden gemaakt.", userList.get(position).getDatabaseUserId());
                }catch(IndexOutOfBoundsException ioobe){
                }
            }
        });

        return convertView;
    }

    public void deleteUser(int databaseId){

        try{

            String output =
                    new Delete().execute(
                            new URL(singleton.REST_URL + "/api/users/"+databaseId)
                    ).get();

            JSONObject object = new JSONObject(output);

            if(object.has("http_status")) {
                int status = object.getInt("http_status");

                System.out.println(""+status);

                if (status >= 200 && status <= 299) {
                    //Server response ophalen

                    for(Gebruiker user: userList){
                        System.out.println("Usernames before: "+user.getUsername());
                    }

                    succesAlert("Gebruiker verwijderd");
                    for(Gebruiker user: userList){
                        if(user.getDatabaseUserId()==databaseId){
                            userList.remove(user);
                            this.notifyDataSetChanged();
                        }
                    }
                }else{
                    HashMap<String, String> error_map = new ErrorHandler(status).getErrorMessage();
                    showAlert(error_map.get("titel"), error_map.get("bericht"));}
            }

        }catch (Exception e){}

    }

    public void deleteAlert(String titel, String bericht, final int databaseId) {
        new AlertDialog.Builder(context)
                .setTitle(titel)
                .setMessage(bericht)
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser(databaseId);
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

    public void showAlert(String titel, String bericht) {
        new AlertDialog.Builder(context)
                .setTitle(titel)
                .setMessage(bericht)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /* niks doen */
                    }
                })
                .show();
    }

    public void refresh(){
        this.notifyDataSetChanged();
    }

}
