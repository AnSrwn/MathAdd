package com.andreas.mathadd;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andreas.mathadd.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MathData mathData = MathData.getInstance();

    private Chronometer chronometer;
    private Button startButton;
    private Button restartButton;
    private View.OnLongClickListener longClickListener;
    private Handler handler = new Handler();
    private TextView popupNumber;

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
        popupNumber = findViewById(R.id.textViewPopup);

        popupNumber.setVisibility(View.GONE);

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
                final int index = Integer.parseInt(v.getTag().toString());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(v.isPressed()) {
                            if(Arrays.asList(buttonCarryIds).contains(new Integer(v.getId()))) {
                                mathData.setCarry(index);
                                mathData.setPopupNum(mathData.getCarry()[index]);

                            } else {
                                mathData.setResult(index);
                                mathData.setPopupNum(mathData.getResult()[index]);
                            }
                            findViewById(R.id.textViewPopup).setVisibility(View.VISIBLE);
                            handler.postDelayed(this, 200);
                        }
                        else {
                            findViewById(R.id.textViewPopup).setVisibility(View.GONE);
                            MainActivity.this.checkAnswer();

                            handler.removeCallbacks(this);
                        }
                    }
                },  100);
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
