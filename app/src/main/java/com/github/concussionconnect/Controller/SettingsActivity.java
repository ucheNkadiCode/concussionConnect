package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.github.concussionconnect.R;

public class SettingsActivity extends Activity implements View.OnClickListener {
    private ToggleButton infoButton;
    private Button buttonChangePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        infoButton = (ToggleButton) findViewById(R.id.infoButton);
        buttonChangePassword = (Button) findViewById(R.id.buttonChangePassword);
        infoButton.setOnClickListener(this);
        buttonChangePassword.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == infoButton) {
            if (!infoButton.isChecked()) {
                //Set some kind of user boolean that will make sure they never see the tutorial
            } else {
                //Set the boolean to true
            }
        }
        if (v == buttonChangePassword) {
            startActivity(new Intent(this, ChangePasswordActivity.class));
        }
    }
}
