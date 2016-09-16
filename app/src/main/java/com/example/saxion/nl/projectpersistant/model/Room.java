package com.example.saxion.nl.projectpersistant.model;

/**
 * Created by rickv on 16-9-2016.
 */
public class Room {
    private int roomId, amountOfPeople;
    private String roomName;
    private boolean available;

    public Room(int roomId, int amountOfPeople, String roomName, boolean available) {
        this.roomId = roomId;
        this.amountOfPeople = amountOfPeople;
        this.roomName = roomName;
        this.available = available;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getAmountOfPeople() {
        return amountOfPeople;
    }

    public String getRoomName() {
        return roomName;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setAmountOfPeople(int amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
