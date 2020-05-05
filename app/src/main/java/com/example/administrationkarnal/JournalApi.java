package com.example.administrationkarnal;

import android.app.Application;

public class JournalApi extends Application
{

    String department;
    String Name;
    String email;
    private static JournalApi instance;
    public static JournalApi getInstance()
    {
        if (instance==null)
        {
            instance=new JournalApi();
        }
        return instance;
    }
    public JournalApi()
    {

    }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
