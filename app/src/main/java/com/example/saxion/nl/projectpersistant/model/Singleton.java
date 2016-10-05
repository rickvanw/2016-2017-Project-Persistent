package com.example.saxion.nl.projectpersistant.model;

import android.util.Log;

import com.example.saxion.nl.projectpersistant.Classes.AdminGebruiker;
import com.example.saxion.nl.projectpersistant.Classes.Gebruiker;
import com.example.saxion.nl.projectpersistant.Classes.NormaleGebruiker;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by Niels Laptop on 16-9-2016.
 */
public class Singleton {
    private Gebruiker loggedInUser;
    public final String REST_URL = "http://145.76.249.25:8080";
    ArrayList<Reservation> reservations;
    ArrayList<Gebruiker> gebruikers;

    private static Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        if (ourInstance == null) {
            ourInstance = new Singleton();
        }
        return ourInstance;
    }

    private Singleton() {
        // Dummy data
        Room room = new Room(10, 4, "ruimte.1");
        reservations = new ArrayList<>();
        gebruikers = new ArrayList<>();

        Reservation reservation = new Reservation(room,"12:00", "12:15", "21-07-1992", "vergadering", 8);
        addReservation(reservation);
        Reservation reservation1 = new Reservation(room,"12:15", "12:15", "21-07-1992", "meeting", 8);
        addReservation(reservation1);
        Reservation reservation2 = new Reservation(room,"12:45", "12:15", "21-07-1992", "interview", 8);
        addReservation(reservation2);
        Reservation reservation3 = new Reservation(room,"13:00", "12:15", "21-07-1992", "lunch", 8);
        addReservation(reservation3);
        Reservation reservation4 = new Reservation(room,"15:00", "12:15", "21-07-1992", "vergadering", 8);
        addReservation(reservation4);
    }



    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    public void addReservation(String description) {
        Reservation reservation = new Reservation(description);
        addReservation(reservation);
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void removeReservation(int position) {
        reservations.remove(position);
    }

    public void addGebruiker(Gebruiker gebruiker) {
        this.loggedInUser = gebruiker;
    }

    /**
     * Geeft een kopie terug van de ingelogde gebruiker
     * Indien NULL, betekent dat er niemand is ingelogd
     * @return
     */
    public Gebruiker getLoggedInUser() {
        Gebruiker g = this.loggedInUser;
        return loggedInUser;
    }

    /**
     * Geeft een SHA512 hash terug op basis van de ingevoerde String
     * @param password
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @return String gehashte wachtwoord
     */
    public String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hash = md.digest( password.getBytes() );

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString().toUpperCase();
    }
}
