package com.example.saxion.nl.projectpersistant.Networking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Niels Laptop on 6-10-2016.
 */

public class ErrorHandler {
    private int http_code;

    public ErrorHandler(int http_code) {
        this.http_code = http_code;
    }

    public HashMap<String, String> getErrorMessage() {
        HashMap<String, String> returnMap = new HashMap<>();

        if (http_code == 400) {
            returnMap.put("titel", "Server fout");
            returnMap.put("bericht", "Interne server fout, neem contact op met de beheerder.\n\nCode 400");
        }
        if (http_code == 401) {
            returnMap.put("titel", "Foutmelding");
            returnMap.put("bericht", "Ongeldige gebruikersgegevens");
        }
        if (http_code == 500) {
            returnMap.put("titel", "Server fout");
            returnMap.put("bericht", "Interne server fout, neem contact op met de beheerder.\n" +
                    "\n" +
                    "Code 500");
        }
        //Exceptions
        if (http_code == 901) {
            returnMap.put("titel", "Verbindingsfout");
            returnMap.put("bericht", "Kan niet verbinden met de server (timeout)!");
        }

        return returnMap;
    }
}
