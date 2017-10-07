package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.github.concussionconnect.R;

import java.sql.BatchUpdateException;

public class TestSetup extends Activity  implements View.OnClickListener {
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

        ArrayAdapter<CharSequence> rosterAdapter = ArrayAdapter.createFromResource(this, R.array.roster, android.R.layout.simple_spinner_item);
        rosterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rosterSpinner.setAdapter(rosterAdapter);

        ArrayAdapter<CharSequence> wordListAdapter = ArrayAdapter.createFromResource(this, R.array.wordBankSelector, android.R.layout.simple_spinner_item);
        wordListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordListSpinner.setAdapter(wordListAdapter);

        nextButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if (v == nextButton) {
            Intent i = new Intent(this, SymptomsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("listAnswer",  String.valueOf(wordListSpinner.getSelectedItemPosition()));
            i.putExtras(bundle);
            startActivity(i);
        }
    }
}
