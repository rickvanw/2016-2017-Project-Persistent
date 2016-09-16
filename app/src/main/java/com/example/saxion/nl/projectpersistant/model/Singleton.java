package com.example.saxion.nl.projectpersistant.model;

import android.util.Log;

import com.example.saxion.nl.projectpersistant.Classes.AdminGebruiker;
import com.example.saxion.nl.projectpersistant.Classes.Gebruiker;
import com.example.saxion.nl.projectpersistant.Classes.NormaleGebruiker;

/**
 * Created by Niels Laptop on 16-9-2016.
 */
public class Singleton {
    private Gebruiker loggedInUser;

    private static Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
        //DEBUG
        try {
            this.loggedInUser = new AdminGebruiker("Peter", "password");
        }
        catch (Exception e) {
            Log.d("SINGLETON-CONSTRUCTOR", e.getMessage());
        }
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
}
