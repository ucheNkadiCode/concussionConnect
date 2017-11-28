package com.github.concussionconnect.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by unkadi on 10/12/17.
 */

public class SymptomModel implements Serializable{
    private String sympName;
    private int value;

    public SymptomModel(String sympName) {
        this.sympName = sympName;
        this.value = 0;
    }

    public String getSympName() {
        return sympName;
    }

    public void setSympName(String sympName) {
        this.sympName = sympName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ArrayList<SymptomModel> getSymptomArray(String[] words) {
        ArrayList<SymptomModel> sympList = new ArrayList<>();
        for (String x : words) {
            SymptomModel word = new SymptomModel(x);
            sympList.add(word);
        }
        return sympList;
    }
}
