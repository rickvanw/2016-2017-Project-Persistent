package com.example.saxion.nl.projectpersistant.model;

import com.example.saxion.nl.projectpersistant.Classes.Gebruiker;

import java.sql.Timestamp;

/**
 * Created by rubenassink on 20-09-16.
 */
public class Reservation {

    private Room room;
    private Gebruiker gebruiker;
    private String startTime;
    private String endTime;
    private String description;
    private int amountOfPersons;
    private String date;

    public Reservation(Room room, String startTime, String endTime, String date, String description, int amountOfPersons) {
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.amountOfPersons = amountOfPersons;
        this.date = date;
    }
    public Reservation(String description) {
        this.description = description;
    }


    // Getters

    public Room getRoom() {
        return room;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDate() {
        return date;
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


    // Setters

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmountOfPersons(int amountOfPersons) {
        this.amountOfPersons = amountOfPersons;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "room=" + room +
                ", gebruiker=" + gebruiker +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", description='" + description + '\'' +
                ", amountOfPersons=" + amountOfPersons +
                '}';
    }
}