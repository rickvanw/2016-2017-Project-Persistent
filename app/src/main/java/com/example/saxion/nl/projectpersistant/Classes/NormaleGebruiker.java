package com.example.saxion.nl.projectpersistant.Classes;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niels Laptop on 16-9-2016.
 */
public class NormaleGebruiker extends Gebruiker {
    public NormaleGebruiker(String username, String password, int type, String session_id) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        super(username, password, type, session_id);
    }

    public NormaleGebruiker(String username, String password, int type, String session_id, int user_id) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        super(username, password, type, session_id, user_id);
    }
}
