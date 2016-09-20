package com.example.saxion.nl.projectpersistant.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.saxion.nl.projectpersistant.R;

/**
 * Created by Gebruiker on 20-9-2016.
 */
public class BeschikbaarFragment extends Fragment {
    public enum STATUS { AVAILABLE, BUSY, ERROR}
    private TextView beschibaarheid;
    public BeschikbaarFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.beschikbaarheid_fragment, container, false);
        beschibaarheid = (TextView) rootview.findViewById(R.id.Beschikbaar);
        init();
        return rootview;
    }

    private void init() {

        checkRoomStatus(STATUS.AVAILABLE);
    }


    private void checkRoomStatus(STATUS status) {
        switch (status) {
            case AVAILABLE:Y:
            Log.i("PER-ACT","room status logged as available");
                //Kamer is beschikbaar dus geef op het scherm weer dat de room beschikbaar is
                beschibaarheid.setText("Beschikbaar");
                beschibaarheid.setTextColor(Color.GREEN);
                break;

            case BUSY:
                Log.i("PER-ACT","room status logged as BUSY");
               beschibaarheid.setText("Bezet");
                beschibaarheid.setTextColor(Color.RED);
                break;
            default:
                Log.e("PER-ACT","error: default zou nooit aangeroepen mogen worden");
                break;
        }
    }
}
