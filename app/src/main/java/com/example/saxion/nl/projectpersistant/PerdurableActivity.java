package com.example.saxion.nl.projectpersistant;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.saxion.nl.projectpersistant.fragments.AgendaFragment;
import com.example.saxion.nl.projectpersistant.fragments.BeschikbaarFragment;
import com.example.saxion.nl.projectpersistant.fragments.DetailsFragment;
import com.example.saxion.nl.projectpersistant.model.Reservation;

/**
 * Pendurable("eeuwigdurend) is de stand van de app zoals die aan de muur wordt opgehangen.
 * Landscape is enforced.
 */

public class PerdurableActivity extends FragmentActivity implements BeschikbaarFragment.OnMenuClickListener {
    @Override
    public void goToMenu() {
        Intent i = new Intent(this, ReservationActivity.class);
        startActivity(i);
    }

    public enum STATUS { AVAILABLE, BUSY, ERROR}
    private BeschikbaarFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perdurable);

//       // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            AgendaFragment firstFragment = new AgendaFragment();

            getFragmentManager().beginTransaction().add(R.id.fragment_container,firstFragment).commit();
            // Add the fragment to the 'fragment_container' FrameLayout


        }
    }

    public void onCClick(View view){
        DetailsFragment newFragment = new DetailsFragment();
        Bundle args = new Bundle();


        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
    }
}
