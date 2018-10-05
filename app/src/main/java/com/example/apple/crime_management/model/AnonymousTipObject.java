package com.example.apple.crime_management.model;

import io.realm.RealmObject;

public class AnonymousTipObject extends RealmObject
{
    private String tip;

    public String getTip()
    {
        return tip;
    }

    public void setTip(String tip)
    {
        this.tip = tip;
    }
}
