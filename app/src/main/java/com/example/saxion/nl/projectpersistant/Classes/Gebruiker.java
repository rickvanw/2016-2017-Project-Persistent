package com.example.saxion.nl.projectpersistant.Classes;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Niels Laptop on 16-9-2016.
 * Wachtwoord is SHA512 encrypt ivm veiligheid
 *
 * Gebruikersnaam moet ZONDER %-teken!!!!
 */
public abstract class Gebruiker {
    private String username, password, session_id;
    private int type;

    public Gebruiker(String username, String password, int type, String session_id) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.username = username;
        this.password = hashPassword(password);
        this.type = type;
        this.session_id = session_id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        this.password = hashPassword(password);
    }

    public String getSession_id() {
        return session_id;
    }

    public int getType() {
        return type;
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

    /**
     * Checkt of de gebruikersnaam en wachtwoord overeenkomen met deze gebruiker
     * @param username
     * @param password
     * @return
     */
    public boolean isAuth(String username, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if(this.username.equalsIgnoreCase(username) && this.password.equals( hashPassword(password) )) {
            return true;
        }
        else {
            return false;
        }
    }
}
