package com.example.saxion.nl.projectpersistant.model;

/**
 * Created by Gebruiker on 26-10-2016.
 */
public class Afspraak {
    private String tijd;
    private String beschrijving;

    public Afspraak(String tijd, String beschrijving) {
        this.tijd = tijd;
        this.beschrijving = beschrijving;
    }

    public String getTijd() {
        return tijd;
    }

    public void setTijd(String tijd) {
        this.tijd = tijd;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
}
