package com.example.saxion.nl.projectpersistant.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.saxion.nl.projectpersistant.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by Gebruiker on 20-9-2016.
 */
public class BeschikbaarFragment extends Fragment {
    public enum STATUS { AVAILABLE, BUSY, ERROR}
    private TextView beschibaarheid;
    private CircularProgressBar progressbar;
    private OnMenuClickListener listener;
    private Button menu;


    public BeschikbaarFragment() {
    }

    public interface OnMenuClickListener {
        void goToMenu();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.beschikbaarheid_fragment, container, false);

        beschibaarheid = (TextView) rootview.findViewById(R.id.Beschikbaar);
        menu =(Button) rootview.findViewById(R.id.goToMenu);

        progressbar = (CircularProgressBar) rootview.findViewById(R.id.progressBar);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //callback to communicatie between fragment and activity.
//                listener.goToMenu();
                int animationDuration = 5000; // 2500ms = 2,5s
                progressbar.setProgressWithAnimation(65, animationDuration); // Default duration = 1500ms

                //Intent i = new Intent(this., ReservationActivity.class);
            }
        });
        init();

        return rootview;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnMenuClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
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
            ///    beschibaarheid.setTextColor(Color.GREEN);
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
