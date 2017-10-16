package com.example.fabiorossi.myapplication.models;

/**
 * Created by fabio.rossi on 09/10/2017.
 */

public class Clock implements IClock{
    IDisplay display;

    public Clock(){

        display= new Display();
    }

    private String mOrario;
    @Override
    public void turnOn() {

    }

    @Override
    public void setAlarm(String s) {


    }

    @Override
    public void setTime(String s) {
        mOrario=s;
        display.showTime(s);

    }




    @Override
    public void turnOff() {

    }


}
