package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.concussionconnect.R;

public class SettingsActivity extends Activity implements View.OnClickListener {
    private ToggleButton infoButton;
    private Button newPasswordButton;
    private ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        infoButton = (ToggleButton) findViewById(R.id.infoButton);
        newPasswordButton = (Button) findViewById(R.id.newPasswordButton);
        backButton = (ImageButton) findViewById(R.id.backButton);
        infoButton.setOnClickListener(this);
        newPasswordButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == infoButton) {
            if (!infoButton.isChecked()) {
                //Set some kind of user boolean that will make sure they never see the test guid
            } else {
                //Set the boolean to true
            }
        }
        if (v == newPasswordButton) {
            //make a change password page and bring the user to it
        }
        if (v == backButton) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
