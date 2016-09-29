package com.example.saxion.nl.projectpersistant.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saxion.nl.projectpersistant.R;

/**
 * Created by Gebruiker on 20-9-2016.
 */
public class DetailsFragment extends Fragment {
    public DetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.details_fragment, container, false);
        return  rootview;
    }
}
