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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMathData(mathData);

        findViewById(R.id.startBackground).bringToFront();

        //TODO modify chronometer, so that it also shows milliseconds
        final Chronometer chronometer = findViewById(R.id.chronometer);
        final Button startButton = findViewById(R.id.buttonStart);
        final Button restartButton = findViewById(R.id.buttonRestart);
        final Button submitButton = findViewById(R.id.buttonSubmit);

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

        submitButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if(mathData.checkAnswer()) {
                    chronometer.stop();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.correct),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.tryAgain),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }
}
