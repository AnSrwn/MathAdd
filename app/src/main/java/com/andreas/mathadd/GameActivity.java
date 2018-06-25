package com.andreas.mathadd;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andreas.mathadd.databinding.ActivityGameBinding;
import com.google.gson.Gson;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity {

    ActivityGameBinding binding;
    MathData mathData = MathData.getInstance();

    HighScoreData highScoreData = HighScoreData.getInstance();

    private Chronometer chronometer;
    private View.OnLongClickListener longClickListener;
    private Handler handler = new Handler();
    private Button restartButton;
    private TextView popupNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game);
        binding.setMathData(mathData);
    }

    @Override
    protected void onStart() {
        super.onStart();

        chronometer = findViewById(R.id.chronometer);
        restartButton = findViewById(R.id.buttonRestart);
        popupNumber = findViewById(R.id.textViewPopup);

        popupNumber.setVisibility(View.GONE);
        restartGame();

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                restartGame();
            }
        });

        //Setting longClickListeners for the carry and result buttons
        //1. make a arrays with the ids of the buttons
        final Integer[] buttonCarryIds = {R.id.buttonCarry0, R.id.buttonCarry1, R.id.buttonCarry2, R.id.buttonCarry3};
        Integer[] buttonResultIds = {R.id.buttonResult0, R.id.buttonResult1, R.id.buttonResult2, R.id.buttonResult3, R.id.buttonResult4};

        //2. create longClickListener, which can be used for carry and result buttons
        longClickListener = new View.OnLongClickListener() {
            public boolean onLongClick(final View v) {
                //tag gives the index for the array
                final int index = Integer.parseInt(v.getTag().toString());

                //handler who checks every 200 ms if the button is still pressed
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
                            GameActivity.this.checkAnswer();

                            handler.removeCallbacks(this);
                        }
                    }
                },  100);
                return true;
            }
        };

        //3. set the longClickListener to every carry and result button
        for (int buttonCarryId : buttonCarryIds) {
            findViewById(buttonCarryId).setOnLongClickListener(longClickListener);
        }

        for (Integer buttonResultId : buttonResultIds) {
            findViewById(buttonResultId).setOnLongClickListener(longClickListener);
        }
    }

    /**
     * Handels a single click on a carry button.
     * Number of this button is increased by one and it is checked, if the answer is already correct.
     * @param v the clicked carry button
     */
    public void onCarryClick(View v) {
        int index = Integer.parseInt(v.getTag().toString());
        mathData.setCarry(index);
        this.checkAnswer();
    }

    /**
     * Handels a single click on a result button.
     * Number of this button is increased by one and it is checked, if the answer is already correct.
     * @param v the clicked result button
     */
    public void onResultClick(View v) {
        int index = Integer.parseInt(v.getTag().toString());
        mathData.setResult(index);
        this.checkAnswer();
    }

    private void checkAnswer() {
        if(mathData.checkAnswer()) {

            chronometer.stop();

            if(highScoreData.timeIsFastest(chronometer.getTextTime())) {
                makeNewHighscoreDialog();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.correct),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void restartGame() {
        mathData.restart();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    private void makeNewHighscoreDialog() {
        //Source for Dialog: https://stackoverflow.com/questions/10903754/input-text-dialog-android
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.newHighScore));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(input);

        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                highScoreData.setHighScore(new HighScoreItem(chronometer.getTextTime(),input.getText().toString()));
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    /**
     * Overriding onPause to save high score data in shared preferences.
     * Not overriding onStop, because it is not guaranteed to be called, before activity is destroyed.
     */
    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(highScoreData.getHighScore());

        editor.putString("highScoreList", json);
        editor.apply();
    }
}
