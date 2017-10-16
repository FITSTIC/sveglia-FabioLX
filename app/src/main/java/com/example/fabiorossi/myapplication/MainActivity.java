package com.example.fabiorossi.myapplication;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.net.sip.SipAudioCall;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fabiorossi.myapplication.models.Clock;
import com.example.fabiorossi.myapplication.models.IBell;
import com.example.fabiorossi.myapplication.models.IClock;
import com.example.fabiorossi.myapplication.models.IDisplay;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextClock mTimeClock;

    private TextView mOrario;
    private TextView mAlarm;
    private Clock mClock;
    private IDisplay mDisplay;
    private IBell mBell;
    private Button mSveja;
    private FrameLayout mFrame;
    private Button mAlarmOnOff;
    private Date mSimpleDate;
    private Date mAlarmSet;
    private Boolean mAlarmActive=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mTimeClock= (TextClock) findViewById(R.id.clock);

        mOrario=(TextView) findViewById(R.id.orario);
        mAlarm=(TextView) findViewById(R.id.edit);
        mAlarmOnOff=(Button) findViewById(R.id.button_on_off);
        mAlarmOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mAlarmActive==false){
                mAlarmOnOff.setBackgroundColor(Color.RED);
                mAlarmActive=true;}
                else{
                    mAlarmOnOff.setBackgroundColor(Color.GRAY);
                    mAlarmActive=false;}


            }
        });
//listener allarme
        mBell= new IBell() {
            @Override
            public void start() {

                if(mAlarmActive==true)
                Toast.makeText(MainActivity.this,"ALLARME", Toast.LENGTH_LONG).show();
            }

            @Override
            public void end() {

            }
        };


        TimePicker tp = (TimePicker) findViewById(R.id.timeSpinnerAlarm);

        final IClock iclock= new Clock();
        final SimpleDateFormat sdf= new SimpleDateFormat("HH:mm:ss");

        //INTERCETTA il cambio di stato:
        // creo listener che stampa orologio
        mDisplay = new IDisplay() {

            @Override
            public void showTime(String s) {
                mOrario.setText(s);

                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date date = format.parse(s);
                    mSimpleDate=date;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(mSimpleDate.equals(mAlarmSet)){
                    mBell.start();
                }

            }
        };

        //crea calendario
        Calendar c= Calendar.getInstance();
        String hourOfDay = String.valueOf(c.get(c.HOUR_OF_DAY)); //Current Hour
        String minute = String.valueOf(c.get(c.MINUTE)); //Current Minute
        final String second = String.valueOf(c.get(c.SECOND)); //Current Second
        //parso da string a date!!!


        //tempo alarm iniziale settato
        mAlarm.setText("Initial Time\nH:M:S | " + hourOfDay + ":" + minute + ":" + second);

        //cambiamento tempo
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //da fare ad evento cambiato
                mAlarm.setText("Orario Allarme:\n"+hourOfDay + ":" + minute);


                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                try {
                    Date date = format.parse(hourOfDay+":"+minute+":"+second);
                    mAlarmSet=date;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Date date= new Date();
                                String ora= sdf.format(date);
                                iclock.setTime(ora);
                                mDisplay.showTime(ora);


                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();










    }


}
