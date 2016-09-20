package com.example.saxion.nl.projectpersistant.model;

import com.example.saxion.nl.projectpersistant.Classes.Gebruiker;

import java.sql.Timestamp;

/**
 * Created by rubenassink on 20-09-16.
 */
public class Reservation {

    private Room room;
    private Gebruiker gebruiker;
    private Timestamp startTime;
    private Timestamp endTime;
            //= java.sql.Timestamp.valueOf("2007-09-23 10:10:10.0");
    private String description;
    private int amountOfPersons;

    public Reservation(Room room, Timestamp startTime, Timestamp endTime, String description, int amountOfPersons) {
        this.gebruiker = Singleton.getInstance().getLoggedInUser();
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.amountOfPersons = amountOfPersons;
    }

    public Room getRoom() {
        return room;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    public int getAmountOfPersons() {
        return amountOfPersons;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }
}