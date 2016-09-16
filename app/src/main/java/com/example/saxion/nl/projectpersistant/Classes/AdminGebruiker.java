package com.example.saxion.nl.projectpersistant.Classes;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Niels Laptop on 16-9-2016.
 */
public class AdminGebruiker extends Gebruiker {
    public AdminGebruiker(String username, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        super(username, password);
    }
}
