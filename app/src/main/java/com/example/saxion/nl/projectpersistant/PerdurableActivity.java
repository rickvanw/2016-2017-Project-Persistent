package com.example.saxion.nl.projectpersistant;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.saxion.nl.projectpersistant.fragments.BeschikbaarFragment;

/**
 * Pendurable("eeuwigdurend) is de stand van de app zoals die aan de muur wordt opgehangen.
 * Landscape is enforced.
 */

public class PerdurableActivity extends FragmentActivity {
    public enum STATUS { AVAILABLE, BUSY, ERROR}
    private TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perdurable);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
           BeschikbaarFragment firstFragment = new BeschikbaarFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
          //  firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        }

        init();
    }

    /**
     * Initialiseert alles dat aan de start van de activity moet worden aangeroepen.
     */
    private void init() {
        this.textview = (TextView) findViewById(R.id.Beschikbaar);
    }

    private void checkRoomStatus(STATUS status) {
        switch (status) {
            case AVAILABLE:Y:
            Log.i("PER-ACT","room status logged as available");
                //Kamer is beschikbaar dus geef op het scherm weer dat de room beschikbaar is
                textview.setText("Beschikbaar");
                textview.setTextColor(Color.GREEN);
                break;

            case BUSY:
                Log.i("PER-ACT","room status logged as BUSY");
                textview.setText("Bezet");
                textview.setTextColor(Color.RED);
                break;
            default:
                Log.e("PER-ACT","error: default zou nooit aangeroepen mogen worden");
                break;
        }
    }
}
