package com.github.concussionconnect.Model;

import java.util.ArrayList;

/**
 * Created by unkadi on 9/29/17.
 */

public class ChecklistModel {
    private String word;
    private boolean checked;

    public ChecklistModel(String word) {
        this.word = word;
        this.checked = false;
    }

    public String getWord() {
        return word;
    }

    public boolean isChecked() {
        return checked;

    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public static ArrayList<ChecklistModel> getChecklistArray(String[] words) {
        ArrayList<ChecklistModel> wordList = new ArrayList<>();
        for (String x : words) {
            ChecklistModel word = new ChecklistModel(x);
            wordList.add(word);
        }
        return wordList;
    }
}

