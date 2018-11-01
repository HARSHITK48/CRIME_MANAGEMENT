package com.example.apple.crime_management.model;

import io.realm.Realm;
import io.realm.RealmObject;

public class ComplaintObject extends RealmObject
{
    private String id;
    private int personId;
    private String personName;
    private String complaint;
    private String aadhar;

    public String getAadhar()
    {
        return aadhar;
    }

    public void setAadhar(String aadhar)
    {
        this.aadhar = aadhar;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public int getPersonId()
    {
        return personId;
    }

    public void setPersonId(int personId)
    {
        this.personId = personId;
    }

    public String getPersonName()
    {
        return personName;
    }

    public void setPersonName(String personName)
    {
        this.personName = personName;
    }

    public String getComplaint()
    {
        return complaint;
    }

    public void setComplaint(String complaint)
    {
        this.complaint = complaint;
    }
}
