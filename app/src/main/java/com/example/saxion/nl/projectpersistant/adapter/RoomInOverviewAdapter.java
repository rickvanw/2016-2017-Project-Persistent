package com.example.saxion.nl.projectpersistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.saxion.nl.projectpersistant.R;
import com.example.saxion.nl.projectpersistant.model.Room;

import java.util.ArrayList;

/**
 * Created by rickv on 16-9-2016.
 */
public class RoomInOverviewAdapter extends ArrayAdapter<Room>{

    public RoomInOverviewAdapter(Context context, ArrayList<Room> rooms) {
        super(context,0, rooms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.room_in_overview, parent, false);
        }

        TextView tvRoomName = (TextView)convertView.findViewById(R.id.tvRoomNameInOverview);
        TextView tvRoomAvailability = (TextView)convertView.findViewById(R.id.tvRoomAvailabilityInOverview);
        Button button = (Button)convertView.findViewById(R.id.buttonReserveInOverview);

        Room room = getItem(position);

        tvRoomName.setText(room.getRoomName());

        if(room.isAvailable()){
            tvRoomAvailability.setText("Beschikbaar");
        }else{
            tvRoomAvailability.setText("Niet beschikbaar");

        }
        button.setText("Reserveer");

        return convertView;

    }
}
