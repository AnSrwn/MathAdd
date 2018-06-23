package com.andreas.mathadd;

import android.databinding.DataBindingUtil;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andreas.mathadd.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MathData mathData = MathData.getInstance();

    private Chronometer chronometer;
    private Button startButton;
    private Button restartButton;

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
