package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.concussionconnect.Model.SymptomModel;
import com.github.concussionconnect.R;

import java.util.ArrayList;

public class EndActivity extends Activity implements View.OnClickListener {
    private Button logoutButton;
    private Button newTestButton;
    private TextView displayResults;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        newTestButton = (Button) findViewById(R.id.newTestButton);
        displayResults = (TextView) findViewById(R.id.displayResults);
        logoutButton.setOnClickListener(this);
        newTestButton.setOnClickListener(this);
        displayResults.setText("");
        bundle = getIntent().getExtras();
        displayResults.append("List ID: " + bundle.getInt("listId") +"\n");
        ArrayList<SymptomModel> symptoms = (ArrayList<SymptomModel>) getIntent().getSerializableExtra("symptoms");
        int i = 0;
        displayResults.append("\n");
        for (SymptomModel x : symptoms) {
            displayResults.append(x.getSympName() + ", Severity: " + x.getValue());
            if (!(i == symptoms.size() - 1)) {
                displayResults.append(", ");
            }
            i++;
        }
        displayResults.append("\n\n");
        displayResults.append("Double Leg Errors: " + bundle.getInt("doubleLegErrors") +"\n");
        displayResults.append("Short Term Memory Score: " + bundle.getInt("shortMemScore") +"\n");
        displayResults.append("Single Leg Errors: " + bundle.getInt("singleLegErrors") +"\n");
        displayResults.append("Month Memory Score: " + bundle.getInt("monthMemScore") +"\n");
        displayResults.append("Tandem Leg Errors: " + bundle.getInt("tandemLegErrors") +"\n");
        displayResults.append("Long Term Memory Score: " + bundle.getInt("longMemScore") +"\n");
    }

    @Override
    public void onClick(View v) {
        if (v == logoutButton) {
            startActivity(new Intent(this, MainActivity.class));
        }
        if (v == newTestButton) {
            startActivity(new Intent(this, TestSetupActivity.class));
        }
    }
}
