package com.example.actat.clock;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Time;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewClock;
    private TextView textViewDate;
    private TextView textViewTemperature;
    private TextView textViewHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewClock = findViewById(R.id.clock);
        textViewDate = findViewById(R.id.date);
        textViewTemperature = findViewById(R.id.temperature);
        textViewHumidity = findViewById(R.id.humidity);

        final Handler handlerClock = new Handler();
        final Runnable rClock = new Runnable() {
            @Override
            public void run() {
                updateClock();
                updateDate();

                handlerClock.postDelayed(this, 10);
            }
        };
        handlerClock.post(rClock);

        final Handler handlerTemperature = new Handler();
        final Runnable rTemperature = new Runnable() {
            @Override
            public void run() {
                updateTemperature();
                updateHumidity();

                handlerTemperature.postDelayed(this, 10000);
            }
        };
        handlerTemperature.post(rTemperature);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        decorView.setOnSystemUiVisibilityChangeListener(
                new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        // Note that system bars will only be "visible" if none of the LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            Log.d("debug", "The system basr are visible");
                        } else {
                            Log.d("debug", "The system bars are NOT visible");
                        }
                    }
                }
        );
    }

    private void updateClock() {
        textViewClock.setText(String.valueOf(new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis()))));
    }

    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd E", Locale.US);
        textViewDate.setText(String.valueOf(sdf.format(new Date())));
    }

    private void updateTemperature() {
        textViewTemperature.setText("20.0 ℃");
    }

    private void updateHumidity() {
        textViewHumidity.setText("40 %");
    }

}
