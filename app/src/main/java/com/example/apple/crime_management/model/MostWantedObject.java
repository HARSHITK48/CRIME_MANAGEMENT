package com.example.apple.crime_management.model;

import io.realm.RealmObject;

public class MostWantedObject extends RealmObject
{
    private String name;
    private int dangerLevel;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getDangerLevel()
    {
        return dangerLevel;
    }

    public void setDangerLevel(int dangerLevel)
    {
        this.dangerLevel = dangerLevel;
    }
}
