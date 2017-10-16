package com.example.fabiorossi.myapplication.models;

/**
 * Created by fabio.rossi on 09/10/2017.
 */

public class Display  implements IDisplay{

    String mDisplay;
    @Override
    public void showTime(String s) {
        mDisplay=s;
//        return mDisplay;
    }
}
