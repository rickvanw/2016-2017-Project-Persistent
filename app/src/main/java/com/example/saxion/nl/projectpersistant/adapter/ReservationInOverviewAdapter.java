package com.example.saxion.nl.projectpersistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.saxion.nl.projectpersistant.R;
import com.example.saxion.nl.projectpersistant.model.Reservation;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import java.util.ArrayList;

/**
 * Created by rickv on 27-9-2016.
 */
public class ReservationInOverviewAdapter extends ArrayAdapter<Reservation> {


    public ReservationInOverviewAdapter(Context context, ArrayList<Reservation> reservations) {
        super(context,0, reservations);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservation_in_overview, parent, false);
        }

        TextView tvDateDay = (TextView)convertView.findViewById(R.id.tvDateDayInOverview);
        TextView tvDescription = (TextView)convertView.findViewById(R.id.tvDescriptionResInOverview);
        TextView tvStartTime = (TextView)convertView.findViewById(R.id.tvStartTimeInOverview);
        TextView tvRoomName = (TextView)convertView.findViewById(R.id.tvRoomNameInReservationOverview);


        Reservation reservation = getItem(position);

        tvDateDay.setText(reservation.getDate());
        tvDescription.setText(reservation.getDescription());
        tvStartTime.setText(reservation.getStartTime());
        tvRoomName.setText(reservation.getRoom().getRoomName());


        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
