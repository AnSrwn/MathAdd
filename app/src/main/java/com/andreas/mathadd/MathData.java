package com.andreas.mathadd;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.graphics.Color;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

public class MathData extends BaseObservable{

    private int[] firstNum = new int[4]; //3, 2, 1, 0
    private int[] secondNum = new int[4]; //3, 2, , 1, 0

    private int[] carry = new int[4]; //input of the user
    private int[] result = new int [5]; // input of the user

    private int[] carrySolution = new int[4]; //calculated correct remaining
    private int[] resultSolution = new int[5]; //calculated correct solution

    private int[] carryColor = new int[4]; //color of the carry buttons
    private int popupNum;

    private static final MathData ourInstance = new MathData();
    public static MathData getInstance() {
        return ourInstance;
    }

    private MathData() {
        this.setNum(firstNum);
        this.setNum(secondNum);
        this.setSolution();
        this.checkColor();
        this.setPopupNum(0);
    }

    @Bindable
    public int getPopupNum() {
        return this.popupNum;
    }

    public void setPopupNum(int num) {
        this.popupNum = num;
    }

    @Bindable
    public int[] getFirstNum() {
        return this.firstNum;
    }

    public void setFirstNum(int[] num) {
        this.firstNum = num;
    }

    @Bindable
    public int[] getSecondNum() {
        return this.secondNum;
    }

    public void setSecondNum(int[] num) {
        this.secondNum = num;
    }

    @Bindable
    public int[] getCarrySolution() {
        return this.carrySolution;
    }

    //should not be used
    //necesarry for Data Binding
    //TODO: delete this method
    private void setCarrySolution(int[] carry) {
        this.carrySolution = carry;
    }

    @Bindable
    public int[] getResultSolution() {
        return this.resultSolution;
    }

    //should not be used
    //necesarry for Data Binding
    //TODO: delete this method
    private void setResultSolution(int[] result) {
        this.resultSolution = result;
    }

    @Bindable
    public int[] getCarry() {
        return this.carry;
    }

    public void setCarry(int index) {
        if(++this.carry[index] > 9) {
            this.carry[index] = 0;
        }
        this.checkColor();
        notifyPropertyChanged(BR._all);
    }

    @Bindable
    public int[] getResult() {
        return this.result;
    }

    public void setResult(int index) {
        if(++this.result[index] > 9) {
            this.result[index] = 0;
        }

        notifyPropertyChanged(BR._all);
    }

    @Bindable
    public int[] getCarryColor() {
        return this.carryColor;
    }

    public void setCarryColor(int color, int index) {
        this.carryColor[index] = color;
        notifyPropertyChanged(BR._all);
    }


    /**
     * Method to generate a 4 digit random number.
     * @param num an integer array of min. 4 elements must be given
     */
    private void setNum(int[] num) {
        for(int i = 0; i < 4; i++) {
            num[i]= new Random().nextInt(10);
        }
    }

    /**
     * Method to compare the carry input of the user with the correct values of carry.
     * If the input is correct, the button color is set to green.
     */
    private void checkColor() {
        for(int index = 0; index < 4; index++) {
            if (this.carry[index] == this.carrySolution[index]) {
                this.carryColor[index] = Color.rgb(0, 255, 0);
            } else {
                this.carryColor[index] = Color.rgb(190, 190, 190);
            }
        }
    }

    /**
     * Checks if the result input of the user is correct.
     * @return true, if result input is correct, otherwise false
     */
    public boolean checkAnswer() {
        return (Arrays.equals(this.result, this.resultSolution) && Arrays.equals(this.carry, this.carrySolution));
    }

    /**
     * Method to calculate the result number and the carry of each single addition.
     * Should be called after changing firstNum or/and secondNum.
     */
    private void setSolution() {
        for (int i = 0; i < 4; i++) {
            int sum = this.firstNum[i] + this.secondNum[i];

            //except for the first time, the carry of the previous addition must be added
            if(i > 0){
                sum += this.carrySolution[i-1];
            }

            this.resultSolution[i] = sum % 10;

            //calculates the carry, which will be added to the next addition
            this.carrySolution[i] = (sum - this.resultSolution[i]) / 10;
        }

        //the first digit of the result number is the carry of the previous addition
        this.resultSolution[4] = this.carrySolution[3];
    }

    /**
     * Resets the variables.
     * firstNum and secondNum are generated new, so that there are two new random numbers.
     * The new solution is calculated, and carry and result are set to 0.
     */
    public void restart() {
        this.setNum(firstNum);
        this.setNum(secondNum);
        this.setSolution();

        for(int i = 0; i < 4; i++) {
            this.carry[i] = 0;
            this.result[i] = 0;
        }
        this.result[4] = 0;
        this.checkColor();
        notifyPropertyChanged(BR._all);
    }
}
