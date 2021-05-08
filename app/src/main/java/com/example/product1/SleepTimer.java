package com.example.product1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SleepTimer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleep_timer);
    }

   /* public static class setTimer{
        Timer timer;

        public setTimer(int seconds){
            timer = new Timer();
            timer.schedule(new cancelTimer(), seconds*1000);
        }
        class cancelTimer extends TimerTask{
            public void run(){
                System.out.println("Music player stopped.");
                timer.cancel();
                System.exit(0);
            }
        }
    }*/

}
