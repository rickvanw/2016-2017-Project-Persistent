package com.example.saxion.nl.projectpersistant.fragments;

import android.app.Fragment;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.saxion.nl.projectpersistant.R;
import com.example.saxion.nl.projectpersistant.TimeLineAdapter;
import com.example.saxion.nl.projectpersistant.TimeLineModel;
import com.example.saxion.nl.projectpersistant.model.Afspraak;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gebruiker on 20-9-2016.
 */
public class AgendaFragment extends Fragment {
    private RecyclerView mRecyclerView;

    private TimeLineAdapter mTimeLineAdapter;

    private List<TimeLineModel> mDataList = new ArrayList<>();

    private GradientDrawable.Orientation mOrientation;

    public AgendaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.agenda_fragment, container, false);

        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setHasFixedSize(true);

        initView();

        return  rootview;

    }


    private LinearLayoutManager getLinearLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        return linearLayoutManager;

    }

    private void initView() {
        int s = Singleton.getInstance().getAfspraaken().size();
        Log.d("s", String.valueOf(s));
        for(int i = 0; i < Singleton.getInstance().getAfspraaken().size(); i++) {
            TimeLineModel model = new TimeLineModel();
            model.setAllAfspraken(Singleton.getInstance().getAfspraaken());
            mDataList.add(model);
        }


            mTimeLineAdapter = new TimeLineAdapter(mDataList, mOrientation);
         if(mTimeLineAdapter !=null) {
             mRecyclerView.setAdapter(mTimeLineAdapter);
         }
        }

    public void update(){
        initView();
    }

}
