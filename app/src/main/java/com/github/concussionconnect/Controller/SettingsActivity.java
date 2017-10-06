package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.concussionconnect.R;

public class SettingsActivity extends Activity implements View.OnClickListener {
    private ToggleButton infoButton;
    private Button newPasswordButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        infoButton = (ToggleButton) findViewById(R.id.infoButton);
        newPasswordButton = (Button) findViewById(R.id.newPasswordButton);
        infoButton.setOnClickListener(this);
        newPasswordButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == infoButton) {
            //Toast.makeText(this, "No more info", Toast.LENGTH_SHORT).show();
            if (!infoButton.isChecked()) {
                //Do stuff
            } else {
                //Do other stuff
            }
        }
        if (v == newPasswordButton) {
            //make a change password page and bring the user to it
        }
    }
}
