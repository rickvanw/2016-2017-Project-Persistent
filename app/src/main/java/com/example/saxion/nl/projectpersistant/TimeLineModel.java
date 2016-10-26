package com.example.saxion.nl.projectpersistant;

import com.example.saxion.nl.projectpersistant.model.Afspraak;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimeLineModel implements Serializable{
    private ArrayList<Afspraak> afspraken = new ArrayList<Afspraak>();

    private String name;
    private int age;
    private int time;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addNewAfspraak(Afspraak afspraak){
        afspraken.add(afspraak);
    }

    public void setAllAfspraken(ArrayList<Afspraak> afspraken){
        this.afspraken = afspraken;
    }

    public ArrayList<Afspraak> getAfspraken() {
        return afspraken;
    }
}