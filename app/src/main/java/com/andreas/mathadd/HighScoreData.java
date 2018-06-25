package com.andreas.mathadd;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;

public class HighScoreData extends BaseObservable {

    ArrayList<HighScoreItem> highScore;

    private static final HighScoreData ourInstance = new HighScoreData();
    public static HighScoreData getInstance() {
        return ourInstance;
    }

    public HighScoreData() {
        this.highScore = new ArrayList<>();
    }

    @Bindable
    public ArrayList<HighScoreItem> getHighScore() {
        return this.highScore;
    }

    public void setHighScore(HighScoreItem newHighScore) {
        this.highScore.add(0, newHighScore);

        if(this.highScore.size() > 10) {
            this.highScore.remove(10);
        }
    }
}
