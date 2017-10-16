package com.example.fabiorossi.myapplication.models;

/**
 * Created by fabio.rossi on 09/10/2017.
 */

public interface IClock {
    void turnOn();
    void setAlarm(String s);
    void setTime(String s);
    void turnOff();
}
