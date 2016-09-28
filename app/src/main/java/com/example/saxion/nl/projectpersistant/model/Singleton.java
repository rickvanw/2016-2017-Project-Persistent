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
    private final String REST_URL = "http://localhost";
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
        reservations = new ArrayList<>();
        addReservation("meeting");
        addReservation("sprint meeting");
        addReservation("vergadering");
        addReservation("daily standup");
        addReservation("wappie");
        addReservation("school");
        addReservation("school");



        gebruikers = new ArrayList<>();


        try {
            this.loggedInUser = new AdminGebruiker("Peter", "password");
        }
        catch (Exception e) {
            Log.d("SINGLETON-CONSTRUCTOR", e.getMessage());
        }
    }



    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    public void addReservation(String description) {
        Reservation reservation = new Reservation(description);
        addReservation(reservation);
    }




    public void addGebruiker(Gebruiker gebruiker) {

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
     * Verbind met de database en kijk of deze gebruiker bestaat
     * Logt ook direct de gebruiker in indien TRUE
     * @param username
     * @param password
     * @return
     */
    public boolean doAuthUser(String username, String password) {
        //Functie moet nog uit database ophalen
        try {
            if (this.loggedInUser.isAuth(username, password)) return true;
            else return false;
        }
        catch (Exception e) {
            Log.d("SINGLETON-DOAUTHUSER", e.getMessage());
            return false;
        }
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
        return new String(hash, StandardCharsets.UTF_8);
    }
}
