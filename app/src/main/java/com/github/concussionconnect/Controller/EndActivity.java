package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.concussionconnect.R;

public class EndActivity extends Activity implements View.OnClickListener {
    private Button logoutButton;
    private Button newTestButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        newTestButton = (Button) findViewById(R.id.newTestButton);
        logoutButton.setOnClickListener(this);
        newTestButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == logoutButton) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (v == newTestButton) {
            startActivity(new Intent(this, SymptomsActivity.class));
        }
    }
}
