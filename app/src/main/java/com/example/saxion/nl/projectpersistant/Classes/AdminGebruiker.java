package com.example.saxion.nl.projectpersistant.Classes;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Niels Laptop on 16-9-2016.
 */
public class AdminGebruiker extends Gebruiker {
    public AdminGebruiker(String username, String password, int type, String session_id) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        super(username, password, type, session_id);
    }

    public AdminGebruiker(String username, String password, int type, String session_id, int user_id) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        super(username, password, type, session_id, user_id);
    }
}
