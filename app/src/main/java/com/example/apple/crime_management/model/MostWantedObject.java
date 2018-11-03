package com.example.apple.crime_management.model;

import io.realm.RealmObject;

public class MostWantedObject extends RealmObject
{
    private String name;
    private int position;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }
}
