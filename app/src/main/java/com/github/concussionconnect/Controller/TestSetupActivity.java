package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.github.concussionconnect.R;


import java.util.ArrayList;

public class TestSetupActivity extends Activity implements View.OnClickListener {
    private Spinner rosterSpinner;
    private Spinner wordListSpinner;
    private Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_setup);
        rosterSpinner = (Spinner) findViewById(R.id.rosterSpinner);
        wordListSpinner = (Spinner) findViewById(R.id.wordListSpinner);
        nextButton = (Button) findViewById(R.id.nextButton);
        ArrayAdapter<CharSequence> wordListAdapter = ArrayAdapter.createFromResource(this, R.array.wordBankSelector, android.R.layout.simple_spinner_item);
        wordListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordListSpinner.setAdapter(wordListAdapter);
        nextButton.setOnClickListener(this);
        Bundle playerListBundle = getIntent().getExtras();
        ArrayList<String> playerList = playerListBundle.getStringArrayList("roster");
        ArrayAdapter<String> rosterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playerList);
        rosterSpinner.setAdapter(rosterAdapter);


    }
    @Override
    public void onClick(View v) {
        if (v == nextButton) {
            Intent i = new Intent(this, SymptomsActivity.class);
            Bundle bundle = new Bundle();
            String listAnswer = "memory_word_list_" + (wordListSpinner.getSelectedItemPosition() + 1);
            int id = getResources().getIdentifier(listAnswer, "array", getPackageName());
            bundle.putInt("listId", id);
            String playerInfo = rosterSpinner.getSelectedItem().toString();
            bundle.putString("playerInfo", playerInfo);
            i.putExtras(bundle);
            startActivity(i);
        }
    }


}
