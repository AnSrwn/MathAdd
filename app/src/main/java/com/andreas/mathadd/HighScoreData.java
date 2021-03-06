package com.andreas.mathadd;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.util.ArrayList;

public class HighScoreData extends BaseObservable {

    ArrayList<HighScoreItem> highScore; //stores the ten best times, with the appendant name

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

    public void setHighScore(ArrayList<HighScoreItem> highScore) {
        this.highScore = new ArrayList<HighScoreItem>(highScore);
    }

    /**
     * Method checks if the given time is faster than the times in the high score.
     * If the high score is empty, the given time is added immediately. Otherwise it is
     * compared to the first (and fastest) time in the high score list.
     * @param newTime time given in this String format: "00:00:00"
     * @return returns true if given time is faster than any other time in high score, otherweise
     * it return s
     */

    public boolean timeIsFastest(String newTime) {
        int[] givenTime = new int[3]; //for the time format 00:00:00
        int[] timeInHighScore = new int[3];

        if(!this.highScore.isEmpty()) {
            //put given String newTime into an int array, to easier compare the times
            for(int i = 0; i < 3; i++) {
                givenTime[i] = Integer.parseInt(newTime.split(":")[i]);
            }

            //only the time on index 0 needed to compare, because it is the fastest in the list
            for(int i = 0; i < 3; i++) {
                timeInHighScore[i] = Integer.parseInt(((this.highScore.get(0)).getTime().split(":"))[i]);
            }

            if(givenTime[0] > timeInHighScore[0]) {
                return false;
            } else if (givenTime[0] < timeInHighScore[0]) {
                return true;
            } else {
                if(givenTime[1] > timeInHighScore[1]) {
                    return false;
                } else if (givenTime[1] < timeInHighScore[1]) {
                    return true;
                } else {
                    if (givenTime[2] < timeInHighScore[2]) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            return true;
        }
    }
}
