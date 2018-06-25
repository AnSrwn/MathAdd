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
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListView listView = findViewById(R.id.listView);

        ArrayList<HighScoreItem> highScore = new ArrayList<>(highScoreData.getHighScore());

        HighScoreListAdapter adapter = new HighScoreListAdapter(HighScore.this, R.layout.list_view_item, highScore);
        listView.setAdapter(adapter);
    }
}
