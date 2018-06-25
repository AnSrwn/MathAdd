package com.andreas.mathadd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    HighScoreData highScoreData = HighScoreData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();
    }

    public void onStartButtonClick(View v) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        MainActivity.this.startActivity(intent);
    }

    public void onHighScoreButtonClick(View v) {
        Intent intent = new Intent(MainActivity.this, HighScore.class);
        MainActivity.this.startActivity(intent);
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("highScoreList", null);
        Type type = new TypeToken<ArrayList<HighScoreItem>>() {}.getType();
        ArrayList<HighScoreItem> highScore = gson.fromJson(json, type);

        if(highScore != null) {
            highScoreData.setHighScore(highScore);
        }
    }
}
