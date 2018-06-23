package com.andreas.mathadd;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andreas.mathadd.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MathData mathData = MathData.getInstance();

    private Chronometer chronometer;
    private Button startButton;
    private Button restartButton;
    private View.OnLongClickListener longClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMathData(mathData);

        findViewById(R.id.startBackground).bringToFront();

        chronometer = findViewById(R.id.chronometer);
        startButton = findViewById(R.id.buttonStart);
        restartButton = findViewById(R.id.buttonRestart);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.startBackground).setVisibility(View.GONE);
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                mathData.restart();
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();

            }
        });

        //Setting longClickListeners for the carry and result buttons
        final Integer[] buttonCarryIds = {R.id.buttonCarry0, R.id.buttonCarry1, R.id.buttonCarry2, R.id.buttonCarry3};
        Integer[] buttonResultIds = {R.id.buttonResult0, R.id.buttonResult1, R.id.buttonResult2, R.id.buttonResult3, R.id.buttonResult4};

        longClickListener = new View.OnLongClickListener() {
            public boolean onLongClick(final View v) {
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(v.isPressed()) {
                            if(Arrays.asList(buttonCarryIds).contains(new Integer(v.getId()))) {
                                mathData.setCarry(Integer.parseInt(v.getTag().toString()));
                            } else {
                                mathData.setResult(Integer.parseInt(v.getTag().toString()));
                            }
                        }
                        else
                            timer.cancel();
                    }
                },100,200);
                return true;
            }
        };

        for (int buttonCarryId : buttonCarryIds) {
            findViewById(buttonCarryId).setOnLongClickListener(longClickListener);
        }

        for (Integer buttonResultId : buttonResultIds) {
            findViewById(buttonResultId).setOnLongClickListener(longClickListener);
        }


    }

    public void onCarryClick(View v) {
        int index = Integer.parseInt(v.getTag().toString());
        mathData.setCarry(index);
        this.checkAnswer();
    }



    public void onResultClick(View v) {
        int index = Integer.parseInt(v.getTag().toString());
        mathData.setResult(index);
        this.checkAnswer();
    }

    public void checkAnswer() {
        if(mathData.checkAnswer()) {
            chronometer.stop();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.correct),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
