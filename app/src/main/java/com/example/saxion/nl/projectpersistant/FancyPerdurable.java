package com.example.saxion.nl.projectpersistant;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.saxion.nl.projectpersistant.Networking.Get;
import com.example.saxion.nl.projectpersistant.model.Afspraak;
import com.example.saxion.nl.projectpersistant.model.CalendarTimeSlot;
import com.example.saxion.nl.projectpersistant.model.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uk.co.barbuzz.clockscroller.FastScroller;

public class FancyPerdurable extends AppCompatActivity {

    private RecyclerView mContactsRecyclerView;
    private RecyclerViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fancy_perdurable);

        mContactsRecyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
        mContactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CalendarTimeSlot> calendarTimeSlotsList = new ArrayList<>();

        for (int hour = 0; hour <= 23; hour++) {
            //String format = new DecimalFormat("##.00").format(hour);
            String format = String.format("%02d:00", hour);
            CalendarTimeSlot calendarTimeSlot = new CalendarTimeSlot(format);
            calendarTimeSlotsList.add(calendarTimeSlot);
        }

        executeServerRequest();
        mAdapter = new RecyclerViewAdapter(calendarTimeSlotsList, this);
        mContactsRecyclerView.setAdapter(mAdapter);


        FastScroller fastScroller = (FastScroller) findViewById(R.id.fast_scroller_view);

        /*
        //use the following to style the scroll bar & clock handle programmatically if needed
        fastScroller.setClockEdgeColor(getResources().getColor(R.color.clock_edge));
        fastScroller.setClockFaceColor(getResources().getColor(android.R.color.transparent));
        fastScroller.setClockLineWidth(getResources().getDimension(R.dimen.clock_stroke_width));
        fastScroller.setClockScrollBarColor(getResources().getColor(R.color.colorPrimaryDark));
        fastScroller.setClockScrollBarSelectedColor(getResources().getColor(R.color.text_row));
        */

        fastScroller.setRecyclerView(mContactsRecyclerView);
    }

    private void executeServerRequest() {

        try {
            Log.d("EXE1", "exe");
Singleton singleton = Singleton.getInstance();
            String output = new Get().execute(
                    new URL(singleton.REST_URL + "/api/reservations/1")
            ).get();
            Log.d("exe",output);
            JSONObject object = new JSONObject(output);
            if (object.has("http_status")) {
                //HTTP status code ophalen
                int status = object.getInt("http_status");

                //Alles met status 200 is goed
                if (status >= 200 && status <= 200) {
                    //Haal hier de server response in JSON op
                    JSONArray server_response = new JSONArray(object.getString("server_response"));
                    //Log voor debug purposes
                    System.out.println(server_response.toString());
                    for(int i = 0; i<server_response.length();i++){
                        JSONObject jsonObject = server_response.getJSONObject(i);
                        String beschrijving = jsonObject.getString("description");
                        Afspraak tmp = new Afspraak("900", beschrijving);
                        //voeg afspraak aan model toe
                        Singleton.getInstance().addAfspraak(tmp);
                    }
                    //afspraken toegevoegd dus update view

                }


            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


}