package com.andreas.mathadd;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.andreas.mathadd.databinding.ActivityHighScoreBinding;

import java.util.ArrayList;

public class HighScore extends AppCompatActivity {

    ActivityHighScoreBinding binding;
    HighScoreData highScoreData = HighScoreData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_high_score);
        binding.setHighScoreData(highScoreData);

        ListView listView = findViewById(R.id.listView);

        highScoreData.setHighScore(new HighScoreItem("00:3:45", "bla1"));
        highScoreData.setHighScore(new HighScoreItem("00:3:45", "bla2"));
        highScoreData.setHighScore(new HighScoreItem("00:3:45", "bla3"));
        highScoreData.setHighScore(new HighScoreItem("00:3:45", "bla4"));
        highScoreData.setHighScore(new HighScoreItem("00:3:45", "bla5"));
        highScoreData.setHighScore(new HighScoreItem("00:3:45", "bla6"));
        highScoreData.setHighScore(new HighScoreItem("00:3:45", "bla7"));
        highScoreData.setHighScore(new HighScoreItem("00:3:45", "bla8"));
        highScoreData.setHighScore(new HighScoreItem("00:3:45", "bla9"));
        highScoreData.setHighScore(new HighScoreItem("00:3:45", "bla10"));
        highScoreData.setHighScore(new HighScoreItem("00:3:45", "bla11"));
        highScoreData.setHighScore(new HighScoreItem("00:3:45", "bla12"));

        ArrayList<HighScoreItem> highScore = new ArrayList<>(highScoreData.getHighScore());

        HighScoreListAdapter adapter = new HighScoreListAdapter(HighScore.this, R.layout.list_view_item, highScore);
        listView.setAdapter(adapter);
    }
}
